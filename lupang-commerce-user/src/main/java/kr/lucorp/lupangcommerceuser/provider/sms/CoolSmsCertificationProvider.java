package kr.lucorp.lupangcommerceuser.provider.sms;

import jakarta.annotation.PostConstruct;
import kr.lucorp.lupangcommerceuser.common.client.model.ErrorCodes;
import kr.lucorp.lupangcommerceuser.core.exception.defined.sms.SmsMessageNotReceivedException;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CoolSmsCertificationProvider {

  @Value("${coolsms.apikey}")
  private String apiKey;

  @Value("${coolsms.apisecret}")
  private String apiSecret;

  @Value("${coolsms.baseurl}")
  private String baseurl;

  @Value("${coolsms.from.number}")
  private String fromNumber;

  private DefaultMessageService messageService;

  @PostConstruct    // Value가 초기화되기 전에 생성자 호출될 가능성이 있음 (빈생성 후 메서드 실행)
  public void init() {
    this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, baseurl);
  }

  public void sendSMS(String toNumber, String certificationCode) {
    Message message = new Message();
    message.setFrom(fromNumber);
    message.setTo(toNumber);
    message.setText("루팡 휴대폰 인증번호 [" + certificationCode + "] 위 번호를 인증 창에 입력하세요.");

    try {
      messageService.send(message);
    } catch (Exception e) {
      log.warn(e.getMessage());
      throw new SmsMessageNotReceivedException(ErrorCodes.failSmsMessageNotReceived());
    }
  }
}
