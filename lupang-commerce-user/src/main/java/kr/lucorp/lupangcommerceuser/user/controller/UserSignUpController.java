package kr.lucorp.lupangcommerceuser.user.controller;

import java.util.Map;
import kr.lucorp.lupangcommerceuser.common.client.model.ErrorCodes;
import kr.lucorp.lupangcommerceuser.common.client.model.ResponseObject;
import kr.lucorp.lupangcommerceuser.common.util.CheckValidation;
import kr.lucorp.lupangcommerceuser.core.exception.defined.signup.FrontValidationException;
import kr.lucorp.lupangcommerceuser.user.domain.dto.CertificationSmsVerifyRequest;
import kr.lucorp.lupangcommerceuser.user.domain.dto.UserSignUpRequest;
import kr.lucorp.lupangcommerceuser.user.service.FrontCheckService;
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
  private final FrontCheckService frontCheckService;
  private final CertificationService certificationService;

  /**
   *   이메일 중복 체크 API
   *
   *   @param email
   */
  @GetMapping("/signup/checkEmailDuplication")
  public void checkEmailDuplication(@RequestParam(name = "email") String email) {

    //1. 이메일 형식 검증
    if(email == null || !CheckValidation.isValidEmail(email)) {
      throw new FrontValidationException(ErrorCodes.invalidInputEmail());
    }

    //2. 이메일 중복 검증
    frontCheckService.checkDuplicateEmail(email);
  }

  /**
   *  전화번호 중복 가입 여부 확인
   *
   *  @param phoneNumber
   */
  @GetMapping("/signup/checkPhoneNumberDuplication")
  public void checkPhoneNumberDuplication(@RequestParam(name = "phoneNumber") String phoneNumber) {

    //1. 전화번호 형식 검증
    if(phoneNumber == null || !CheckValidation.isValidPhoneNumber(phoneNumber)) {
      throw new FrontValidationException(ErrorCodes.invalidInputPhoneNumber());
    }

    //2. 전화번호로 가입된 회원 여부 확인
    frontCheckService.checkDuplicatePhoneNumber(phoneNumber);
  }

  /**
   * 전화번호 sms 인증번호 발송 API
   *
   * @param phoneNumber
   */
  @GetMapping("/signup/sendSmsCertCode")
  public void sendSmsCertCode(@RequestParam(name = "phoneNumber") String phoneNumber) {

    //1. 전화번호 형식 검증
    if(phoneNumber == null || !CheckValidation.isValidPhoneNumber(phoneNumber)) {
      throw new FrontValidationException(ErrorCodes.invalidInputPhoneNumber());
    }

    //2. 전화번호로 가입된 회원 여부 확인
    frontCheckService.checkDuplicatePhoneNumber(phoneNumber);

    //3. sms 인증번호 발송
    certificationService.actionCertificationSmsRequest(phoneNumber);
  }

  /**
   * 전화번호 sms 인증번호 검증 API
   *
   * @param certificationSmsVerifyRequest
   */
  @PostMapping("/signup/verifySmsCertCode")
  public ResponseEntity<ResponseObject<Map<String, Boolean>>> verifySmsCertCode(@RequestBody
      CertificationSmsVerifyRequest certificationSmsVerifyRequest) {

    return certificationService.actionCertificationSmsVerify(certificationSmsVerifyRequest);
  }



  //todo 4-1. 회원가입 V1 API(단순 회원가입 처리)
  //todo 4-2. 비밀번호 유효성 체크 API
  @PostMapping("/v1/signup")
  public ResponseEntity<Map<String, Object>> SignUp(@RequestBody UserSignUpRequest userSignUpRequest) {

    //todo. 일단 목요일까지 해야할거 정리
    //     - 이메일 중복, 비밀번호 유효성, 회원가입 API 요 3개까지만 먼저 구현
    //     - swagger 도입
    //     - DB 설계 (user_info, acceptance_terms) -> 일단 2가지

    return ResponseEntity.ok().build();
  }


  //todo 4-2 회원가입 V2 API(회원가입시 자동으로 로그인 처리)
  @PostMapping("/v2/signup")
  public ResponseEntity<Map<String, Object>> SignUpAndLogin(@RequestBody UserSignUpRequest userSignUpRequest) {
    return ResponseEntity.ok().build();
  }
}
