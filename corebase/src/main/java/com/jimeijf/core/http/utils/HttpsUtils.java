/*
 * Copyright (C) 2017 zhouyou(478319399@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jimeijf.core.http.utils;


import com.jimeijf.core.log.PrintLog;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * <p>描述：Https相关的工具类</p>
 * 1.https:
 *      即HTTP下加入SSL层,是具有安全性的ssl加密传输协议
 *      http和https使用的是完全不同的连接方式，用的端口也不一样，前者是80，后者是443。
 *      它的主要作用可以分为两种：一种是建立一个信息安全通道，来保证数据传输的安全；另一种就是确认网站的真实性。
 * 2.SSL
 *      利用数据加密(Encryption)技术，可确保数据在网络上之传输过程中不会被截取及窃听。
 *      SSL位于TCP/IP和HTTP协议之间
 *      认证用户和服务器，确保数据发送到正确的客户机和服务器；(验证证书)
 *      加密数据以防止数据中途被窃取；（加密）
 *      维护数据的完整性，确保数据在传输过程中不被改变。（摘要算法）
 * 3.Https握手流程
 *      (1)浏览器将自己支持的一套加密算法、HASH算法发送给网站。
 *      (2)站从中选出一组加密算法与HASH算法，并将自己的身份信息以证书的形式发回给浏览器。
 *         证书里面包含了网站地址，加密公钥，以及证书的颁发机构等信息。
 *      (3)浏览器获得网站证书之后，开始验证证书的合法性，如果证书信任，
 *         则生成一串随机数字作为通讯过程中对称加密的秘钥。
 *         然后取出证书中的公钥，将这串数字以及HASH的结果进行加密，然后发给网站。
 *      (4)网站接收浏览器发来的数据之后，通过私钥进行解密，然后HASH校验，
 *         如果一致，则使用浏览器发来的数字串使加密一段握手消息发给浏览器。
 *      (5)浏览器解密，并HASH校验，没有问题，则握手结束。
 *         接下来的传输过程将由之前浏览器生成的随机密码并利用对称加密算法进行加密。
 * 4.OkHttp
 *      默认支持https请求，不过支持的https的网站基本都是CA机构颁发的证书，默认情况下是可以信任的。
 *      app与自己服务端交互的时候可能也会遇到自签名证书的。需要进行自行配置，安全级别高的可能会有双向验证
 *      双向验证：客户端也会有个“kjs文件”，服务器那边会同时有个“cer文件”与之对应。
 * 关于https的操作客户端核心要干的是让我们的客户端能够信任服务端给的自签名证书
 * 作者： zhouyou<br>
 * 日期： 2017/5/15 16:55 <br>
 * 版本： v1.0<br>
 */
public class HttpsUtils {

    /**
     * sslContext.init()需要的两个核心参数
     * 最终会交由mOkHttpClient.setSslSocketFactory(sslContext.getSocketFactory());
     */
    public static class SSLParams {
        public SSLSocketFactory sSLSocketFactory;
        public X509TrustManager trustManager;
    }

    /**
     * 获取SSL参数,这个方法提供了支持双向认证的方案
     * @param bksFile 双向验证中在客户端的bks文件的输入流
     *                （这里不能是jks文件，否则会java.io.IOException: Wrong version of key store.
     *                 Java平台默认识别jks格式的证书文件，但是android平台只识别bks格式的证书文件。
     *                 如果有jks需要自行将jks转为bks后放入到asset下）
     * @param password bks文件的对应的密码
     * @param certificates  服务的端证书文件(一般从服务端下载下来后放在asset，或者直接导出其字符串)的输入流
     * @return
     */
    public static SSLParams getSslSocketFactory(InputStream bksFile, String password, InputStream[] certificates) {
        SSLParams sslParams = new SSLParams();
        try {
            //双向认证时，根据bks文件和密码得到客户端的keyManager
            KeyManager[] keyManagers = prepareKeyManager(bksFile, password);
            //根据证书输入流得到信任的管理器
            TrustManager[] trustManagers = prepareTrustManager(certificates);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            X509TrustManager trustManager;
            if (trustManagers != null) {
                //选出服务端的X509TrustManager并封装成可兼容的MyTrustManager
                trustManager = new MyTrustManager(chooseTrustManager(trustManagers));
            } else {
                //如果服务端没有则创建一个默认的TrustManager
                trustManager = new UnSafeTrustManager();
            }
            //初始化sslContext
            sslContext.init(keyManagers, new TrustManager[]{trustManager}, null);
            sslParams.sSLSocketFactory = sslContext.getSocketFactory();
            sslParams.trustManager = trustManager;
            return sslParams;
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        } catch (KeyManagementException e) {
            throw new AssertionError(e);
        } catch (KeyStoreException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * 根据证书输入流得到信任的管理器
     * @param certificates 证书输入流
     * @return
     */
    private static TrustManager[] prepareTrustManager(InputStream... certificates) {
        if (certificates == null || certificates.length <= 0) return null;
        try {
            //构造Certificate的工厂对象
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            //获取默认KeyStore对象，XX.jks对应的对象
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                //证书别名
                String certificateAlias = Integer.toString(index++);
                //将得到的certificate放到keystore中
                keyStore.setCertificateEntry(certificateAlias,
                        certificateFactory.generateCertificate(certificate));//根据别名得到Certificate对象
                try {
                    if (certificate != null) certificate.close();
                } catch (IOException e) {
                    PrintLog.e(e);
                }
            }
            //利用keyStore去初始化我们的TrustManagerFactory
            TrustManagerFactory trustManagerFactory;
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            //由trustManagerFactory.getTrustManagers获得TrustManager[]
            return trustManagerFactory.getTrustManagers();
            //其实到这一步我们就可以初始化SSLContext了，然后设置OkHttpClient能认可这个证书
            //后续代码可以是
            //sslContext.init
            //(
            //        null,
            //        trustManagerFactory.getTrustManagers(),
            //        new SecureRandom()
            //);
            //mOkHttpClient.setSslSocketFactory(sslContext.getSocketFactory());
        } catch (NoSuchAlgorithmException e) {
            PrintLog.e(e);
        } catch (CertificateException e) {
            PrintLog.e(e);
        } catch (KeyStoreException e) {
            PrintLog.e(e);
        } catch (Exception e) {
            PrintLog.e(e);
        }
        return null;
    }

    /**
     * 双向认证时，根据bks文件和密码得到客户端的keyManager
     * @param bksFile 客户端bks文件流
     * @param password bks密码
     * @return
     */
    private static KeyManager[] prepareKeyManager(InputStream bksFile, String password) {
        try {
            if (bksFile == null || password == null) return null;
            //获取bks对应的keystore
            KeyStore clientKeyStore = KeyStore.getInstance("BKS");
            //加载bks文件到KeyStore
            clientKeyStore.load(bksFile, password.toCharArray());
            //创建KeyMananger的工厂
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            //根据bks文件对应的keystore初始化keyManagerFactory
            keyManagerFactory.init(clientKeyStore, password.toCharArray());
            //获取双向认证时sslContext初始化需要的KeyManager[]
            return keyManagerFactory.getKeyManagers();
        } catch (KeyStoreException e) {
            PrintLog.e(e);
        } catch (NoSuchAlgorithmException e) {
            PrintLog.e(e);
        } catch (UnrecoverableKeyException e) {
            PrintLog.e(e);
        } catch (CertificateException e) {
            PrintLog.e(e);
        } catch (IOException e) {
            PrintLog.e(e);
        } catch (Exception e) {
            PrintLog.e(e);
        }
        return null;
    }

    /**
     * 从证书所对应的TrustManager中选出一个X509TrustManager对象
     * @param trustManagers
     * @return
     */
    private static X509TrustManager chooseTrustManager(TrustManager[] trustManagers) {
        for (TrustManager trustManager : trustManagers) {
            if (trustManager instanceof X509TrustManager) {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }

    /**
     * 不安全的证书认证管理器，只是简单实现下而已，没有做任何检查
     */
    private static class UnSafeTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    /**
     * 这里主要存储一个默认的X509TrustManager和服务端对应的X509TrustManager
     * 目的是做兼容，如果默认的不行的话才会采用服务端证书做合法性线程
     */
    private static class MyTrustManager implements X509TrustManager {
        private X509TrustManager defaultTrustManager;
        private X509TrustManager localTrustManager;

        public MyTrustManager(X509TrustManager localTrustManager) throws NoSuchAlgorithmException, KeyStoreException {
            TrustManagerFactory var4 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            var4.init((KeyStore) null);
            defaultTrustManager = chooseTrustManager(var4.getTrustManagers());
            this.localTrustManager = localTrustManager;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        /**
         * 检查服务端证书是否可信
         */
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                defaultTrustManager.checkServerTrusted(chain, authType);
            } catch (CertificateException ce) {
                localTrustManager.checkServerTrusted(chain, authType);
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
