package kr.lucorp.lupangcommerceuser.user.domain.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class AcceptanceOfTermsDto {
  private Boolean ageChecked;                               // 만 14세 이상(필수)
  private Boolean lupangTermsOfService;                     // 루팡 이용약관 동의(필수)
  private Boolean termsOfElectronicFinancialTransactions;   // 전자금융거래 이용약관 동의(필수)
  private Boolean collectingMandatoryPersonalInformation;   // 개인정보 수집 및 이용약관 동의(필수)
  private Boolean collectingOptionalPersonalInformation;    // 개인정보 제3자 제공 동의(필수)
  private Boolean collectingMarketingPersonalInformation;   // 마키텡 목적의 개인정보 수집 및 이용 동의
  private Boolean sendingMarketingEmails;   // 이메일 수신 동의
  private Boolean sendingMarketingSMS;      // SMS, SNS 수신 동의
  private Boolean sendingMarketingAPP;      // 앱 푸시 수신 동의

  public boolean isEssentialTermsAgreed() {
    return ageChecked && lupangTermsOfService && termsOfElectronicFinancialTransactions &&
        collectingMandatoryPersonalInformation && collectingOptionalPersonalInformation;
  }

  public Map<String, Boolean> toMap() {
    Map<String, Boolean> map = new HashMap<>();
    map.put("만 14세 이상입니다", this.ageChecked);
    map.put("루팡 이용약관 동의", this.lupangTermsOfService);
    map.put("전자금융거래 이용약관 동의", this.termsOfElectronicFinancialTransactions);
    map.put("개인정보 수집 및 이용 동의", this.collectingMandatoryPersonalInformation);
    map.put("개인정보 제3자 제공 동의", this.collectingOptionalPersonalInformation);
    map.put("마케팅 목적의 개인정보 수집 및 이용 동의", this.collectingMarketingPersonalInformation);
    map.put("이메일 수신 동의", this.sendingMarketingEmails);
    map.put("SMS, SNS 수신 동의", this.sendingMarketingSMS);
    map.put("앱 푸시 수신 동의", this.sendingMarketingAPP);

    return map;
  }
}
