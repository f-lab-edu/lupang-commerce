package kr.lucorp.lupangcommerceuser.user.domain.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.lucorp.lupangcommerceuser.user.domain.dto.AcceptanceOfTermsDto;
import kr.lucorp.lupangcommerceuser.user.domain.dto.TermsType;
import kr.lucorp.lupangcommerceuser.user.domain.entity.Terms;
import kr.lucorp.lupangcommerceuser.user.domain.entity.TermsId;
import kr.lucorp.lupangcommerceuser.user.domain.entity.UserInfo;
import kr.lucorp.lupangcommerceuser.user.domain.entity.UserTermsAgreement;
import kr.lucorp.lupangcommerceuser.user.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TermsConverter {

  private final TermsRepository termsRepository;

  public List<UserTermsAgreement> toTermsConverter(UserInfo userInfo, AcceptanceOfTermsDto acceptanceOfTermsDto) {
    List<UserTermsAgreement> userTermsAgreementList = new ArrayList<>();

    Map<TermsType, Boolean> termsTypeMap = convertDtoToTermsTypeMap(acceptanceOfTermsDto);
    List<TermsId> termsIds = convertTermsIds(termsTypeMap);
    List<Terms> termsList = findAllByAcceptanceOfTerms(termsIds);

    for (Terms terms : termsList) {
      UserTermsAgreement userTermsAgreement = new UserTermsAgreement();
      userTermsAgreement.setUserInfo(userInfo);
      userTermsAgreement.setTerms(terms);
      userTermsAgreementList.add(userTermsAgreement);
    }
    return userTermsAgreementList;
  }

  private List<TermsId> convertTermsIds(Map<TermsType, Boolean> termsTypeMap) {
    List<TermsId> termsIds = new ArrayList<>();
    for(TermsType termType : termsTypeMap.keySet()) {
      termsIds.add(new TermsId(termType.getVersion(), termType.getName()));
    }
    return termsIds;
  }

  private List<Terms> findAllByAcceptanceOfTerms(List<TermsId> termsIds) {
    return termsRepository.findAllById(termsIds);
  }

  public static Map<TermsType, Boolean> convertDtoToTermsTypeMap(AcceptanceOfTermsDto acceptanceOfTermsDto) {
    Map<TermsType, Boolean> termsTypeMap = new HashMap<>();
    termsTypeMap.put(TermsType.AGE_CHECKED, acceptanceOfTermsDto.getAgeChecked());
    termsTypeMap.put(TermsType.LUPANG_TERMS_OF_SERVICE, acceptanceOfTermsDto.getLupangTermsOfService());
    termsTypeMap.put(TermsType.ELECTRONIC_FINANCIAL_TRANSACTIONS, acceptanceOfTermsDto.getTermsOfElectronicFinancialTransactions());
    termsTypeMap.put(TermsType.MANDATORY_PERSONAL_INFO, acceptanceOfTermsDto.getCollectingMandatoryPersonalInformation());
    termsTypeMap.put(TermsType.OPTIONAL_PERSONAL_INFO, acceptanceOfTermsDto.getCollectingOptionalPersonalInformation());
    termsTypeMap.put(TermsType.MARKETING_PERSONAL_INFO, acceptanceOfTermsDto.getCollectingMarketingPersonalInformation());
    termsTypeMap.put(TermsType.MARKETING_EMAILS, acceptanceOfTermsDto.getSendingMarketingEmails());
    termsTypeMap.put(TermsType.MARKETING_SMS, acceptanceOfTermsDto.getSendingMarketingSMS());
    termsTypeMap.put(TermsType.MARKETING_APP, acceptanceOfTermsDto.getSendingMarketingAPP());
    return termsTypeMap;
  }
}
