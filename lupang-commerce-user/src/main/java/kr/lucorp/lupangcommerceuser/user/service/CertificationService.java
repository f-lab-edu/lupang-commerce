package kr.lucorp.lupangcommerceuser.user.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;
import kr.lucorp.lupangcommerceuser.common.client.model.ErrorCode;
import kr.lucorp.lupangcommerceuser.common.client.model.ResponseObject;
import kr.lucorp.lupangcommerceuser.common.util.ResponseUtils;
import kr.lucorp.lupangcommerceuser.constants.RedisCacheKey;
import kr.lucorp.lupangcommerceuser.core.exception.defined.BusinessException;
import kr.lucorp.lupangcommerceuser.provider.crypto.AESEncryptor;
import kr.lucorp.lupangcommerceuser.provider.redis.SmsRedisProvider;
import kr.lucorp.lupangcommerceuser.provider.sms.CoolSmsCertificationProvider;
import kr.lucorp.lupangcommerceuser.provider.sms.SmsInfo;
import kr.lucorp.lupangcommerceuser.user.domain.dto.CertificationSmsVerifyRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CertificationService {

  private final CoolSmsCertificationProvider coolSmsCertificationProvider;
  private final SmsRedisProvider redisProvider;
  private final AESEncryptor aesEncryptor;
  private final SecureRandom secureRandom = new SecureRandom();

  @Value("${sms.try.count}")
  private int MAX_TRY_COUNT;

  @Value("${sms.fail.count}")
  private int MAX_FAILED_COUNT;

  @Value("${sms.code.expired.minute}")
  private int EXPIRED_MINUTE;

  // SMS 인증번호 전송 로직
  public void actionCertificationSmsRequest(String phoneNumber) {
    // 1. 랜덤 번호를 생성해야한다.
    String smsCode = String.valueOf(getCertCode());

    // 2. 인증 요청 제한을 위해 핸드폰 번호로 redisKey를 생성
    RedisCacheKey redisCacheKey = RedisCacheKey.SMS_CERTIFICATE_KEY;
    String smsRedisKey = redisCacheKey.getRedisCacheKey(phoneNumber);

    // 3. redisKey로 인증정보 조회
    SmsInfo smsInfo = redisProvider.getRedisKey(smsRedisKey).orElse(new SmsInfo(smsCode, phoneNumber));

    // 4-1. tryCount가 MAX_TRY_COUNT 초과시 예외 처리
    if (smsInfo.getTryCount() > MAX_TRY_COUNT) {
      throw new BusinessException(ErrorCode.FAIL_OVER_SMS_TRY);
    }

    // 4-2. 재발급의 경우 1분 간격으로 요청 가능하도록 설정
    LocalDateTime lastIssueDate = smsInfo.getLastIssueTime();
    if(lastIssueDate == null || lastIssueDate.plusMinutes(1).isAfter(LocalDateTime.now())) {
      throw new BusinessException(ErrorCode.FAIL_SMS_REQUEST);
    }

    // 5. 발급 시도 횟수 증가
    increaseSmsTryCount(smsRedisKey, redisCacheKey.getTimeoutSec(), smsCode, smsInfo);

    // 6. 검증 후 SMS에 생성한 인증번호를 넘겨서 발송처리한다.
    coolSmsCertificationProvider.sendSMS(phoneNumber, smsCode);
  }

  private int getCertCode() {
    return 100000 + secureRandom.nextInt(900000); // random 범위 0~89999 + 100000
  }

  // SMS 인증번호 검증 로직
  public ResponseEntity<ResponseObject<Map<String, String>>> actionCertificationSmsVerify(
      CertificationSmsVerifyRequest certificationSmsVerifyRequest) {
    String smsCode = certificationSmsVerifyRequest.getSmsCode();

    // 1. redisKey(phoneNumber)로 smsCode RedisKey 조회
    RedisCacheKey redisCacheKey = RedisCacheKey.SMS_CERTIFICATE_KEY;
    String smsRedisKey = redisCacheKey.getRedisCacheKey(certificationSmsVerifyRequest.getPhoneNumber());

    // 2. redisKey에 대한 정보 조회
    SmsInfo smsInfo = redisProvider.getRedisKey(smsRedisKey).orElseThrow(() -> new BusinessException(ErrorCode.FAIL_GET_REDIS_KEY));

    // 3. 검증
    // 3-1. 인증실패 횟수 초과로 인한 차단 상태 확인
    if(smsInfo.isStatus()) {
      throw new BusinessException(ErrorCode.FAIL_SMS_STATUS);
    }

    // 3-2. smsCode 일치 여부 검증
    if(!smsCode.equals(smsInfo.getSmsCode())) {
      //인증번호 실패 count 증가
      increaseSmsFailedCount(smsRedisKey);
      throw new BusinessException(ErrorCode.FAIL_VERIFY_SMS_CODE); // SMS코드 불일치 예외
    }

    // 3-3. 암호화 토큰 만료시간 검증
    validationTokenExpiration(smsInfo.getLastIssueTime());

    // 4. smsCode 검증확인 암호화 진행
    String certificationCode = smsInfo.generateCertificationCode();
    String encryptedCode = aesEncryptor.encryptAES(certificationCode);

    // 5. 결과 값 출력
    return ResponseUtils.createResponseEntity(Map.of("certificationVerifyCode", encryptedCode), HttpStatus.OK);
  }

  private void validationTokenExpiration(LocalDateTime requestDateTime) {
    LocalDateTime expireTime = requestDateTime.plusMinutes(EXPIRED_MINUTE);
    LocalDateTime nowTime = LocalDateTime.now();

    if(nowTime.isAfter(expireTime)) {
      throw new BusinessException(ErrorCode.FAIL_VERIFY_TOKEN_EXPIRED);
    }
  }

  // 기존에 redisKey가 존재하면 해당 값의 시도 횟수를 증가시키고 없으면 새로 키를 생성한다.
  private void increaseSmsTryCount(String redisKey, long timeoutSec, String smsCode, SmsInfo smsInfo) {
    redisProvider.getRedisKey(redisKey)
        .ifPresentOrElse(
            value -> {
              value.updateSmsCodeAndTokenExpired(smsCode);
              redisProvider.increaseSmsTryCount(redisKey, value);
            },
            () -> redisProvider.saveRedisData(redisKey, smsInfo, timeoutSec)
        );
  }

  private void increaseSmsFailedCount(String redisKey) {
    redisProvider.getRedisKey(redisKey)
        .ifPresentOrElse(
            value -> redisProvider.increaseSmsFailedCount(redisKey, value, MAX_FAILED_COUNT),
            () -> { throw new BusinessException(ErrorCode.FAIL_GET_REDIS_KEY); }
        );
  }
}
