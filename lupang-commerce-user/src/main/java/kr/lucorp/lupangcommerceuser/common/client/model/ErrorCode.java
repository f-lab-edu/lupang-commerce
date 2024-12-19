package kr.lucorp.lupangcommerceuser.common.client.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  //400번 에러 invalidInputEmail
  INVALID_INPUT_EMAIL("40001", "잘못된 요청 : 이메일 형식", HttpStatus.BAD_REQUEST, "올바르지 않은 이메일 형식입니다."),
  INVALID_INPUT_PHONE_NUMBER("40002", "잘못된 요청 : 전화번호 형식", HttpStatus.BAD_REQUEST, "올바르지 않은 전화번호 형식입니다."),
  INVALID_INPUT_PASSWORD("40003", "잘못된 요청 : 비밀번호 형식", HttpStatus.BAD_REQUEST, "올바르지 않은 비밀번호 형식입니다."),
  INVALID_INPUT_SMS_CODE("40004", "잘못된 요청 : SMS code", HttpStatus.BAD_REQUEST, "SMS 코드가 올바르지 않습니다."),

  //408번 에러
  FAIL_VERIFY_TOKEN_EXPIRED("40801", "요청 시간 초과 : 토큰만료 검증 오류", HttpStatus.REQUEST_TIMEOUT, "요청 시간이 초과되어 인증 시간이 만료되었습니다. 다시 시도해주세요."),

  //409번 에러
  INVALID_DUPLICATE_EMAIL("40901", "비지니스 에러 : 이메일 중복", HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),
  INVALID_DUPLICATE_PHONE_NUMBER("40902", "비지니스 에러 : 전화번호 중복", HttpStatus.CONFLICT, "이미 가입된 전화번호입니다."),
  FAIL_CRYPTO_ENCRYPT_ERROR("40903", "비지니스 에러 : 암호화 실패 오류", HttpStatus.CONFLICT, "암호화 도중 에러가 발생했습니다."),
  FAIL_CRYPTO_DECRYPT_ERROR("40904", "비지니스 에러 : 복호화 실패 오류", HttpStatus.CONFLICT, "복호화 도중 에러가 발생했습니다."),
  FAIL_OVER_SMS_TRY("40905", "비지니스 에러 : 시도 횟수 초과 오류", HttpStatus.CONFLICT, "가능한 SMS 인증번호 발급 횟수를 초과하였습니다."),
  FAIL_OVER_SMS_VERIFY("40906", "비지니스 에러 : 검증 실패 횟수 초과 오류", HttpStatus.CONFLICT, "가능한 SMS 인증번호 입력 검증 횟수를 초과하였습니다."),
  FAIL_GET_REDIS_KEY("40907", "비지니스 에러 : Redis 조회 오류", HttpStatus.CONFLICT, "해당 RedisKey는 존재하지 않습니다."),
  FAIL_VERIFY_SMS_CODE("40908", "비지니스 에러 : SMS 검증 오류", HttpStatus.CONFLICT, "SMS 인증코드가 일치하지 않습니다."),
  FAIL_VERIFY_PHONE_NUMBER("40909", "비지니스 에러 : 비밀번호 검증 오류", HttpStatus.CONFLICT, "전화번호가 일치하지 않습니다."),
  INVALID_INPUT_ACCEPTANCE_OF_TERMS("40910","비지니스 에러 : 약관 동의 검증 오류", HttpStatus.CONFLICT, "필수 약관동의사항을 체크하지 않았습니다."),
  INVALID_TERMS_TYPE_CODE("40911","비지니스 에러 : 약관 동의 타입 오류", HttpStatus.CONFLICT, "약관 동의 타입 변환중 에러가 발생하였습니다."),
  FAIL_SMS_STATUS("40912", "비지니스 에러 : SMS 차단 상태", HttpStatus.CONFLICT, "SMS 인증이 차단되었습니다. 10분 후에 다시 시도하세요."),
  FAIL_SMS_REQUEST("40913", "비지니스 에러 : SMS 요청 제한", HttpStatus.CONFLICT, "SMS 발급 요청은 1분 간격으로 가능합니다."),

  //500번대 에러
  FAIL_SMS_MESSAGE_NOT_RECEIVED("50002", "서버 에러 : SMS API 에러", HttpStatus.INTERNAL_SERVER_ERROR, "SMS 전송이 실패되었습니다."),
  FAIL_TO_ACCESS_FIELD("50003", "서버 에러 : Reflection 에러", HttpStatus.INTERNAL_SERVER_ERROR, "필드값에 잘못된 접근을 시도하였습니다.");


  private final String code;
  private final String title;
  private final HttpStatus status;
  private final String detailMessage;

  @Override
  public String toString() {
    return "ErrorCode{" +
        "code='" + code + '\'' +
        ", title='" + title + '\'' +
        ", status=" + status +
        ", detailMessage='" + detailMessage + '\'' +
        '}';
  }
}
