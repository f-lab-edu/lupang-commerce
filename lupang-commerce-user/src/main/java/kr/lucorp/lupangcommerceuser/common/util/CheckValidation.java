package kr.lucorp.lupangcommerceuser.common.util;

import org.apache.commons.validator.routines.EmailValidator;

public class CheckValidation {

  private CheckValidation() {
    throw new IllegalStateException("CheckValidation 인스턴스 생성 제한");
  }

  public static boolean isValidEmail(String text) {
    EmailValidator emailValidator = EmailValidator.getInstance();

    // 이메일 유효성 검사
    return emailValidator.isValid(text);
  }

  public static boolean isValidPhoneNumber(String phoneNumber) {
    String phoneNumberRegex = "^(01[016789])-?(\\d{3,4})-?(\\d{4})$";

    // 전화번호 유효성 검사
    return phoneNumber.matches(phoneNumberRegex);
  }
}
