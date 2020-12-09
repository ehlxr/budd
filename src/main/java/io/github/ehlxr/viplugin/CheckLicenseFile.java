package io.github.ehlxr.viplugin;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CheckLicenseFile {
	private static final String publicKey = "308201b83082012d";
	private static SecretKeySpec key;
	private static Cipher cipher;
	private static final byte[] linebreak = new byte[0];
	private static Base64 coder;

	static {
		try {
			key = new SecretKeySpec("308201b83082012d".getBytes(), "AES");
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			coder = new Base64(32, linebreak, true);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public static boolean checkLicenseLocations(String[] paths) throws Exception {
		String path = "";
		boolean found = false;
		for (int i = 0; i < paths.length; i++) {
			path = paths[i] + "viPlugin2.lic";
			if (new File(path).exists()) {
				found = true;
				break;
			}
			path = paths[i] + "viplugin2.lic";
			if (new File(path).exists()) {
				found = true;
				break;
			}
		}
		if (!found) {
			throw new Exception("License should be in one of the following locations:\n" + paths[0] + "\n" + paths[1]);
		}
		return checkLicenseFile(path);
	}

	private static boolean checkLicenseFile(String fileName) throws Exception {
		char[] buffer = new char[(int) new File(fileName).length()];
		try {
			FileReader fileReader = new FileReader(fileName);
			fileReader.read(buffer);
			fileReader.close();
		} catch (FileNotFoundException e) {
			throw new Exception("License file not found: " + fileName);
		} catch (IOException e) {
			throw new Exception("Can't read license file: " + fileName);
		}
		FileReader fileReader;
		String license = new String(buffer);
		if (!decrypt(license)) {
			throw new Exception("Invalid license found: " + fileName);
		}
		return true;
	}

	public static synchronized String encrypt(String name, String email) throws Exception {
		String plainText = name + "viPlugin 2.0" + email;
		cipher.init(1, key);
		byte[] cipherText = cipher.doFinal(plainText.getBytes());
		return new String(coder.encode(cipherText));
	}

	public static synchronized boolean decrypt(String codedText) throws Exception {
		byte[] encypted = coder.decode(codedText.getBytes());
		cipher.init(2, key);
		byte[] decrypted = cipher.doFinal(encypted);
		String decoded = new String(decrypted);
		return decoded.contains("viPlugin 2.0");
	}
}