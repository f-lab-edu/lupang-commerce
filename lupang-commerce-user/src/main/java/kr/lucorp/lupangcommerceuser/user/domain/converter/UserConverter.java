package kr.lucorp.lupangcommerceuser.user.domain.converter;

import kr.lucorp.lupangcommerceuser.provider.crypto.AESEncryptor;
import kr.lucorp.lupangcommerceuser.user.domain.dto.UserSignUpRequest;
import kr.lucorp.lupangcommerceuser.user.domain.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {

  private final AESEncryptor aesEncryptor;

  public UserInfo toUserInfoEntity(UserSignUpRequest userSignUpRequest) {
    UserInfo userInfoEntity =  new UserInfo();
    userInfoEntity.setPhoneNumber(userSignUpRequest.getPhoneNumber());
    userInfoEntity.setUserName(userSignUpRequest.getUsername());
    userInfoEntity.setEmail(userSignUpRequest.getEmail());

    String encryptedPassword = aesEncryptor.encryptAES(userSignUpRequest.getPassword()); //비밀번호 암호화
    userInfoEntity.setPasswdEnc(encryptedPassword);
    return userInfoEntity;
  }
}
