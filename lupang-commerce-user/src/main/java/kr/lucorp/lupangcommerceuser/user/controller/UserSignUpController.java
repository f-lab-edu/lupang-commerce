package kr.lucorp.lupangcommerceuser.user.controller;

import java.util.Map;
import kr.lucorp.lupangcommerceuser.common.client.model.ErrorCode;
import kr.lucorp.lupangcommerceuser.common.client.model.ResponseObject;
import kr.lucorp.lupangcommerceuser.common.util.CheckValidation;
import kr.lucorp.lupangcommerceuser.core.exception.defined.BusinessException;
import kr.lucorp.lupangcommerceuser.user.domain.dto.CertificationSmsVerifyRequest;
import kr.lucorp.lupangcommerceuser.user.domain.dto.UserSignUpRequest;
import kr.lucorp.lupangcommerceuser.user.service.UserSignUpFrontCheckService;
import kr.lucorp.lupangcommerceuser.user.service.CertificationService;
import kr.lucorp.lupangcommerceuser.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserSignUpController {

  private final UserService userService;
  private final UserSignUpFrontCheckService userSignUpFrontCheckService;
  private final CertificationService certificationService;

  /**
   *   이메일 중복 체크 API
   *
   *   @param email
   */
  @GetMapping("/signup/checkEmailDuplication")
  public void checkEmailDuplication(@RequestParam(name = "email") String email) {
    email = validateEmail(email);
    userSignUpFrontCheckService.checkDuplicateEmail(email);
  }

  /**
   *  전화번호 중복 가입 여부 확인
   *
   *  @param phoneNumber
   */
  @GetMapping("/signup/checkPhoneNumberDuplication")
  public void checkPhoneNumberDuplication(@RequestParam(name = "phoneNumber") String phoneNumber) {
    //1. 전화번호 형식 검증
    phoneNumber = validatePhoneNumber(phoneNumber);
    userSignUpFrontCheckService.checkDuplicatePhoneNumber(phoneNumber);
  }

  /**
   * 전화번호 sms 인증번호 발송 API
   *
   * @param phoneNumber
   */
  @GetMapping("/signup/sendSmsCertCode")
  public void sendSmsCertCode(@RequestParam(name = "phoneNumber") String phoneNumber) {

    //1. 전화번호로 가입된 회원 여부 확인
    phoneNumber = validatePhoneNumber(phoneNumber);
    userSignUpFrontCheckService.checkDuplicatePhoneNumber(phoneNumber);

    //3. sms 인증번호 발송
    certificationService.actionCertificationSmsRequest(phoneNumber);
  }

  /**
   * 전화번호 sms 인증번호 검증 API
   *
   * @param certificationSmsVerifyRequest
   */
  @PostMapping("/signup/verifySmsCertCode")
  public ResponseEntity<ResponseObject<Map<String, String>>> verifySmsCertCode(@RequestBody
      CertificationSmsVerifyRequest certificationSmsVerifyRequest) {
    certificationSmsVerifyRequest.validate();
    return certificationService.actionCertificationSmsVerify(certificationSmsVerifyRequest);
  }

  /**
   * 회원가입 API
   *
   * @param userSignUpRequest
   * @return
   */
  @PostMapping("/v1/signup")
  public ResponseEntity<ResponseObject<Map<String, Boolean>>> SignUp(@RequestBody UserSignUpRequest userSignUpRequest) {
    userSignUpRequest.validate();
    return userService.saveUserJoin(userSignUpRequest);
  }

  /**
   * 이메일 검증 로직 공통화
   */
  private String validateEmail(String email) {
    if (email == null || !CheckValidation.isValidEmail(email)) {
      throw new BusinessException(ErrorCode.INVALID_INPUT_EMAIL);
    }
    return email.toLowerCase();
  }

  /**
   * 전화번호 검증 로직 공통화
   */
  private String validatePhoneNumber(String phoneNumber) {
    if (phoneNumber == null || !CheckValidation.isValidPhoneNumber(phoneNumber)) {
      throw new BusinessException(ErrorCode.INVALID_INPUT_PHONE_NUMBER);
    }
    return phoneNumber.replaceAll("\\D", "");
  }
}
