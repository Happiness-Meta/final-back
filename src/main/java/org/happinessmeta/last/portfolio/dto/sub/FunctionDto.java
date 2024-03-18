package org.happinessmeta.last.portfolio.dto.sub;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.happinessmeta.last.portfolio.domain.entity.ProjectFunction;

import java.io.Serializable;

/**
 * DTO for {@link ProjectFunction}
 */
public record FunctionDto(@NotNull String description, @Min(0) @Max(100) Integer contribution) implements Serializable {

    public ProjectFunction toEntity(){
        return ProjectFunction.builder()
                .description(description)
                .contribution(contribution)
                .build();
    }
}