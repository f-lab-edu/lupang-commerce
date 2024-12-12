package kr.lucorp.lupangcommerceuser.provider.redis;

import java.util.Optional;

public interface RedisProvider <T> {
  Optional<T> getRedisKey(String key);
  void saveRedisData(String key, T data, long timeoutSec);
  void deleteRedisData(String key);
}
