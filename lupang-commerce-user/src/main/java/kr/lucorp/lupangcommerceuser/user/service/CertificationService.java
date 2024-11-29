package kr.lucorp.lupangcommerceuser.user.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;
import kr.lucorp.lupangcommerceuser.common.client.model.ErrorCodes;
import kr.lucorp.lupangcommerceuser.common.client.model.ResponseObject;
import kr.lucorp.lupangcommerceuser.common.util.ResponseUtils;
import kr.lucorp.lupangcommerceuser.constants.RedisCacheKey;
import kr.lucorp.lupangcommerceuser.core.exception.defined.redis.RedisValidationException;
import kr.lucorp.lupangcommerceuser.provider.crypto.AESEncryptor;
import kr.lucorp.lupangcommerceuser.provider.redis.RedisProvider;
import kr.lucorp.lupangcommerceuser.provider.sms.CoolSmsCertificationProvider;
import kr.lucorp.lupangcommerceuser.user.domain.dto.CertificationSmsVerifyRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CertificationService {

  private final CoolSmsCertificationProvider coolSmsCertificationProvider;
  private final RedisProvider redisProvider;
  private final AESEncryptor aesEncryptor;

  private final int MAX_TRY_COUNT = 5;

  // SMS 인증번호 전송 로직
  public void actionCertificationSmsRequest(String phoneNumber) {
    // 0. 핸드폰번호 '-' 제거
    phoneNumber = phoneNumber.replace("-", "");

    // 1. 랜덤 번호를 생성해야한다.
    String smsCode = String.valueOf(getCertCode());

    // 2. 인증 요청 제한을 위해 핸드폰 번호로 redisKey를 생성
    RedisCacheKey redisCacheKey = RedisCacheKey.SMS_CERTIFICATE_TRY_COUNT;
    String smsTryRedisKey = redisCacheKey.getRedisCacheKey(phoneNumber);

    // 3. 생성한 redisKey로 실패 횟수 계산 검증 로직을 수행
    verifySmsTryCount(smsTryRedisKey, redisCacheKey.getTimeoutSec());

    // 4. 검증 후 SMS에 생성한 인증번호를 넘겨서 발송처리한다.
    coolSmsCertificationProvider.sendSMS(phoneNumber, smsCode);

    // 5. 전화번호를 redisKey로 암호화(인증코드, 전화번호, 현재 시각)하여 redis에 저장한다.
    String certificationCode = smsCode + "&" + LocalDateTime.now();
    String encryptedCode = aesEncryptor.encryptAES(certificationCode);
    RedisCacheKey redisSmsCodeKey = RedisCacheKey.SMS_CERTIFICATE_ENCRYPTED;
    String smsCodeRedisKey = redisSmsCodeKey.getRedisCacheKey(phoneNumber);

    // 6. redis에 추가하기
    redisProvider.saveRedisData(smsCodeRedisKey, encryptedCode, redisSmsCodeKey.getTimeoutSec());
  }

  private static int getCertCode() {
    SecureRandom random = new SecureRandom();
    return 100000 + random.nextInt(900000); // random 범위 0~89999 + 100000
  }

  // SMS 인증번호 검증 로직
  public ResponseEntity<ResponseObject<Map<String, Boolean>>> actionCertificationSmsVerify(
      CertificationSmsVerifyRequest certificationSmsVerifyRequest) {
    String phoneNumber = certificationSmsVerifyRequest.getPhoneNumber();
    phoneNumber = phoneNumber.replace("-", "");
    String smsCode = certificationSmsVerifyRequest.getSmsCode();

    // 1. redisKey(phoneNumber)로 smsCode RedisKey 조회
    RedisCacheKey redisCacheKey = RedisCacheKey.SMS_CERTIFICATE_ENCRYPTED;
    String encryptedToken = getRedisData(redisCacheKey, phoneNumber);

    // 2. 암호화 데이터 복호화 -> element별 요소 담기
    String decodedEncryptedToken = aesEncryptor.decryptAES(encryptedToken);
    String[] tokenElements = decodedEncryptedToken.split("\\&");

    // 3. 검증
    // 3-1. smsCode 검증
    if(!smsCode.equals(tokenElements[0])) {
      //인증번호 실패 count를 관리를 위한  redisKey를 생성
      RedisCacheKey smsFailCntKey = RedisCacheKey.SMS_CERTIFICATE_VERIFY_FAIL_COUNT;
      String smsTryRedisKey = smsFailCntKey.getRedisCacheKey(tokenElements[0]);
      verifySmsTryCount(smsTryRedisKey, smsFailCntKey.getTimeoutSec());

      throw new RedisValidationException(ErrorCodes.failVerifySms());
    }

    // 3-2. 암호화 토큰 만료시간 검증
    validationTokenExpiration(tokenElements[1]);

    // 4. sms 인증토큰 redis에서 제거
    redisProvider.deleteRedisData(redisCacheKey.getRedisCacheKey(phoneNumber));

    // 5. 결과 값 출력
    return ResponseUtils.createResponseEntity(Map.of("smsCertificationVerify", true), HttpStatus.OK);
  }

  private String getRedisData(RedisCacheKey redisCacheKey, String key) {
    return redisProvider.getRedisKey(redisCacheKey.getRedisCacheKey(key))
       .orElseThrow(() -> new RedisValidationException(ErrorCodes.failGetRedisKey()));
  }

  private void validationTokenExpiration(String createdDatetime) {
    LocalDateTime requestDateTime = LocalDateTime.parse(createdDatetime);
    LocalDateTime expireTime = requestDateTime.plusMinutes(3);
    LocalDateTime nowTime = LocalDateTime.now();

    if(nowTime.isAfter(expireTime)) {
      throw new RedisValidationException(ErrorCodes.failVerifyTokenExpired());
    }
  }

  // 기존에 redisKey가 존재하면 해당 값의 데이터를 1 증가 시키고 없으면 새로 생성한다.
  private void verifySmsTryCount(String redisKey, long timeoutSec) {
    long count;
    String existKey = redisProvider.getRedisKey(redisKey).orElse("0");

    if(existKey.equals("0")) {
      redisProvider.saveRedisData(redisKey, String.valueOf(1), timeoutSec); // 30 seconds
      count = 1;
    }
    else {
      count = redisProvider.increaseRedisCount(redisKey, 1);
    }

    if(count > MAX_TRY_COUNT) { //초과 시도시 예외 처리
      throw new RedisValidationException(ErrorCodes.failOverSMSTryCount());
    }
  }
}
