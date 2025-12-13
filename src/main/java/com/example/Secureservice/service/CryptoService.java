package com.example.Secureservice.service;



import com.example.Secureservice.config.KeyProperties;
import com.example.Secureservice.util.PemUtils;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.nio.file.Path;
import java.security.PrivateKey;
import java.security.spec.MGF1ParameterSpec;
import java.util.Base64;
import jakarta.annotation.PostConstruct;


@Service
public class CryptoService {

    private final KeyProperties keyProps;
    private PrivateKey studentPrivate;

    public CryptoService(KeyProperties keyProps) {
        this.keyProps = keyProps;
    }

    @PostConstruct
    public void init() throws Exception {
        Path p = Path.of(keyProps.getStudentPrivate());
        studentPrivate = PemUtils.loadPrivateKey(p);
    }

    public String decryptSeedBase64(String encryptedBase64) throws Exception {
        byte[] cipherBytes = Base64.getDecoder().decode(encryptedBase64);
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding", "BC");
        OAEPParameterSpec oaepParams = new OAEPParameterSpec(
                "SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
        cipher.init(Cipher.DECRYPT_MODE, studentPrivate, oaepParams);
        byte[] plain = cipher.doFinal(cipherBytes);
        String hex = new String(plain, java.nio.charset.StandardCharsets.US_ASCII);
        // validate hex length 64
        if (hex.length() != 64 || !hex.matches("[0-9a-fA-F]+")) {
            throw new IllegalArgumentException("Decrypted seed is not 64-hex characters");
        }

        return hex;
    }
}
