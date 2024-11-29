package kr.lucorp.lupangcommerceuser.user.repository;

import kr.lucorp.lupangcommerceuser.user.domain.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

}
