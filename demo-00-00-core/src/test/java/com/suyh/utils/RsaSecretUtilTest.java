package com.suyh.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

@Slf4j
public class RsaSecretUtilTest {

    @Test

    public void test01() {
        KeyPair keyPair = RsaSecretUtil.makeRsaSecretKey();
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] encoded = privateKey.getEncoded();
        String strEncoded = RadixConvertUtils.bytes2Hex(encoded);
        String format = privateKey.getFormat();
        log.info("encoded: {}", encoded);
        log.info("strEncoded: {}", strEncoded);
        log.info("format: {}", format);
        PublicKey publicKey = keyPair.getPublic();
        byte[] encodePub = publicKey.getEncoded();
        String strEncodedPub = RadixConvertUtils.bytes2Hex(encodePub);
        String formatPub = publicKey.getFormat();
        log.info("encode: {}", encodePub);
        log.info("strEncodePub: {}", strEncodedPub);
        log.info("format: {}", formatPub);
    }

    private final String strPrivateKey = "30820279020100300d06092a864886f70d0101010500048202633082025f02010002818100b59ac368fb61df1c13a90aa41c5922f202af77ed66c4c67cc46352e9c9a4f44acd41407583845c8c497e530d496ea093a6929a4d0f07781de027d06e828082026e074fd5644a2c410029c294cd3f926c82d8834a8db86dd4fa9fab3cb59c1639cd824b1eb24038e2231f58b88d543c0427bd23af8f20453b5f595b87a087e38b02030100010281810097a5a2a3d9b7029de974f123d56041a421c5f0b95765ddf175f512d992bea2188c897e9022c0b69f97075bc6ee98e8922338ac8cc88e0e29b472cac45d497e45ab47966005f458a292d1ccb3c2f7c312d8b3c9cc3470f5e17e0fcee0454631e54d6c9421c193a141798136c414950a35009ee798bfd50ea0ef23dc149c634961024100ee17410eddbdd7911d8a84052486736c655ea226318cc81d450dddc78a3cb78d6ea896e0b78ca0134864fb3b19e7bdce24f9a9527747e5e27a9477a1dc379c09024100c343caf79aa2147e48ac19f1ec6c56b3915f1c47894e695f0944d69441f9466366345ce54eae61c36643d88ee6fd50c78db49c5ac1c3aa87992e41fc4fd84ff3024100d93bf66c2dfdccf5e6d23f6ac1ccbafba7f214586cd55ab0fa1490ca4362eac6ecb47e1542e3d81a3fef831a85a46015fabea0b3518a1441f43e0a7970887ca10241009c368a1418637e5da129fdffab12e931f324d1ecf10e08027dd9d288fd56bd571c16dc129f25a714b9a5a184818b3aaacad52918fa56dd006a2db5a073082cbd0241009f06e6f4dc8383f3de051933396c30d646c2e5f544862cb3d086ebd69d7a0e3e0a2819c0cc9c857678271dbc8b0454a94ba09f0d4f1ab8c577f3aee865cf096e";
    private final String strPublicKey = "30819f300d06092a864886f70d010101050003818d0030818902818100b59ac368fb61df1c13a90aa41c5922f202af77ed66c4c67cc46352e9c9a4f44acd41407583845c8c497e530d496ea093a6929a4d0f07781de027d06e828082026e074fd5644a2c410029c294cd3f926c82d8834a8db86dd4fa9fab3cb59c1639cd824b1eb24038e2231f58b88d543c0427bd23af8f20453b5f595b87a087e38b0203010001";

    // 私钥加密，公钥解密
    @Test
    public void testRsaEncryptByPrivateKey() {
        byte[] secretData = RsaSecretUtil.RsaEncryptByPrivateKey("suyh-value", strPrivateKey);

        byte[] bytesData = RsaSecretUtil.RsaDecryptByPublicKey(secretData, strPublicKey);
        String strData = new String(bytesData);
        log.info("strDataResult: {}", strData);
    }

    // 公钥加密私钥解密
    @Test
    public void test02() {
        byte[] secretData = RsaSecretUtil.RsaEncryptByPublicKey("suyh-value", strPublicKey);

        byte[] bytesData = RsaSecretUtil.RsaDecryptByPrivateKey(secretData, strPrivateKey);
        String strData = new String(bytesData);
        log.info("strDataResult: {}", strData);
    }
}
