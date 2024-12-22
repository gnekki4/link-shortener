package ru.gnekki4.linkshortener.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

@MappedSuperclass
public class AuditableEntity {

    public static final String DEFAULT_USER = "app";

    private String createUser;
    private String lastUpdateUser;
    private LocalDateTime createTime;
    private LocalDateTime lastUpdateTime;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createTime = now;
        this.lastUpdateTime = now;
        this.createUser = DEFAULT_USER;
        this.lastUpdateUser = DEFAULT_USER;

    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdateUser = DEFAULT_USER;
        this.lastUpdateTime = LocalDateTime.now();
    }

}