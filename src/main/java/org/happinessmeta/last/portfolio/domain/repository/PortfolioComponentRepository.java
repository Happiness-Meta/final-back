package org.happinessmeta.last.portfolio.domain.repository;

import jakarta.persistence.QueryHint;
import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;
import org.happinessmeta.last.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static org.hibernate.jpa.HibernateHints.HINT_CACHEABLE;

public interface PortfolioComponentRepository extends JpaRepository<PortfolioComponent, Long> {

    @QueryHints(value = { @QueryHint(name = HINT_CACHEABLE, value = "true") })
    @Query("select distinct p from PortfolioComponent p join fetch p.user u where u = :user")
    List<PortfolioComponent> findAllByUserFetch(@Param("user") User user);

    List<PortfolioComponent> findAllByUser(User user);

    @Query("select p from PortfolioComponent p join fetch p.user u where u = :user and p.isContained = true and p is not null")
    List<PortfolioComponent> findAllByUserAndVisibilityIsTrue(@Param("user") User user);
}
