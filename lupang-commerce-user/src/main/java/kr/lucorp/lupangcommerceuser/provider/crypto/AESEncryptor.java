package kr.lucorp.lupangcommerceuser.provider.crypto;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import kr.lucorp.lupangcommerceuser.common.client.model.ErrorCodes;
import kr.lucorp.lupangcommerceuser.core.exception.defined.crypto.CryptoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AESEncryptor {

  @Value("${aes.key}")
  private String aesKey; //16bit, AES128 사용

  @Value("${aes.iv}")
  private String aesIv;  //16bit

  @Value("${aes.algorithms}")
  private String aesAlgorithms;

  public String encryptAES(String text) {
    try {
      // 암호화/복호화 기능 객체
      Cipher cipher = Cipher.getInstance(aesAlgorithms);

      // 비밀키 생성
      SecretKeySpec secretKeySpec = new SecretKeySpec(aesKey.getBytes(), "AES");

      // Iv로 SPEC 생성
      IvParameterSpec ivParameterSpec = new IvParameterSpec(aesIv.getBytes());

      // 암호화 적용
      cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

      byte[] encryptedByte = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));

      return Base64.getEncoder().encodeToString(encryptedByte);

    } catch (Exception e) {
      //NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
      log.error("암호화 도중 에러 발생 : {}", e.getMessage());
      throw new CryptoException(ErrorCodes.failCryptoEncryptAES());
    }
  }

  public String decryptAES(String encryptedText) {
    try {
      // 암호화/복호화 기능 객체
      Cipher cipher = Cipher.getInstance(aesAlgorithms);

      // 비밀키 생성
      SecretKeySpec secretKeySpec = new SecretKeySpec(aesKey.getBytes(), "AES");

      // Iv로 SPEC 생성
      IvParameterSpec ivParameterSpec = new IvParameterSpec(aesIv.getBytes());

      // ��호화 적용
      cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

      byte[] decodedByte = Base64.getDecoder().decode(encryptedText);
      byte[] decryptedByte = cipher.doFinal(decodedByte);

      return new String(decryptedByte, StandardCharsets.UTF_8);
    } catch (Exception e) {
      log.error("복호화 도중 에러 발생 : {}", e.getMessage());
      throw new CryptoException(ErrorCodes.failCryptoDecryptAES());
    }
  }
}
