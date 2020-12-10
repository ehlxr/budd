/*
 * The MIT License (MIT)
 *
 * Copyright © 2020 xrv <xrg@live.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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