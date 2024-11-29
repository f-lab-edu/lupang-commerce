package kr.lucorp.lupangcommerceuser.constants;

import java.time.Duration;
import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RedisCacheKey {

  SMS_CERTIFICATE_ENCRYPTED(
      value -> "sms:certificate:encrypted:" + value,
      Duration.ofMinutes(3).getSeconds()
  ),
  SMS_CERTIFICATE_TRY_COUNT(
      value -> "sms:certificate:count:" + value,
      Duration.ofMinutes(3).getSeconds()
  ),
  SMS_CERTIFICATE_VERIFY_FAIL_COUNT(
      value -> "sms:certificate:verify:fail:count:" + value,
      Duration.ofMinutes(3).getSeconds()
  );

  private final Function<Object, String> generateRedisKey;

  @Getter
  private final long timeoutSec;

  public String getRedisCacheKey(Object value) {
    return generateRedisKey.apply(value);
  }
}
