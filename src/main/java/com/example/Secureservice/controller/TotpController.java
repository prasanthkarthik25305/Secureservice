package com.example.Secureservice.controller;


import com.example.Secureservice.service.TotpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TotpController {

    private final TotpService totpService;

    public TotpController(TotpService totpService) {
        this.totpService = totpService;
    }

    @GetMapping("/generate-2fa")
    public ResponseEntity<?> generate2FA() {
        try {
            Map<String, Object> data = totpService.generate2FA();
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/verify-2fa")
    public ResponseEntity<?> verify(@RequestBody Map<String, String> body) {
        try {
            String code = body.get("code");
            if (code == null || code.isBlank()) {
                return ResponseEntity.badRequest().body(Map.of("error", "missing code"));
            }

            boolean isValid = totpService.verify(code);
            return ResponseEntity.ok(Map.of("valid", isValid));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
