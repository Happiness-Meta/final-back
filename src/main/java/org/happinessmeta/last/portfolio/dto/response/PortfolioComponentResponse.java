package org.happinessmeta.last.portfolio.dto.response;

import lombok.Builder;
import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;
import org.happinessmeta.last.portfolio.dto.sub.FunctionDto;
import org.happinessmeta.last.portfolio.dto.sub.ProblemAndSolutionDto;
import org.happinessmeta.last.portfolio.dto.sub.RefLinkDto;
import org.happinessmeta.last.user.dto.UserResponse;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link PortfolioComponent}
 */
@Builder
public record PortfolioComponentResponse(LocalDateTime lastModifiedAt,
                                         Long id,
                                         boolean isContained,
                                         String themeColor,
                                         String projectName,
                                         String description,
                                         LocalDate projectStartDate,
                                         LocalDate projectEndDate,
                                         List<String> techStack,
                                         List<FunctionDto> projectFunction,
                                         List<RefLinkDto> link,
                                         List<ProblemAndSolutionDto> problemAndSolution,
                                         String takeaway, int personnel,
                                         PortfolioComponentUserDto user) implements Serializable {
}