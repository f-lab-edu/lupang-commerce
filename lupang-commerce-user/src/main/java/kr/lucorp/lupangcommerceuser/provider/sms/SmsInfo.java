package kr.lucorp.lupangcommerceuser.provider.sms;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class SmsInfo {
  private String smsCode;
  private final String phoneNumber;
  private int tryCount;             // 재발급 횟수
  private int failedCount;          // 검증실패 횟수
  private boolean status;           // 차단 상태 관리
  private LocalDateTime lastIssueTime;  // 발급 시간

  public void incrementTryCount() {
    this.tryCount++;
  }

  public void incrementFailedCount() {
    this.failedCount++;
  }

  public void verifyFailedStatus(int maxFailedCount) {
    if(this.failedCount > maxFailedCount) {
      this.status = true;
    }
  }

  public void updateSmsCodeAndTokenExpired(String smsCode) {
    this.smsCode = smsCode;
    this.lastIssueTime = LocalDateTime.now(); // 인증토큰 시간도 초기화
  }

  public SmsInfo(String smsCode, String phoneNumber) {
    this.smsCode = smsCode;
    this.phoneNumber = phoneNumber;
    this.tryCount = 0;
    this.failedCount = 0;
    this.status = false;
    this.lastIssueTime = LocalDateTime.now();
  }

  public String generateCertificationCode() {
    return this.smsCode + "&" + this.phoneNumber + "&" + LocalDateTime.now();
  }
}
