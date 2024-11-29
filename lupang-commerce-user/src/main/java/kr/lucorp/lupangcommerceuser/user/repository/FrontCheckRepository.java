package kr.lucorp.lupangcommerceuser.user.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FrontCheckRepository extends UserRepository{

  @Query("SELECT count(ui.id) FROM UserInfo ui WHERE ui.email = :email")
  Integer cntDuplicateEmail(@Param("email") String email);

  @Query("SELECT count(ui.id) FROM UserInfo ui WHERE ui.phoneNumber = :phoneNumber")
  Integer cntDuplicatePhoneNumber(@Param("phoneNumber") String phoneNumber);
}
