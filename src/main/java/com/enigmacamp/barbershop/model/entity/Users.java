package com.enigmacamp.barbershop.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "m_users")
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    
    @OneToOne(mappedBy = "userId")
    private Barbers barbers;

    @OneToOne(mappedBy = "userId")
    private Customer customer;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false,updatable = false)
    private Long createdAt;

    @Column(name = "update_at", nullable = false)
    private Long updateAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Role> role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.stream().map(role -> new SimpleGrantedAuthority(role.getRole().name())).toList();
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
