package com.example.Secureservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String seedFile;
    private String cronOutput;
    private String timezone;
    private Totp totp = new Totp();

    public static class Totp {
        private int digits;
        private int period;
        public int getDigits() { return digits; }
        public void setDigits(int digits) { this.digits = digits; }
        public int getPeriod() { return period; }
        public void setPeriod(int period) { this.period = period; }
    }

    public String getSeedFile() { return seedFile; }
    public void setSeedFile(String seedFile) { this.seedFile = seedFile; }
    public String getCronOutput() { return cronOutput; }
    public void setCronOutput(String cronOutput) { this.cronOutput = cronOutput; }
    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }
    public Totp getTotp() { return totp; }
    public void setTotp(Totp totp) { this.totp = totp; }
}

