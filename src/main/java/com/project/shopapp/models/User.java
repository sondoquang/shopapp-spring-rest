package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname", length = 100)
    private String fullName;

    @Column(name="phone_number", nullable = false, length = 10)
    private String phoneNumber;

    @Column(name="address", length = 200)
    private String address;

    @Column(name="password", length = 200, nullable = true)
    private String password;

    @Column(name="is_active")
    private boolean isActive;

    @Column(name="date_of_birth")
    private Date dateOfBirth;

    @Column(name="facebook_account _id")
    private int facebookAccountId;

    @Column(name="google_account _id")
    private int googleAccountId;

    @ManyToOne
    @JoinColumn(name ="role_id")
    private Role role;

    public User( String fullName, String phoneNumber, String address, String password, boolean isActive, Date dateOfBirth, int facebookAccountId, int googleAccountId, Role role) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
        this.isActive = isActive;
        this.dateOfBirth = dateOfBirth;
        this.facebookAccountId = facebookAccountId;
        this.googleAccountId = googleAccountId;
        this.role = role;
    }

    public User( String fullName, String phoneNumber, String address, String password, boolean isActive, Date dateOfBirth, int facebookAccountId, int googleAccountId, Role role,LocalDate createdAt, LocalDate updateAt) {
        super(createdAt, updateAt);
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
        this.isActive = isActive;
        this.dateOfBirth = dateOfBirth;
        this.facebookAccountId = facebookAccountId;
        this.googleAccountId = googleAccountId;
        this.role = role;
    }
}
