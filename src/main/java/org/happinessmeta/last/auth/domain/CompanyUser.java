package org.happinessmeta.last.auth.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.List;

@NoArgsConstructor
@Entity
@DiscriminatorValue("COMPANY")
public class CompanyUser extends User{
    @Column(nullable = false, unique = true)
    private String companyName;
    private String industry;
    private Boolean subscribed;
    @Builder
    public CompanyUser(Long id, String email, String password, Role role, String companyName, String industry, Boolean subscribed) {
        super(id, email, password, role);
        this.companyName = companyName;
        this.industry = industry;
        this.subscribed = subscribed;
    }
}