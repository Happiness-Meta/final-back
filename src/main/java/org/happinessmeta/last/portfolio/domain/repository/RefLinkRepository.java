package org.happinessmeta.last.portfolio.domain.repository;

import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;
import org.happinessmeta.last.portfolio.domain.entity.RefLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefLinkRepository extends JpaRepository<RefLink, Long> {
    List<RefLink> findAllByPortfolioComponent(PortfolioComponent portfolioComponent);
}
