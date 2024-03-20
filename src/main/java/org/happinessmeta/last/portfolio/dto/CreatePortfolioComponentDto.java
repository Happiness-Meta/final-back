package org.happinessmeta.last.portfolio.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;
import org.happinessmeta.last.portfolio.dto.sub.FunctionDto;
import org.happinessmeta.last.portfolio.dto.sub.ProblemAndSolutionDto;
import org.happinessmeta.last.portfolio.dto.sub.RefLinkDto;
import org.happinessmeta.last.user.domain.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


import static org.happinessmeta.last.portfolio.utils.PortfolioUtils.createPortfolioComponentEntity;

/**
 * DTO for {@link PortfolioComponent}
 */
@Builder
public record CreatePortfolioComponentDto(boolean isContained,
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
                                          List<FunctionDto> projectFunction,
                                          List<RefLinkDto> link,
                                          List<ProblemAndSolutionDto> problemAndSolution,
                                          String takeaway, int teamMember) implements Serializable {

    @AssertTrue(message = "End date must be after start date")
    public boolean isEndDateAfterStartDate() {
        return projectEndDate.isEqual(projectStartDate) || projectEndDate.isAfter(projectStartDate);
    }

    public PortfolioComponent toEntity(User user) {
        return createPortfolioComponentEntity(isContained, themeColor, projectName, description, projectStartDate, projectEndDate, techStack, projectFunction, link, problemAndSolution, takeaway, teamMember, user
        );
    }
}