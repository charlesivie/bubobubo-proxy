package uk.co.bubobubo.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TwoWayPasswordEncoder implements PasswordEncoder {

	@Value("${ENCRYPTION_KEY}")
	private String encryptionKey;

	@Value("${ENCRYPTION_SALT}")
	private String encryptionSalt;

	@Override
	public String encode(CharSequence password) {
		TextEncryptor textEncryptor = Encryptors.queryableText(encryptionKey, encryptionSalt);
		return textEncryptor.encrypt(password.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encryptedPassword) {
		TextEncryptor textEncryptor = Encryptors.queryableText(encryptionKey, encryptionSalt);
		return encryptedPassword.equals(textEncryptor.encrypt(rawPassword.toString()));
	}
}

