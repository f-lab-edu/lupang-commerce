package kr.lucorp.lupangcommerceuser.user.domain.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpRequest {
  private String username;        // 이름
  private String email;           // 이메일
  private String phoneNumber;     // 전화번호
  private String password;        // 비밀번호
  private String certificationVerifyCode;  // sms 검증확인코드
  private AcceptanceOfTermsDto termsDto;   // 약관 동의
}