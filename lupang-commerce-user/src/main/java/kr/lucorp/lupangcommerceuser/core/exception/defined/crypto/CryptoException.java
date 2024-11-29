package kr.lucorp.lupangcommerceuser.core.exception.defined.crypto;

import kr.lucorp.lupangcommerceuser.common.client.model.ErrorCodes;
import kr.lucorp.lupangcommerceuser.core.exception.defined.BusinessException;

public class CryptoException extends BusinessException {

  public CryptoException(ErrorCodes errorCodes) {
    super(errorCodes);
  }
}
