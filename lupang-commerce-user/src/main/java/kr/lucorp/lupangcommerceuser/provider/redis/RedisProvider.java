package kr.lucorp.lupangcommerceuser.provider.redis;

import java.util.Optional;

public interface RedisProvider {
  Optional<String> getRedisKey(String key);
  void saveRedisData(String key, String data, long timeoutSec);
  long increaseRedisCount(String key, long value);
  void deleteRedisData(String key);
}
