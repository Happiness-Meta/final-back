package org.happinessmeta.last.portfolio.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "sol_table")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemAndSolution {
    @Id
    @GeneratedValue
    private Long id;
    private String definition;
    private String reason;
    private String solution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_component_id", nullable = false)
    private PortfolioComponent portfolioComponent;

    @Builder
    public ProblemAndSolution(String definition, String reason, String solution) {
        this.definition = definition;
        this.reason = reason;
        this.solution = solution;
    }
}
