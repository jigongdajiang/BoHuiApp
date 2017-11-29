package com.framework.core.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * 功能：AES加密解密工具包
 * 提供对称加密
 * 提供对文件对称加密
 *
 * @author yubaojian0616@163.com
 *         <p/>
 *         mobile enterprise application platform
 *         Version 0.1
 */
public class AESUtils {

    private static final String IV = "0102030405060708";
    private static final String ALGORITHM = "AES";
    private static final String AES_CBC = "AES/CBC/PKCS5Padding"; // "算法/模式/补码方式"
    private static final int KEY_SIZE = 128;
    private static final int CACHE_SIZE = 1024;


    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /** */
    /**
     * <p>
     * 生成随机密钥
     * </p>
     *
     * @throws Exception
     */
    public static String getSecretKey() throws Exception {
        return getSecretKey(null);
    }

    /** */
    /**
     * <p>
     * 生成密钥
     * </p>
     *
     * @param seed 密钥种子
     * @throws Exception
     */
    public static String getSecretKey(String seed) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        SecureRandom secureRandom;
        if (seed != null && !"".equals(seed)) {
            secureRandom = new SecureRandom(seed.getBytes());
        } else {
            secureRandom = new SecureRandom();
        }
        keyGenerator.init(KEY_SIZE, secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();
        return Arrays.toString(secretKey.getEncoded());
    }

    /** */
    /**
     * <p>
     * 加密
     * </p>
     *
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, String key) throws Exception {
        Key k = toKey(key.getBytes());
        byte[] raw = k.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_CBC);
        //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ips);
        return cipher.doFinal(data);
    }

    /** */
    /**
     * <p>
     * 文件加密
     * </p>
     *
     * @throws Exception
     */
    public static void encryptFile(String key, String sourceFilePath, String destFilePath) throws Exception {
        File sourceFile = new File(sourceFilePath);
        File destFile = new File(destFilePath);
        if (sourceFile.exists() && sourceFile.isFile()) {
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            destFile.createNewFile();
            InputStream in = new FileInputStream(sourceFile);
            OutputStream out = new FileOutputStream(destFile);
            Key k = toKey(key.getBytes());
            byte[] raw = k.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            CipherInputStream cin = new CipherInputStream(in, cipher);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead;
            while ((nRead = cin.read(cache)) != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            }
            out.close();
            cin.close();
            in.close();
        }
    }

    /** */
    /**
     * <p>
     * 解密
     * </p>
     *
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, String key) throws Exception {
        Key k = toKey(key.getBytes());
        byte[] raw = k.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_CBC);
        IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ips);
        return cipher.doFinal(data);
    }

    /** */
    /**
     * <p>
     * 文件解密
     * </p>
     *
     * @throws Exception
     */
    public static void decryptFile(String key, String sourceFilePath, String destFilePath) throws Exception {
        File sourceFile = new File(sourceFilePath);
        File destFile = new File(destFilePath);
        if (sourceFile.exists() && sourceFile.isFile()) {
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            destFile.createNewFile();
            FileInputStream in = new FileInputStream(sourceFile);
            FileOutputStream out = new FileOutputStream(destFile);
            Key k = toKey(key.getBytes());
            byte[] raw = k.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            CipherOutputStream cout = new CipherOutputStream(out, cipher);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead;
            while ((nRead = in.read(cache)) != -1) {
                cout.write(cache, 0, nRead);
                cout.flush();
            }
            cout.close();
            out.close();
            in.close();
        }
    }

    /** */
    /**
     * <p>
     * 转换密钥
     * </p>
     *
     * @throws Exception
     */
    private static Key toKey(byte[] key) throws Exception {
        return new SecretKeySpec(key, ALGORITHM);
    }

    /**
     * 将16进制转换为二进制
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /*
    *将二进制转换成16进制
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
        for (byte aBuf : buf) {
            String hex = Integer.toHexString(aBuf & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
}
