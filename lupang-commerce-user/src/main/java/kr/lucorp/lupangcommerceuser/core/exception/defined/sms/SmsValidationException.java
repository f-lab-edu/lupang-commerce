package kr.lucorp.lupangcommerceuser.core.exception.defined.sms;

import kr.lucorp.lupangcommerceuser.common.client.model.ErrorCodes;
import kr.lucorp.lupangcommerceuser.core.exception.defined.BusinessException;

public class SmsValidationException extends BusinessException {

  public SmsValidationException(ErrorCodes errorCodes) {
    super(errorCodes);
  }
}