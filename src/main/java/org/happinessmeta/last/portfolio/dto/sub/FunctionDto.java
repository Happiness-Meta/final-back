package org.happinessmeta.last.portfolio.dto.sub;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.happinessmeta.last.portfolio.domain.entity.Function;
import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;

import java.io.Serializable;

/**
 * DTO for {@link Function}
 */
public record FunctionDto(@NotNull String description, @Min(0) @Max(100) Integer contribution) implements Serializable {

    public Function toEntity(){
        return Function.builder()
                .description(description)
                .contribution(contribution)
                .build();
    }
}