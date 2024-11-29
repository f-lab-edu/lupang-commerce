package kr.lucorp.lupangcommerceuser.provider.redis;

import static java.util.Optional.ofNullable;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import kr.lucorp.lupangcommerceuser.constants.RedisCacheKey;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RedisProviderImpl implements RedisProvider {

  private final StringRedisTemplate redisTemplate;

  @Override
  public Optional<String> getRedisKey(String key) {
    return Optional.ofNullable(redisTemplate.opsForValue().get(key));
  }

  @Override
  public void saveRedisData(String key, String data, long timeoutSec) {
    redisTemplate.opsForValue().set(key, data, timeoutSec, TimeUnit.SECONDS);
  }

  @Override
  public long increaseRedisCount(String key, long value) {
    return Optional.ofNullable(redisTemplate.opsForValue().increment(key, value))
        .orElse(0L);
  }

  @Override
  public void deleteRedisData(String key) {
    redisTemplate.delete(key);
  }
}
