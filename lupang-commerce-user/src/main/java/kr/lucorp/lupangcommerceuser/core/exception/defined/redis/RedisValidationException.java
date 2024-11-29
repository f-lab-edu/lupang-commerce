package kr.lucorp.lupangcommerceuser.core.exception.defined.redis;

import kr.lucorp.lupangcommerceuser.common.client.model.ErrorCodes;
import kr.lucorp.lupangcommerceuser.core.exception.defined.BusinessException;

public class RedisValidationException extends BusinessException {

  public RedisValidationException(ErrorCodes errorCodes) {
    super(errorCodes);
  }
}
