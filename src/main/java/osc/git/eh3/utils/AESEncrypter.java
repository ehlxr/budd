package osc.git.eh3.utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AESEncrypter {

	private static byte[] iv = new byte[] { 21, 22, 50, 44, -16, 124, -40, -114, -11, -40, 37, 23, -33, 23, -33, 75 };
	private static String defalut_key = "defalut_keydefalut_key";

	/**
	 * 加密
	 * 
	 * @param content
	 *            要加密的内容
	 * @return 加密后的32位字符串
	 */
	public static String encrypt(String content) {
		return encrypt(content, defalut_key);
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            AES密文
	 * @return 解密后的内容
	 */
	public static String decrypt(String content) {
		return decrypt(content, defalut_key);
	}

	/**
	 * 加密
	 * 
	 * @param content
	 *            要加密的内容
	 * @param key
	 *            秘钥
	 * @return 加密后的32位字符串
	 */
	public static String encrypt(String content, String key) {
		String str = "";
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(key.getBytes()));
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
			SecretKey skey = kgen.generateKey();
			Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			ecipher.init(Cipher.ENCRYPT_MODE, skey, paramSpec);
			str = asHex(ecipher.doFinal(content.getBytes()));
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            AES密文
	 * @param key
	 *            秘钥
	 * @return 解密后的内容
	 */
	public static String decrypt(String content, String key) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(key.getBytes()));
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
			SecretKey skey = kgen.generateKey();
			Cipher dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			dcipher.init(Cipher.DECRYPT_MODE, skey, paramSpec);
			return new String(dcipher.doFinal(asBin(content)));
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		return "";
	}

	private static String asHex(byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;
		for (i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10)
				strbuf.append("0");
			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}
		return strbuf.toString();
	}

	private static byte[] asBin(String src) {
		if (src.length() < 1)
			return null;
		byte[] encrypted = new byte[src.length() / 2];
		for (int i = 0; i < src.length() / 2; i++) {
			int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);

			encrypted[i] = (byte) (high * 16 + low);
		}
		return encrypted;
	}
}
