package kr.lucorp.lupangcommerceuser.user.repository;

import kr.lucorp.lupangcommerceuser.user.domain.entity.Terms;
import kr.lucorp.lupangcommerceuser.user.domain.entity.TermsId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermsRepository extends JpaRepository<Terms, TermsId> {
}
