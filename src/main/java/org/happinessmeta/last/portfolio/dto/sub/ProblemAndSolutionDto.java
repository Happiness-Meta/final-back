package org.happinessmeta.last.portfolio.dto.sub;

import org.happinessmeta.last.portfolio.domain.entity.ProblemAndSolution;

import java.io.Serializable;

/**
 * DTO for {@link ProblemAndSolution}
 */
public record ProblemAndSolutionDto(String definition, String solution) implements Serializable {
    public ProblemAndSolution toEntity() {
        return ProblemAndSolution.builder()
                .definition(definition)
                .solution(solution)
                .build();
    }
}