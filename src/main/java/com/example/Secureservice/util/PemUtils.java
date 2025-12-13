package com.example.Secureservice.util;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.PrivateKey;

public class PemUtils {

    public static PrivateKey loadPrivateKey(Path pemPath) throws Exception {
        try (Reader r = Files.newBufferedReader(pemPath);
             PEMParser pp = new PEMParser(r)) {
            Object obj = pp.readObject();
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
            if (obj instanceof PEMKeyPair) {
                KeyPair kp = converter.getKeyPair((PEMKeyPair) obj);
                return kp.getPrivate();
            } else if (obj instanceof PrivateKeyInfo) {
                return converter.getPrivateKey((PrivateKeyInfo) obj);
            } else {
                throw new IllegalArgumentException("Unsupported PEM object: " + obj.getClass());
            }
        }
    }
}

