package org.happinessmeta.last.portfolio.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.happinessmeta.last.common.entity.BaseTimeEntity;
import org.happinessmeta.last.portfolio.dto.UpdatePortfolioComponentDto;
import org.happinessmeta.last.portfolio.dto.sub.FunctionDto;
import org.happinessmeta.last.portfolio.dto.sub.ProblemAndSolutionDto;
import org.happinessmeta.last.portfolio.dto.sub.RefLinkDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Table(name = "portfolio_component")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioComponent extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 공개,비공개
    private boolean visibility;

    // 포트폴리오 메인 컬러
    private String themeColor;

    // 제품명(고유)
    private String projectName;

    // 제품 설명
    @Lob
    private String description;

    // 작업 기간
    private LocalDateTime projectStartDate;
    private LocalDateTime projectEndDate;

    // 기술 스택
//    @Enumerated(value = EnumType.STRING)
//    @ElementCollection(fetch = FetchType.LAZY)
//    private List<TechStack> techStack;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> techStack;

    // 주요 기능
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> mainFunction;

    // 내가 구현한 기능
    @OneToMany(mappedBy = "portfolioComponent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Function> myFunction;

    // 링크
    @OneToMany(mappedBy = "portfolioComponent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefLink> links;

    // 기능 구현에 있어 발생한 문제와 그 해결 과정
    @OneToMany(mappedBy = "portfolioComponent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProblemAndSolution> problemAndSolutions;

    // 배운 점
    @Lob
    private String takeaway;


    @Builder
    public PortfolioComponent(boolean visibility, String themeColor, String projectName, String description,
                              LocalDateTime projectStartDate, LocalDateTime projectEndDate, List<String> techStack,
                              List<String> mainFunction, List<Function> myFunction,
                              List<RefLink> links, List<ProblemAndSolution> problemAndSolutions,
                              String takeaway) {
        this.visibility = visibility;
        this.themeColor = themeColor;
        this.projectName = projectName;
        this.description = description;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.techStack = techStack != null ? new ArrayList<>(techStack) : new ArrayList<>();
        this.mainFunction = mainFunction != null ? new ArrayList<>(mainFunction) : new ArrayList<>();
        this.myFunction = myFunction != null ? new ArrayList<>(myFunction) : new ArrayList<>();
        this.links = links != null ? new ArrayList<>(links) : new ArrayList<>();
        this.problemAndSolutions = problemAndSolutions != null ? new ArrayList<>(problemAndSolutions) : new ArrayList<>();
        this.takeaway = takeaway;
    }

    public void update(UpdatePortfolioComponentDto requestDto) {
        this.visibility = requestDto.visibility();
        this.themeColor = requestDto.themeColor();
        this.projectName = requestDto.projectName();
        this.description = requestDto.description();
        this.projectStartDate = requestDto.projectStartDate();
        this.projectEndDate = requestDto.projectEndDate();
        this.techStack = techStack != null ? new ArrayList<>(requestDto.techStack()) : new ArrayList<>();
        this.mainFunction = mainFunction != null ? new ArrayList<>(requestDto.mainFunction()) : new ArrayList<>();
        this.myFunction = myFunction != null ? new ArrayList<>(requestDto.myFunction().stream().map(FunctionDto::toEntity).collect(Collectors.toList())) : new ArrayList<>();
        this.links = links != null ? new ArrayList<>(requestDto.links().stream().map(RefLinkDto::toEntity).collect(Collectors.toList())) : new ArrayList<>();
        this.problemAndSolutions = problemAndSolutions != null ? new ArrayList<>(requestDto.problemAndSolutions().stream().map(ProblemAndSolutionDto::toEntity).collect(Collectors.toList())) : new ArrayList<>();
        this.takeaway = requestDto.takeaway();
    }
}
