package org.happinessmeta.last.portfolio.domain.repository;

import org.happinessmeta.last.portfolio.domain.entity.ProjectFunction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FunctionRepository extends JpaRepository<ProjectFunction, Long> {

}
