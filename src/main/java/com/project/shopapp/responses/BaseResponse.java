package com.project.shopapp.responses;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseResponse {
    @Column(name="create_at")
    private LocalDate createdAt;

    @Column(name="update_at")
    private LocalDate updateAt;
}
