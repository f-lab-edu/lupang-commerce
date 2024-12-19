package kr.lucorp.lupangcommerceuser.common.util;

import kr.lucorp.lupangcommerceuser.user.domain.dto.AcceptanceOfTermsDto;
import org.apache.commons.validator.routines.EmailValidator;

public class CheckValidation {

  private CheckValidation() {
    throw new IllegalStateException("CheckValidation 인스턴스 생성 제한");
  }

  public static boolean isValidEmail(String email) {
    EmailValidator emailValidator = EmailValidator.getInstance();

    // 이메일 유효성 검사
    return emailValidator.isValid(email);
  }

  public static boolean isValidPhoneNumber(String phoneNumber) {
    String phoneNumberRegex = "^(01[016789])-?(\\d{3,4})-?(\\d{4})$";

    // 전화번호 유효성 검사
    return phoneNumber.matches(phoneNumberRegex);
  }

  /**
   *  1. 영문/숫자/특수문자 2가지 이상 조합 (8~20자)
   *  2. 3개 이상 연속되거나 동일한 문자/숫자 제외
   *  3. 아이디(이메일) 제외
   */
  public static boolean isValidPassword(String password, String email) {
    String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d|.*[\\W_])[A-Za-z\\d\\W_]{8,20}(?!.*(.)\\1\\1).*$";

    // 비밀번호 유효성 검증
    if(!password.matches(passwordRegex)) return false;
    if(password.toLowerCase().contains(email.split("@")[0].toLowerCase())) return false;

    return true;
  }

  public static boolean isValidAcceptanceTerms(AcceptanceOfTermsDto termsDto) {
    return termsDto.isEssentialTermsAgreed();
  }
}
