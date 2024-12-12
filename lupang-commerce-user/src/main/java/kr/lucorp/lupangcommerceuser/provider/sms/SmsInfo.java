package kr.lucorp.lupangcommerceuser.provider.sms;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class SmsInfo {
  private String smsCode;
  private final String phoneNumber;
  private int tryCount;
  private int failedCount;
  private LocalDateTime createdAt;

  public void incrementTryCount() {
    this.tryCount++;
  }

  public void incrementFailedCount() {
    this.failedCount++;
  }

  public void updateSmsCodeAndTokenExpired(String smsCode) {
    this.smsCode = smsCode;
    this.createdAt = LocalDateTime.now(); // 인증토큰 시간도 초기화
  }

  public SmsInfo(String smsCode, String phoneNumber) {
    this.smsCode = smsCode;
    this.phoneNumber = phoneNumber;
    this.tryCount = 0;
    this.failedCount = 0;
    this.createdAt = LocalDateTime.now();
  }

  public String generateCertificationCode() {
    return this.smsCode + "&" + this.phoneNumber + "&" + LocalDateTime.now();
  }
}
