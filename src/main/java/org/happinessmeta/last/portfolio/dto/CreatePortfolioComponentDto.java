package org.happinessmeta.last.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;
import org.happinessmeta.last.portfolio.dto.sub.FunctionDto;
import org.happinessmeta.last.portfolio.dto.sub.ProblemAndSolutionDto;
import org.happinessmeta.last.portfolio.dto.sub.RefLinkDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


import static org.happinessmeta.last.portfolio.utils.PortfolioUtils.createPortfolioComponentEntity;

/**
 * DTO for {@link PortfolioComponent}
 */
@Builder
public record CreatePortfolioComponentDto(boolean visibility,
                                          @NotBlank String themeColor,
                                          @NotBlank String projectName,
                                          @NotNull String description,
                                          @NotNull LocalDateTime projectStartDate,
                                          @NotNull LocalDateTime projectEndDate,
                                          @NotNull List<String> techStack,
                                          @NotNull List<String> mainFunction,
                                          List<FunctionDto> myFunction,
                                          List<RefLinkDto> links,
                                          List<ProblemAndSolutionDto> problemAndSolutions,
                                          String takeaway) implements Serializable {

    public PortfolioComponent toEntity() {
        return createPortfolioComponentEntity(visibility, themeColor, projectName, description, projectStartDate, projectEndDate, techStack, mainFunction, myFunction, links, problemAndSolutions, takeaway);
    }
}