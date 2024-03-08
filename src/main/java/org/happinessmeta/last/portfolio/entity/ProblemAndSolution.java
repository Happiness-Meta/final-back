package org.happinessmeta.last.portfolio.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "sol_table")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemAndSolution {
    @Id
    private Long id;
    private String definition;
    private String solution;
    private String resolution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_component_id", nullable = false)
    private PortfolioComponent portfolioComponent;
}
