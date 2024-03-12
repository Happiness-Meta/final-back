package org.happinessmeta.last.auth.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@Entity
@DiscriminatorValue("COMPANY")
public class CompanyUser extends User{
    @Column(nullable = false, unique = true)
    private String name;
    private String industry;
    private Boolean subscribed;
}