package org.happinessmeta.last.portfolio.dto.sub;

import lombok.Builder;
import org.happinessmeta.last.portfolio.domain.entity.ProblemAndSolution;

import java.io.Serializable;

/**
 * DTO for {@link ProblemAndSolution}
 */
@Builder
public record ProblemAndSolutionDto(String definition, String solution, String reason) implements Serializable {
    public ProblemAndSolution toEntity() {
        return ProblemAndSolution.builder()
                .definition(definition)
                .reason(reason)
                .solution(solution)
                .build();
    }
}