package com.toskey.framework.core.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.SecureRandom;

/*
 * DES加密解密帮助类
 */
public class DESUtils {

	private static byte[] desKey = "20160202".getBytes(Charset.forName("UTF-8"));

	public DESUtils() {
	}

	private static byte[] desEncrypt(byte[] plainText) throws Exception {
		SecureRandom sr = new SecureRandom();
		byte rawKeyData[] = desKey;
		DESKeySpec dks = new DESKeySpec(rawKeyData);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, key, sr);
		byte data[] = plainText;
		byte encryptedData[] = cipher.doFinal(data);
		return encryptedData;
	}

	private static byte[] desDecrypt(byte[] encryptText) throws Exception {
		SecureRandom sr = new SecureRandom();
		byte rawKeyData[] = desKey;
		DESKeySpec dks = new DESKeySpec(rawKeyData);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, key, sr);
		byte encryptedData[] = encryptText;
		byte decryptedData[] = cipher.doFinal(encryptedData);
		return decryptedData;
	}

	/**
	 * DES加密 encrypt
	 *
	 * @描述:
	 *
	 * @param input
	 * @return
	 * @throws Exception
	 * @异常:
	 * @公司: 济南广域软件
	 * @author jingxj @创建日期： 2016年9月18日 @修改人： @修改日期： @备注：
	 */
	public static String encrypt(String input) throws Exception {
		return base64Encode(desEncrypt(input.getBytes()));
	}

	public static String decrypt(String input) throws Exception {
		byte[] result = base64Decode(input);
		return new String(desDecrypt(result));
	}

	private static String base64Encode(byte[] s) {
		if (s == null)
			return null;
		BASE64Encoder b = new BASE64Encoder();
		return b.encode(s);
	}

	private static byte[] base64Decode(String s) throws IOException {
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] b = decoder.decodeBuffer(s);
		return b;
	}

	public static String base64Decode1(String s) throws Exception {
		byte[] result = base64Decode(s);
		return new String(result);
	}

	public static void main(String[] args) throws Exception {
		// 待加密字符串
		String data = "{\"IP\":\"192.168.3.252\",\"Port\":\"3306\",\"DBBrand\":\"MySQL\",\"DBName\":\"stationdb\",\"UserName\":\"root\",\"PassWord\":\"root\"}";

		String encrypt = encrypt(data);
		System.out.println(encrypt);
		System.out.println(decrypt(encrypt));
		// String mm =
		// "sctTUgVh717AEjKD2WjU5dbAVZvzRO8hcVKahR/D1PG3uDfTLTEju/XKRUQp0nv5exTZgBDlLYDIjzJSeRnTWgj+w5M7ysq82Hlix4a19Hc1sfcBBnbzavES0FPqfcdHSb9F7JuWHIpI4Gcaq8K1hwPNedwcgklM";
		String mm = "sctTUgVh717AEjKD2WjU5dbAVZvzRO8hcVKahR/D1PG3uDfTLTEju/XKRUQp0nv5exTZgBDlLYDIjzJSeRnTWgj+w5M7ysq82Hlix4a19Hc1sfcBBnbzavES0FPqfcdHSb9F7JuWHIpI4Gcaq8K1hwPNedwcgklM";
		System.out.println(decrypt(mm));
	}

}