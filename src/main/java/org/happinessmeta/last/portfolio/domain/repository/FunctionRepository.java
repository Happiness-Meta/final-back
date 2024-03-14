package org.happinessmeta.last.portfolio.domain.repository;

import org.happinessmeta.last.portfolio.domain.entity.MyFunction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FunctionRepository extends JpaRepository<MyFunction, Long> {

}
