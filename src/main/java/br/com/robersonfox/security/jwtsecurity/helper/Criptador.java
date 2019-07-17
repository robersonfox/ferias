package br.com.robersonfox.security.jwtsecurity.helper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptador {
	public String encoder(String originalString) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));

		return bytesToHex(encodedhash);
	}

	private static String bytesToHex(byte[] hash) {
		StringBuffer hexString = new StringBuffer();

		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);

			if (hex.length() == 1)
				hexString.append('0');

			hexString.append(hex);
		}

		return hexString.toString();
	}
}
