package com.example.Secureservice.controller;

import com.example.Secureservice.service.CryptoService;
import com.example.Secureservice.service.SeedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class SeedController {

    private final CryptoService cryptoService;
    private final SeedService seedService;

    public SeedController(CryptoService cryptoService, SeedService seedService) {
        this.cryptoService = cryptoService;
        this.seedService = seedService;
    }

    @PostMapping("/decrypt-seed")
    public ResponseEntity<?> decryptSeed(@RequestBody Map<String, String> body) {
        try {
            String enc = body.get("encrypted_seed");
            if (enc == null || enc.isBlank()) {
                return ResponseEntity.badRequest().body(Map.of("error", "missing encrypted_seed"));
            }
            String hex = cryptoService.decryptSeedBase64(enc);
            seedService.saveSeed(hex);
            return ResponseEntity.ok(Map.of("status", "ok"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}

