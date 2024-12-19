package kr.lucorp.lupangcommerceuser.user.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSignUpFrontCheckRepository extends UserRepository{

  @Query("SELECT exists(ui.id) FROM UserInfo ui WHERE ui.email = :email")
  Boolean cntDuplicateEmail(@Param("email") String email);

  @Query("SELECT exists (ui.id) FROM UserInfo ui WHERE ui.phoneNumber = :phoneNumber")
  Boolean cntDuplicatePhoneNumber(@Param("phoneNumber") String phoneNumber);
}
