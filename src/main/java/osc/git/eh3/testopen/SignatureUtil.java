package osc.git.eh3.testopen;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 签名工具类
 *
 */
public  class SignatureUtil {
	
	
	private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

	private static String encryptionAlgorithm = "SHA-1";

	public static String bytesToHexString(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static byte[] hexStringToBytes(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	/**
	 * 使用指定算法生成消息摘要，默认是md5
	 * 
	 * @param strSrc
	 *            , a string will be encrypted; <br/>
	 * @param encName
	 *            , the algorithm name will be used, dafault to "MD5"; <br/>
	 * @return
	 */
	public static  String digest(String strSrc, String encName) {
		MessageDigest md = null;
		String strDes = null;
		byte[] bt = strSrc.getBytes();
		try {
			if (encName == null || encName.equals("")) {
				encName = "MD5";
			}
			md = MessageDigest.getInstance(encName);
			md.update(bt);
			strDes = bytesToHexString(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		return strDes;
	}

	/**
	 * 根据appid、token、lol以及时间戳来生成签名
	 * 
	 * @param appid
	 * @param token
	 * @param lol
	 * @param millis
	 * @return
	 */
	public static String generateSignature(String appid, String token, String lol, long millis) {
		String timestamp = String.valueOf(millis);
		String signature = null;
		if (StringUtils.isNotBlank(token) && StringUtils.isNotBlank(timestamp) && StringUtils.isNotBlank(appid)) {
			List<String> srcList = new ArrayList<String>();
			srcList.add(timestamp);
			srcList.add(appid);
			srcList.add(token);
			srcList.add(lol);
			// 按照字典序逆序拼接参数
			Collections.sort(srcList);
			Collections.reverse(srcList);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < srcList.size(); i++) {
				sb.append(srcList.get(i));
			}
			signature = digest(sb.toString(), encryptionAlgorithm);
			srcList.clear();
			srcList = null;
		}
		return signature;
	}

	/**
	 * 验证签名: <br/>
	 * 1.根据appid获取该渠道的token;<br/>
	 * 2.根据appid、token、lol以及时间戳计算一次签名;<br/>
	 * 3.比较传过来的签名以及计算出的签名是否一致;
	 * 
	 * @param signature
	 * @param appid
	 * @param lol
	 * @param millis
	 * @return
	 */
	public static boolean isValid(String signature, String appid,String token, String lol, long millis) {
		String calculatedSignature = generateSignature(appid, token, lol, millis);
		if (StringUtils.equals(calculatedSignature, signature)) {
			return true;
		} else {
			return false;
		}
	}

	 
	public   static void main(String[] args) {
		String xmlString = "<root><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name><name>test</name></root>";
		System.out.println(xmlString.getBytes().length);
		
		//消息
		String digest = SignatureUtil.digest(xmlString, "MD5");
		System.out.println("----" + digest);
		System.out.println(digest.getBytes().length);
		
		
		String appid = "canairport001";
		String token = "111ddff";
		long millis = System.currentTimeMillis();
		
		//生成签名
		String signature = SignatureUtil.generateSignature(appid, token, digest, millis);

		System.out.println(signature);

		//验证签名
		boolean isValid = SignatureUtil.isValid(signature, appid,token, digest, millis);
		System.out.println(isValid);
	}
}
