package org.happinessmeta.last.portfolio.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "sol_table")
@JsonIgnoreProperties("portfolioComponent")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemAndSolution {
    @Id
    @GeneratedValue
    @Column(name = "sol_table_id")
    private Long id;
    private String definition;
    private String reason;
    private String solution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_component_id", nullable = false)
    private PortfolioComponent portfolioComponent;

    @Builder
    public ProblemAndSolution(String definition, String reason, String solution, PortfolioComponent portfolioComponent) {
        this.definition = definition;
        this.reason = reason;
        this.solution = solution;
        this.portfolioComponent = portfolioComponent;
    }

    public void putPortfolioComponent(PortfolioComponent portfolioComponent) {
        this.portfolioComponent = portfolioComponent;
    }
}
