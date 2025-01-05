package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="users")
public class User extends BaseEntity implements UserDetails {

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

    @Column(name="day_of_birth")
    private Date dateOfBirth;

    @Column(name="facebook_account_id")
    private int facebookAccountId;

    @Column(name="google_account_id")
    private int googleAccountId;

    @ManyToOne
    @JoinColumn(name ="role_id")
    private Role role;

    // Lay ra cac quyen cua user//
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+getRole().getName().toUpperCase()));
//        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    // Username la truong co gia tri dung de dang nhap //
    @Override
    public String getUsername() {
        return getPhoneNumber();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
