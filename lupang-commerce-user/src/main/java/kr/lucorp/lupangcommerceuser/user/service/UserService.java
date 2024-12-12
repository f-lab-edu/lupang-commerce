package kr.lucorp.lupangcommerceuser.user.service;

import java.util.List;
import java.util.Map;
import kr.lucorp.lupangcommerceuser.common.client.model.ErrorCode;
import kr.lucorp.lupangcommerceuser.common.client.model.ResponseObject;
import kr.lucorp.lupangcommerceuser.common.util.ResponseUtils;
import kr.lucorp.lupangcommerceuser.constants.RedisCacheKey;
import kr.lucorp.lupangcommerceuser.core.exception.defined.BusinessException;
import kr.lucorp.lupangcommerceuser.provider.crypto.AESEncryptor;
import kr.lucorp.lupangcommerceuser.provider.redis.SmsRedisProvider;
import kr.lucorp.lupangcommerceuser.provider.sms.SmsInfo;
import kr.lucorp.lupangcommerceuser.user.domain.converter.TermsConverter;
import kr.lucorp.lupangcommerceuser.user.domain.converter.UserConverter;
import kr.lucorp.lupangcommerceuser.user.domain.dto.UserSignUpRequest;
import kr.lucorp.lupangcommerceuser.user.domain.entity.UserInfo;
import kr.lucorp.lupangcommerceuser.user.domain.entity.UserTermsAgreement;
import kr.lucorp.lupangcommerceuser.user.repository.UserRepository;
import kr.lucorp.lupangcommerceuser.user.repository.UserTermsAgreementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

  private final FrontCheckService frontCheckService;
  private final UserRepository userRepository;
  private final UserTermsAgreementRepository userTermsAgreementRepository;
  private final AESEncryptor aesEncryptor;
  private final SmsRedisProvider redisProvider;
  private final UserConverter userConverter;
  private final TermsConverter termsConverter;

  public ResponseEntity<ResponseObject<Map<String, Boolean>>> saveUserJoin(UserSignUpRequest userSignUpRequest) {

    // 1. 이메일 중복 검증
    frontCheckService.checkDuplicateEmail(userSignUpRequest.getEmail());

    // 2. 전화번호 중복 검증
    frontCheckService.checkDuplicatePhoneNumber(userSignUpRequest.getPhoneNumber());
    userSignUpRequest.setPhoneNumber(userSignUpRequest.getPhoneNumber().replaceAll("\\D", ""));

    // 3. 토큰 검증
    String encryptedToken = userSignUpRequest.getCertificationVerifyCode();
    String decodedEncryptedToken = aesEncryptor.decryptAES(encryptedToken);
    String[] tokenElements = decodedEncryptedToken.split("\\&");
    validateCertificateToken(tokenElements, userSignUpRequest);

    // 4. userInfoEntity 변환
    UserInfo userInfo = userConverter.toUserInfoEntity(userSignUpRequest);

    // 5. 약관 동의 조회 및 변환
    List<UserTermsAgreement> userTermsAgreements = this.termsConverter.toTermsConverter(userInfo, userSignUpRequest.getTermsDto());
    userInfo.setAggremtTermList(userTermsAgreements);

    // 6. 저장
    userRepository.save(userInfo);

    return ResponseUtils.createResponseEntity(Map.of("SUCCESS", Boolean.TRUE), HttpStatus.OK);
  }

  void validateCertificateToken(String[] tokenElements, UserSignUpRequest userSignUpRequest) {
    if(!StringUtils.equals(userSignUpRequest.getPhoneNumber(), tokenElements[1])) { // 번호 일치 비교
      throw new BusinessException(ErrorCode.FAIL_VERIFY_PHONE_NUMBER);
    }

    // redis 토큰 검증
    RedisCacheKey redisCacheKey = RedisCacheKey.SMS_CERTIFICATE_KEY;
    String smsRedisKey = redisCacheKey.getRedisCacheKey(userSignUpRequest.getPhoneNumber());

    // redisKey에 대한 정보 조회 검증
    SmsInfo smsInfo = redisProvider.getRedisKey(smsRedisKey).orElseThrow(() -> new BusinessException(
        ErrorCode.FAIL_GET_REDIS_KEY));

    if(!StringUtils.equals(smsInfo.getSmsCode(), tokenElements[0])) { // smsCode 일치 비교
      throw new BusinessException(ErrorCode.FAIL_VERIFY_SMS_CODE);
    }

    redisProvider.deleteRedisData(smsRedisKey); // redis 제거
  }
}
