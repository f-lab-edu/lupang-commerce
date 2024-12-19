package kr.lucorp.lupangcommerceuser.user.domain.dto;

import kr.lucorp.lupangcommerceuser.common.client.model.ErrorCode;
import kr.lucorp.lupangcommerceuser.common.util.CheckValidation;
import kr.lucorp.lupangcommerceuser.common.util.Validatable;
import kr.lucorp.lupangcommerceuser.core.exception.defined.BusinessException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpRequest implements Validatable {
  private String username;        // 이름
  private String email;           // 이메일
  private String phoneNumber;     // 전화번호
  private String password;        // 비밀번호
  private String certificationVerifyCode;  // sms 검증확인코드
  private AcceptanceOfTermsDto termsDto;   // 약관 동의

  @Override
  public void validate() {

    //1. 이메일 형식 검증
    if(this.email == null || !CheckValidation.isValidEmail(this.email)) {
      throw new BusinessException(ErrorCode.INVALID_INPUT_EMAIL);
    }
    this.email = this.email.toLowerCase();

    //2. 전화번호 형식 검증
    if(this.phoneNumber == null || !CheckValidation.isValidPhoneNumber(this.phoneNumber)) {
      throw new BusinessException(ErrorCode.INVALID_INPUT_PHONE_NUMBER);
    }
    this.phoneNumber = this.phoneNumber.replaceAll("\\D", "");

    //3. 비밀번호 유효성 검증
    if(this.password == null || !CheckValidation.isValidPassword(this.password,
        this.email)) {
      throw new BusinessException(ErrorCode.INVALID_INPUT_PASSWORD);
    }

    //4. 약관동의 여부 검증
    if(this.termsDto == null || !CheckValidation.isValidAcceptanceTerms(this.termsDto)) {
      throw new BusinessException(ErrorCode.INVALID_INPUT_ACCEPTANCE_OF_TERMS);
    }
  }
}