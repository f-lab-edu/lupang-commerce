package kr.lucorp.lupangcommerceuser.constants;

import java.time.Duration;
import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RedisCacheKey {

  SMS_CERTIFICATE_KEY(
      value -> "sms:certificate:key:" + value,
      Duration.ofMinutes(5).getSeconds()
  );

  private final Function<Object, String> generateRedisKey;

  @Getter
  private final long timeoutSec;

  public String getRedisCacheKey(Object value) {
    return generateRedisKey.apply(value);
  }
}
