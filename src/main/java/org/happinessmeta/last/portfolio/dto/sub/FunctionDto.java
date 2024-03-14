package org.happinessmeta.last.portfolio.dto.sub;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.happinessmeta.last.portfolio.domain.entity.MyFunction;

import java.io.Serializable;

/**
 * DTO for {@link MyFunction}
 */
public record FunctionDto(@NotNull String description, @Min(0) @Max(100) Integer contribution) implements Serializable {

    public MyFunction toEntity(){
        return MyFunction.builder()
                .description(description)
                .contribution(contribution)
                .build();
    }
}