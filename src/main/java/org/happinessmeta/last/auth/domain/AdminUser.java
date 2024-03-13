package org.happinessmeta.last.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("ADMIN")
public class AdminUser extends User{
    @Column(nullable = false, unique = true)
    private String name;

    @Builder
    public AdminUser(Long id, String email, String password, Role role, String name) {
        super(id, email, password, role);
        this.name = name;
    }
}