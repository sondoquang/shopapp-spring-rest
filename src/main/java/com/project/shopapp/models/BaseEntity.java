package com.project.shopapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Column(name="create_at")
    private LocalDate createdAt;

    @Column(name="update_at")
    private LocalDate updateAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
        this.updateAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateAt = LocalDate.now();
    }
    public BaseEntity(){}

    public BaseEntity(LocalDate createdAt, LocalDate updateAt) {
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }

}
