package com.framework.core.security;

import android.content.Context;

import com.framework.core.security.codec.binary.Base64;

import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.crypto.Cipher;

/**
 * RSA加密
 *
 * @author 张海龙
 * @version 1.0
 * @since 1.0
 */
public abstract class RSAUtils {


    /**
     * Java密钥库(Java Key Store，JKS)KEY_STORE
     */
    public static final String KEY_STORE = "JKS";

    public static final String X509 = "X.509";

    /**
     * 由Certificate获得公钥
     *
     * @param certificatePath 证书路径
     */
    private static PublicKey getPublicKey(Context context, String certificatePath)
            throws Exception {
        Certificate certificate = getCertificate(certificatePath, context);
        PublicKey key = certificate.getPublicKey();
        return key;
    }

    /**
     * 获得Certificate
     *
     * @param certificatePath 证书路径
     */
    private static Certificate getCertificate(String certificatePath, Context context)
            throws Exception {
        CertificateFactory certificateFactory = CertificateFactory
                .getInstance(X509);
        //InputStream in =context.getResources().getAssets().open(certificatePath);
        FileInputStream in = (FileInputStream) context.getResources().getAssets().open(certificatePath); //new FileInputStream(certificatePath);
        Certificate certificate = certificateFactory.generateCertificate(in);
        in.close();

        return certificate;
    }

    /**
     * 公钥加密
     *
     * @param certificatePath 证书路径
     */
    public static byte[] encryptByPublicKey(byte[] data, String certificatePath, Context context)
            throws Exception {

        // 取得公钥
        PublicKey publicKey = getPublicKey(context, certificatePath);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);

    }

    /**
     * 公钥解密
     *
     * @param certificatePath 证书路径
     */
    public static byte[] decryptByPublicKey(byte[] data, String certificatePath, Context context)
            throws Exception {
        // 取得公钥
        PublicKey publicKey = getPublicKey(context, certificatePath);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);

    }

    /**
     * base64 解密
     */
    public static byte[] decryptBASE64(String str) {
        return Base64.decodeBase64(str);
//        return Base64Util.decode(str);
//        try {
//            return new BASE64Decoder().decodeBuffer(str);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return null;
    }

    /**
     * base64 加密
     *
     */
    public static String encryptBASE64(byte[] str) {
//        String result = null;
//        if (str != null) {
//            result = new BASE64Encoder().encode(str);
//        }
//        return result;
        return Base64.encodeBase64URLSafeString(str);
    }
}