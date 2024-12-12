package kr.lucorp.lupangcommerceuser.user.domain.dto;

import kr.lucorp.lupangcommerceuser.common.client.model.ErrorCode;
import kr.lucorp.lupangcommerceuser.core.exception.defined.BusinessException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TermsType {

  AGE_CHECKED("AGE_CHECKED", "만 14세 이상 동의", "1.0"),
  LUPANG_TERMS_OF_SERVICE("LUPANG_TERMS_OF_SERVICE", "루팡 이용약관 동의", "1.0"),
  ELECTRONIC_FINANCIAL_TRANSACTIONS("ELECTRONIC_FINANCIAL_TRANSACTIONS", "전자금융거래 이용약관 동의", "1.0"),
  MANDATORY_PERSONAL_INFO("MANDATORY_PERSONAL_INFO", "개인정보 수집 및 이용 동의", "1.0"),
  OPTIONAL_PERSONAL_INFO("OPTIONAL_PERSONAL_INFO", "개인정보 제3자 제공 동의", "1.0"),
  MARKETING_PERSONAL_INFO("MARKETING_PERSONAL_INFO", "마케팅 목적의 개인정보 수집 및 이용 동의", "1.0"),
  MARKETING_EMAILS("MARKETING_EMAILS", "이메일 수신 동의", "1.0"),
  MARKETING_SMS("MARKETING_SMS", "SMS/SNS 수신 동의", "1.0"),
  MARKETING_APP("MARKETING_APP", "앱 푸시 수신 동의", "1.0");

  private final String code;        // 코드
  private final String name; // 약관 설명
  private final String version;     // 현재 사용 버전

  public static TermsType fromCode(String code) {
    for (TermsType type : TermsType.values()) {
      if (type.code.equalsIgnoreCase(code)) {
        return type;
      }
    }
    throw new BusinessException(ErrorCode.INVALID_TERMS_TYPE_CODE);
  }
}
