package kr.lucorp.lupangcommerceuser.user.repository;

import java.util.List;
import kr.lucorp.lupangcommerceuser.user.domain.entity.Terms;
import kr.lucorp.lupangcommerceuser.user.domain.entity.TermsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TermsRepository extends JpaRepository<Terms, TermsId> {

  @Query(value = """
        SELECT name, version, order_type, content, created_at, updated_at
        FROM (
            SELECT *,
                   ROW_NUMBER() OVER (PARTITION BY name ORDER BY version DESC) AS row_num
            FROM terms
        ) t
        WHERE row_num = 1
        """, nativeQuery = true)
  List<Terms> findAllLatestTerms();
}
