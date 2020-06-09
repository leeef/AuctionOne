package com.hlnwl.auction.utils.sp;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * . 类描述： DES加密工具类
 */

public class DESUtils {
    //这个是DES加密解密的Key   还不确定
    private final static String encryptKey = "MySports";

    // 加密
    public static String encryptDES(String encryptString) {
        try {
            IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
//		IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
            return MyBase64.encode(encryptedData);
        } catch (Exception e) {

        }
        return null;
    }

    // 解密
    public static String decryptDES(String decryptString) {
        try {
            new MyBase64();
            byte[] byteMi = MyBase64.decode(decryptString);
//		IvParameterSpec zeroIv = new IvParameterSpec(iv);
            IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
            SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte decryptedData[] = cipher.doFinal(byteMi);
            return new String(decryptedData);
        } catch (Exception e) {
        }
        return null;
    }
}
