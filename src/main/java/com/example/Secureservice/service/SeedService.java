package com.example.Secureservice.service;


import com.example.Secureservice.config.AppProperties;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
public class SeedService {

    private final AppProperties appProps;
    public SeedService(AppProperties appProps) { this.appProps = appProps; }

    public void saveSeed(String hexSeed) throws Exception {
        Path p = Path.of(appProps.getSeedFile());
        // ensure parent exists
        Files.createDirectories(p.getParent());
        Files.writeString(p, hexSeed, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public String readSeed() throws Exception {
        Path p = Path.of(appProps.getSeedFile());
        if (!Files.exists(p)) {
            throw new IllegalStateException("Seed not found");
        }
        return Files.readString(p).trim();
    }
}
