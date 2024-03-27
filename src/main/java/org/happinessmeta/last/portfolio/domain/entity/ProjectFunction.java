package org.happinessmeta.last.portfolio.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.happinessmeta.last.portfolio.dto.sub.FunctionDto;

@Entity
@Getter
@Table(name = "function_table")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties("portfolioComponent")
public class ProjectFunction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "function_table_id")
    private Long id;

    private String description;

    @Column(nullable = false)
    @Min(0)
    @Max(100)
    private Integer contribution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_component_id", nullable = false)
    private PortfolioComponent portfolioComponent;

    @Builder
    public ProjectFunction(String description, Integer contribution, PortfolioComponent portfolioComponent){
        this.contribution = contribution;
        this.description = description;
        this.portfolioComponent = portfolioComponent;
    }

    public void putPortfolioComponent(PortfolioComponent portfolioComponent) {
        this.portfolioComponent = portfolioComponent;
    }

    public FunctionDto toDto() {
        return FunctionDto.builder()
                .contribution(this.contribution)
                .description(this.description)
                .build();
    }
}
