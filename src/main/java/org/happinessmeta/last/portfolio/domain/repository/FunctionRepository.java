package org.happinessmeta.last.portfolio.domain.repository;

import org.happinessmeta.last.portfolio.domain.entity.ProjectFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FunctionRepository extends JpaRepository<ProjectFunction, Long> {
    @Query("select f FROM ProjectFunction f join RefLink l on f.portfolioComponent = l.portfolioComponent")
    List<ProjectFunction> findJoinWithRefLink();

    @Query("select f FROM ProjectFunction f left join RefLink l on f.portfolioComponent = l.portfolioComponent")
    List<ProjectFunction> findLOuterJoinWithRefLink();

    @Query("select f FROM ProjectFunction f right join RefLink l on f.portfolioComponent = l.portfolioComponent")
    List<ProjectFunction> findROuterJoinWithRefLink();

}
