package com.example.Secureservice.util;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.time.Instant;

public class TotpUtil {

    // Generate TOTP (HMAC-SHA1, 30 sec, 6 digits)
    public static String generateTotp(byte[] secret, int period, int digits, long unixTime) throws Exception {
        long counter = unixTime / period;

        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putLong(counter);
        byte[] counterBytes = bb.array();

        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(secret, "HmacSHA1"));
        byte[] hash = mac.doFinal(counterBytes);

        int offset = hash[hash.length - 1] & 0x0F;
        int binary = ((hash[offset] & 0x7f) << 24) |
                ((hash[offset + 1] & 0xff) << 16) |
                ((hash[offset + 2] & 0xff) << 8) |
                (hash[offset + 3] & 0xff);

        int otp = binary % (int) Math.pow(10, digits);

        return String.format("%0" + digits + "d", otp);
    }

    public static String generateNow(byte[] secret, int period, int digits) throws Exception {
        return generateTotp(secret, period, digits, Instant.now().getEpochSecond());
    }

    public static boolean verify(byte[] secret, String code, int period, int digits) throws Exception {
        long now = Instant.now().getEpochSecond();
        for (int i = -1; i <= 1; i++) {
            String c = generateTotp(secret, period, digits, now + (i * period));
            if (c.equals(code)) return true;
        }
        return false;
    }
}
