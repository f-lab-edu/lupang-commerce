package kr.lucorp.lupangcommerceuser.user.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CertificationSmsVerifyRequest {
  private String phoneNumber;
  private String smsCode;
}
