package com.example.Secureservice.service;


import com.example.Secureservice.config.AppProperties;
import com.example.Secureservice.util.TotpUtil;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
public class TotpService {

    private final AppProperties appProps;
    private final SeedService seedService;

    public TotpService(AppProperties appProps, SeedService seedService) {
        this.appProps = appProps;
        this.seedService = seedService;
    }

    private byte[] getSecretBytes() throws Exception {
        String seedHex = seedService.readSeed();
        return Hex.decodeHex(seedHex.toCharArray());
    }

    public Map<String, Object> generate2FA() throws Exception {
        byte[] key = getSecretBytes();
        int digits = appProps.getTotp().getDigits();
        int period = appProps.getTotp().getPeriod();

        String code = TotpUtil.generateNow(key, period, digits);

        long now = Instant.now().getEpochSecond();
        long validFor = period - (now % period);

        return Map.of(
                "code", code,
                "valid_for", validFor
        );
    }

    public boolean verify(String code) throws Exception {
        return TotpUtil.verify(getSecretBytes(), code,
                appProps.getTotp().getPeriod(),
                appProps.getTotp().getDigits());
    }
}
