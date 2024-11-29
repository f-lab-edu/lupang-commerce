package kr.lucorp.lupangcommerceuser.core.exception.defined.signup;

import kr.lucorp.lupangcommerceuser.common.client.model.ErrorCodes;
import kr.lucorp.lupangcommerceuser.core.exception.defined.BusinessException;

public class FrontValidationException extends BusinessException {

  public FrontValidationException(ErrorCodes errorCodes) {
    super(errorCodes);
  }
}
