package kr.lucorp.lupangcommerceuser.user.domain.dto;

import java.util.List;
import java.util.Map;
import kr.lucorp.lupangcommerceuser.user.domain.entity.TermsId;
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
}
