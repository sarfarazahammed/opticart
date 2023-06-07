package com.sarfaraz.opticart.commons.helper;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class RsaKeyHelper {

    private RsaKeyHelper() {
        throw new IllegalStateException("Helper Class");
    }
    public static RSAPublicKey getPublicKey(RSAPrivateKey privateKey) throws Exception {
        RSAPrivateCrtKey rsaPrivateKey = (RSAPrivateCrtKey) privateKey;
        return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(rsaPrivateKey.getModulus(), rsaPrivateKey.getPublicExponent()));
    }

    public static RSAPrivateKey getPrivateKeyFromString(String privateKeyString) throws Exception {
        byte[] encodedPrivate = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedPrivate);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) kf.generatePrivate(keySpec);
    }
}
