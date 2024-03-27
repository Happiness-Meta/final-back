package org.happinessmeta.last.portfolio.utils;

import lombok.RequiredArgsConstructor;
import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;
import org.happinessmeta.last.portfolio.domain.entity.ProblemAndSolution;
import org.happinessmeta.last.portfolio.domain.entity.ProjectFunction;
import org.happinessmeta.last.portfolio.domain.entity.RefLink;
import org.happinessmeta.last.portfolio.dto.response.PortfolioComponentResponse;
import org.happinessmeta.last.portfolio.dto.response.PortfolioComponentUserDto;
import org.happinessmeta.last.portfolio.dto.sub.FunctionDto;
import org.happinessmeta.last.portfolio.dto.sub.ProblemAndSolutionDto;
import org.happinessmeta.last.portfolio.dto.sub.RefLinkDto;
import org.happinessmeta.last.user.domain.User;
import org.happinessmeta.last.user.dto.UserResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PortfolioUtils {
    @NotNull
    public static PortfolioComponentResponse createPortfolioComponentDto(PortfolioComponent portfolioComponent) {
        List<FunctionDto> functionDtos = portfolioComponent.getProjectFunction().stream()
                .map(ProjectFunction::toDto)
                .collect(Collectors.toList());
        List<RefLinkDto> linkDtos = portfolioComponent.getLink().stream()
                .map(RefLink::toDto)
                .collect(Collectors.toList());
        List<ProblemAndSolutionDto> problemAndSolutionDtos = portfolioComponent.getProblemAndSolution().stream()
                .map(ProblemAndSolution::toDto)
                .collect(Collectors.toList());
        User target = portfolioComponent.getUser();
        PortfolioComponentUserDto userDto = PortfolioComponentUserDto.builder()
                .id(target.getId())
                .roles(target.getRoles())
                .email(target.getEmail())
                .name(target.getName())
                .techStack(target.getTechStack())
                .build();

        return portfolioComponent.toDto(functionDtos, linkDtos, problemAndSolutionDtos, userDto);
    }
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
                                                                    int personnel,
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
                .personnel(personnel)
                .build();
    }
}
