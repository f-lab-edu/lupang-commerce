package kr.lucorp.lupangcommerceuser.user.service;

import kr.lucorp.lupangcommerceuser.common.client.model.ErrorCode;
import kr.lucorp.lupangcommerceuser.core.exception.defined.BusinessException;
import kr.lucorp.lupangcommerceuser.user.repository.FrontCheckRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/*
  front 유효성 로직 처리 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FrontCheckService {

  private final FrontCheckRepository frontCheckRepository;

  public void checkDuplicateEmail(String email) {
    //중복된 이메일 검증
    email = email.toLowerCase();
    Boolean isExist = frontCheckRepository.cntDuplicateEmail(email);

    if(Boolean.TRUE.equals(isExist)) {
      throw new BusinessException(ErrorCode.INVALID_DUPLICATE_EMAIL);
    }
  }

  public void checkDuplicatePhoneNumber(String phoneNumber) {
    // 핸드폰 번호 형식 통일
    phoneNumber = phoneNumber.replaceAll("\\D", "");

    // 중복된 회원 전화번호 검증
    Boolean isExist = frontCheckRepository.cntDuplicatePhoneNumber(phoneNumber);

    if(Boolean.TRUE.equals(isExist)) {
      throw new BusinessException(ErrorCode.INVALID_DUPLICATE_PHONE_NUMBER);
    }
  }
}
