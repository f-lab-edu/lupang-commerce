package kr.lucorp.lupangcommerceuser.user.service;

import kr.lucorp.lupangcommerceuser.common.client.model.ErrorCode;
import kr.lucorp.lupangcommerceuser.core.exception.defined.BusinessException;
import kr.lucorp.lupangcommerceuser.user.repository.UserSignUpFrontCheckRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/*
  front 유효성 로직 처리 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserSignUpFrontCheckService {

  private final UserSignUpFrontCheckRepository userSignUpFrontCheckRepository;

  public void checkDuplicateEmail(String email) {
    //중복된 이메일 검증
    Boolean isExist = userSignUpFrontCheckRepository.cntDuplicateEmail(email);

    if(Boolean.TRUE.equals(isExist)) {
      throw new BusinessException(ErrorCode.INVALID_DUPLICATE_EMAIL);
    }
  }

  public void checkDuplicatePhoneNumber(String phoneNumber) {
    // 중복된 회원 전화번호 검증
    Boolean isExist = userSignUpFrontCheckRepository.cntDuplicatePhoneNumber(phoneNumber);

    if(Boolean.TRUE.equals(isExist)) {
      throw new BusinessException(ErrorCode.INVALID_DUPLICATE_PHONE_NUMBER);
    }
  }
}
