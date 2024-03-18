package org.happinessmeta.last.portfolio.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "link_table")
@JsonIgnoreProperties("portfolioComponent")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefLink {
    @Id
    @GeneratedValue
    private Long id;

    // 배포주소, git 주소, 외부 주소
    private String description;

    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_component_id", nullable = false)
    private PortfolioComponent portfolioComponent;

    @Builder
    public RefLink(String description, String address, PortfolioComponent portfolioComponent) {
        this.address = address;
        this.description = description;
        this.portfolioComponent = portfolioComponent;
    }

    public void putPortfolioComponent(PortfolioComponent portfolioComponent) {
        this.portfolioComponent = portfolioComponent;
    }
}
