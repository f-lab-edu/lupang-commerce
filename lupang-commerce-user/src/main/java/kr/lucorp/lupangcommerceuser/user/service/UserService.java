package kr.lucorp.lupangcommerceuser.user.service;

import kr.lucorp.lupangcommerceuser.user.domain.entity.UserInfo;
import kr.lucorp.lupangcommerceuser.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  public Long saveUserJoin() {

    UserInfo userInfoEntity =  new UserInfo();//변환 로직 넣기
    return userRepository.save(userInfoEntity).getId();
  }
}
