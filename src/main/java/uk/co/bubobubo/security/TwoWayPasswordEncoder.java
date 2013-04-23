package uk.co.bubobubo.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class TwoWayPasswordEncoder implements PasswordEncoder {

	@Value("${ENCRYPTION_KEY}")
	private String encryptionKey;

	@Value("${ENCRYPTION_SALT}")
	private String encryptionSalt;

	@Override
	public String encodePassword(String password, Object ignoredSalt) {
		TextEncryptor textEncryptor = Encryptors.queryableText(encryptionKey, encryptionSalt);
		return textEncryptor.encrypt(password);
	}

	@Override
	public boolean isPasswordValid(String encryptedPassword, String rawPassword, Object ignoredSalt) {
		TextEncryptor textEncryptor = Encryptors.queryableText(encryptionKey, encryptionSalt);
		return encryptedPassword.equals(textEncryptor.encrypt(rawPassword));
	}
}

