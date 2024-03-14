package org.happinessmeta.last.portfolio.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;
import org.happinessmeta.last.portfolio.dto.sub.FunctionDto;
import org.happinessmeta.last.portfolio.dto.sub.ProblemAndSolutionDto;
import org.happinessmeta.last.portfolio.dto.sub.RefLinkDto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.happinessmeta.last.portfolio.utils.PortfolioUtils.createPortfolioComponentEntity;

@Builder
public record UpdatePortfolioComponentDto(boolean visibility,
                                          @NotBlank(message = "The themeColor is required.")
                                          String themeColor,
                                          @NotBlank(message = "The projectName is required.")
                                          String projectName,
                                          @NotNull(message = "The description is required.")
                                          String description,
                                          @NotNull(message = "The projectStartDate is required.")
                                          LocalDate projectStartDate,
                                          @NotNull(message = "The projectEndDate is required.")
                                          LocalDate projectEndDate,
                                          @NotNull(message = "The techStack is required.")
                                          List<String> techStack,
                                          @NotNull(message = "The mainFunction is required.")
                                          List<String> mainFunction,
                                          List<FunctionDto> myFunction,
                                          List<RefLinkDto> links,
                                          List<ProblemAndSolutionDto> problemAndSolutions,
                                          String takeaway) implements Serializable {

    @AssertTrue(message = "End date must be after start date")
    public boolean isEndDateAfterStartDate() {
        return projectEndDate.isEqual(projectStartDate) || projectEndDate.isAfter(projectStartDate);
    }
    public PortfolioComponent toEntity() {
        return createPortfolioComponentEntity(visibility, themeColor, projectName, description, projectStartDate, projectEndDate, techStack, mainFunction, myFunction, links, problemAndSolutions, takeaway);
    }
}
