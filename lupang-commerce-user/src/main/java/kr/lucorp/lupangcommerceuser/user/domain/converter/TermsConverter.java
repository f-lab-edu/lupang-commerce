package kr.lucorp.lupangcommerceuser.user.domain.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kr.lucorp.lupangcommerceuser.user.domain.dto.AcceptanceOfTermsDto;
import kr.lucorp.lupangcommerceuser.user.domain.entity.Terms;
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
    List<Terms> termsList = findAllByAcceptanceOfTerms();
    Map<String, Boolean> termsStatus = acceptanceOfTermsDto.toMap();

    for (Terms terms : termsList) {
      if(termsStatus.get(terms.getTermsId().getName())) { //동의한 항목만 저장
        UserTermsAgreement userTermsAgreement = new UserTermsAgreement();
        userTermsAgreement.setUserInfo(userInfo);
        userTermsAgreement.setTerms(terms);
        userTermsAgreementList.add(userTermsAgreement);
      }
    }
    return userTermsAgreementList;
  }

  private List<Terms> findAllByAcceptanceOfTerms() {
    return termsRepository.findAllLatestTerms();
  }
}
