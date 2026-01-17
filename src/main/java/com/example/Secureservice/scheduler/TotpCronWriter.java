package com.example.Secureservice.scheduler;

import com.example.Secureservice.service.TotpService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TotpCronWriter {

    private final TotpService totpService;

    public TotpCronWriter(TotpService totpService) {
        this.totpService = totpService;
    }

    // Run every second to ensure file gets refreshed within the current period window
    @Scheduled(fixedRate = 1000)
    public void refreshTotpFile() {
        try {
            totpService.writeCurrentCodeToFile();
        } catch (Exception ignored) {
            // Intentionally ignore to avoid crashing scheduler; errors are transient until seed is set
        }
    }
}
