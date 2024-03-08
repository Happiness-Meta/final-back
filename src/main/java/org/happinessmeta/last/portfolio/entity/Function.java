package org.happinessmeta.last.portfolio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "function_table")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Function {

    @Id
    @GeneratedValue
    private Long id;

    private String description;

    @Column(nullable = false)
    @Min(0)
    @Max(100)
    private Integer contribution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_component_id", nullable = false)
    private PortfolioComponent portfolioComponent;
}
