package com.example.Secureservice.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "keys")
public class KeyProperties {
    private String studentPrivate;
    private String studentPublic;
    private String instructorPublic;

    public String getStudentPrivate() { return studentPrivate; }
    public void setStudentPrivate(String studentPrivate) { this.studentPrivate = studentPrivate; }
    public String getStudentPublic() { return studentPublic; }
    public void setStudentPublic(String studentPublic) { this.studentPublic = studentPublic; }
    public String getInstructorPublic() { return instructorPublic; }
    public void setInstructorPublic(String instructorPublic) { this.instructorPublic = instructorPublic; }
}
