package org.happinessmeta.last.portfolio.utils;

import lombok.RequiredArgsConstructor;
import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;
import org.happinessmeta.last.portfolio.dto.sub.FunctionDto;
import org.happinessmeta.last.portfolio.dto.sub.ProblemAndSolutionDto;
import org.happinessmeta.last.portfolio.dto.sub.RefLinkDto;
import org.happinessmeta.last.user.domain.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PortfolioUtils {
    public static PortfolioComponent createPortfolioComponentEntity(boolean isContained,
                                                                    String themeColor,
                                                                    String projectName,
                                                                    String description,
                                                                    LocalDate projectStartDate,
                                                                    LocalDate projectEndDate,
                                                                    List<String> techStack,
                                                                    List<FunctionDto> projectFunction,
                                                                    List<RefLinkDto> links,
                                                                    List<ProblemAndSolutionDto> problemAndSolutions,
                                                                    String takeaway,
                                                                    int teamMember,
                                                                    User user
    ) {
        return PortfolioComponent.builder()
                .isContained(isContained)
                .themeColor(themeColor)
                .projectName(projectName)
                .description(description)
                .projectStartDate(projectStartDate)
                .projectEndDate(projectEndDate)
                .techStack(techStack)
                .projectFunction(projectFunction.stream().map(FunctionDto::toEntity).collect(Collectors.toList()))
                .link(links.stream().map(RefLinkDto::toEntity).collect(Collectors.toList()))
                .problemAndSolution(problemAndSolutions.stream().map(ProblemAndSolutionDto::toEntity).collect(Collectors.toList()))
                .takeaway(takeaway)
                .user(user)
                .teamMember(teamMember)
                .build();
    }
}
