package kr.lucorp.lupangcommerceuser.user.service;

import kr.lucorp.lupangcommerceuser.common.client.model.ErrorCodes;
import kr.lucorp.lupangcommerceuser.core.exception.defined.signup.FrontValidationException;
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
    Integer cnt = frontCheckRepository.cntDuplicateEmail(email);

    if(cnt > 0) {
      throw new FrontValidationException(ErrorCodes.invalidDuplicateEmail());
    }
  }

  public void checkDuplicatePhoneNumber(String phoneNumber) {
    // 핸드폰 번호 형식 통일
    phoneNumber = phoneNumber.replaceAll("\\D", "");

    // 중복된 회원 전화번호 검증
    Integer cnt = frontCheckRepository.cntDuplicatePhoneNumber(phoneNumber);

    if(cnt > 0) {
      throw new FrontValidationException(ErrorCodes.invalidDuplicatePhoneNumber());
    }

  }
}
