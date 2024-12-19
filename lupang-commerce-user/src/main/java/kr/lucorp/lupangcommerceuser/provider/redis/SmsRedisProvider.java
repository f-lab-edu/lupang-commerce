package kr.lucorp.lupangcommerceuser.provider.redis;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import kr.lucorp.lupangcommerceuser.provider.sms.SmsInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SmsRedisProvider implements RedisProvider<SmsInfo> {

  private final RedisTemplate<String, SmsInfo> redisTemplate;

  @Override
  public Optional<SmsInfo> getRedisKey(String key) {
    return Optional.ofNullable(redisTemplate.opsForValue().get(key));
  }

  @Override
  public void saveRedisData(String key, SmsInfo data, long timeoutSec) {
    redisTemplate.opsForValue().set(key, data, timeoutSec, TimeUnit.SECONDS);
  }

  public void increaseSmsTryCount(String key, SmsInfo smsInfo) {
    if(smsInfo != null) {
      executeTaskBySetnxLock(key, smsInfo, () -> {
        smsInfo.incrementTryCount();            // 시도횟수 증가
        Long redisExpireTime = redisTemplate.getExpire(key, TimeUnit.SECONDS); //redis 만료시간 조회
        if(redisExpireTime != null && redisExpireTime > 0) {
          redisTemplate.opsForValue().set(key, smsInfo, redisExpireTime, TimeUnit.SECONDS);
        }
      });
    }
  }

  public void increaseSmsFailedCount(String key, SmsInfo smsInfo, int maxFailedCount) {
    if (smsInfo != null) {
      executeTaskBySetnxLock(key, smsInfo, () -> {
        smsInfo.incrementFailedCount();                // 실패횟수 증가
        smsInfo.verifyFailedStatus(maxFailedCount);    // 최대 실패 횟수 초과시 차단 상태로 변경
        Long redisExpireTime = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        if(redisExpireTime != null && redisExpireTime > 0) {
          redisTemplate.opsForValue().set(key, smsInfo, redisExpireTime, TimeUnit.SECONDS);
        }
      });
    }
  }

  private void executeTaskBySetnxLock(String key, SmsInfo smsInfo, Runnable task) {
    String lockKey = "lock:" + key;

    Boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, smsInfo, 1, TimeUnit.SECONDS);
    if(locked != null && locked) {
      try {
        task.run();
      } finally {
        redisTemplate.delete(lockKey);
      }
    }
  }

  @Override
  public void deleteRedisData(String key) {
    redisTemplate.delete(key);
  }
}
