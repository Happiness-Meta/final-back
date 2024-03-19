package org.happinessmeta.last.portfolio.domain.repository;

import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;
import org.happinessmeta.last.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PortfolioComponentRepository extends JpaRepository<PortfolioComponent, Long> {

    @Query("select p from PortfolioComponent p join fetch p.user u where u = :user and p is not null")
    List<PortfolioComponent> findAllByUser(@Param("user") User user);

    @Query("select p from PortfolioComponent p join fetch p.user u where u = :user and p.visibility = true and p is not null")
    List<PortfolioComponent> findAllByUserAndVisibilityIsTrue(@Param("user") User user);
}
