package kr.lucorp.lupangcommerceuser.common.client.model;

import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public record ErrorCodes(ErrorCode errorCode, String detailMessage) implements Serializable {

  @Serial
  private static final long serialVersionUID = 1905122041950251207L;

  public static ErrorCodes invalidInputEmail() {
    return new ErrorCodes(ErrorCode.INVALID_INPUT_ERROR_1, "올바르지 않은 이메일 형식입니다.");
  }

  public static ErrorCodes invalidInputPhoneNumber() {
    return new ErrorCodes(ErrorCode.INVALID_INPUT_ERROR_2, "올바르지 않은 전화번호 형식입니다.");
  }

  public static ErrorCodes invalidInputPassword() {
    return new ErrorCodes(ErrorCode.INVALID_INPUT_ERROR_3, "올바르지 않은 비밀번호 형식입니다.");
  }

  public static ErrorCodes invalidDuplicateEmail() {
    return new ErrorCodes(ErrorCode.CONFLICT_CLIENT_ERROR_1, "이미 가입된 이메일입니다.");
  }

  public static ErrorCodes invalidDuplicatePhoneNumber() {
    return new ErrorCodes(ErrorCode.CONFLICT_CLIENT_ERROR_2, "이미 가입된 전화번호입니다.");
  }

  public static ErrorCodes failCryptoEncryptAES() {
    return new ErrorCodes(ErrorCode.CONFLICT_CRYPTO_ERROR_3, "암호화 도중 에러가 발생했습니다.");
  }

  public static ErrorCodes failCryptoDecryptAES() {
    return new ErrorCodes(ErrorCode.CONFLICT_CRYPTO_ERROR_3, "복호화 도중 에러가 발생했습니다.");
  }

  public static ErrorCodes failOverSMSTryCount() {
    return new ErrorCodes(ErrorCode.CONFLICT_CRYPTO_ERROR_5, "가능한 SMS 인증번호 횟수를 초과하였습니다.");
  }

  public static ErrorCodes failGetRedisKey() {
    return new ErrorCodes(ErrorCode.CONFLICT_CRYPTO_ERROR_6, "해당 RedisKey는 존재하지 않습니다.");
  }

  public static ErrorCodes failSmsMessageNotReceived() {
    return new ErrorCodes(ErrorCode.INTERNAL_SERVER_ERROR_2, "SMS 전송이 실패되었습니다.");
  }

  public static ErrorCodes failVerifySms() {
    return new ErrorCodes(ErrorCode.CONFLICT_CRYPTO_ERROR_7, "입력한 SMS 인증코드가 일치하지 않습니다.");
  }

  public static ErrorCodes failVerifyTokenExpired() {
    return new ErrorCodes(ErrorCode.REQUEST_TIMEOUT_ERROR_1, "요청 시간이 초과되어 인증 시간이 만료되었습니다. 다시 시도해주세요.");
  }

  @Getter
  public enum ErrorCode {

    //400번 에러
    INVALID_INPUT_ERROR_1("4001", "잘못된 요청 : 이메일 형식", HttpStatus.BAD_REQUEST),
    INVALID_INPUT_ERROR_2("4002", "잘못된 요청 : 전화번호 형식", HttpStatus.BAD_REQUEST),
    INVALID_INPUT_ERROR_3("4003", "잘못된 요청 : 비밀번호 형식", HttpStatus.BAD_REQUEST),

    //408번 에러
    REQUEST_TIMEOUT_ERROR_1("4081", "요청 시간 초과 : 토큰만료 검증 오류", HttpStatus.REQUEST_TIMEOUT),

    //409번 에러
    CONFLICT_CLIENT_ERROR_1("4091", "비지니스 에러 : 이메일 중복", HttpStatus.CONFLICT),
    CONFLICT_CLIENT_ERROR_2("4092", "비지니스 에러 : 전화번호 중복", HttpStatus.CONFLICT),
    CONFLICT_CRYPTO_ERROR_3("4093", "비지니스 에러 : 암호화 실패 오류", HttpStatus.CONFLICT),
    CONFLICT_CRYPTO_ERROR_4("4094", "비지니스 에러 : 복호화 실패 오류", HttpStatus.CONFLICT),
    CONFLICT_CRYPTO_ERROR_5("4095", "비지니스 에러 : 시도 횟수 초과 오류", HttpStatus.CONFLICT),
    CONFLICT_CRYPTO_ERROR_6("4096", "비지니스 에러 : Redis 조회 오류", HttpStatus.CONFLICT),
    CONFLICT_CRYPTO_ERROR_7("4096", "비지니스 에러 : SMS 검증 오류", HttpStatus.CONFLICT),

    //500번대 에러
    INTERNAL_SERVER_ERROR("5001", "서버 에러 : DB 에러", HttpStatus.INTERNAL_SERVER_ERROR),
    INTERNAL_SERVER_ERROR_2("5002", "서버 에러 : SMS API 에러", HttpStatus.INTERNAL_SERVER_ERROR);


    private final String code;
    private final String title;
    private final HttpStatus status;

    ErrorCode(String code, String title, HttpStatus status) {
      this.code = code;
      this.title = title;
      this.status = status;
    }
  }
}