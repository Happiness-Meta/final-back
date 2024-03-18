package org.happinessmeta.last.portfolio.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.happinessmeta.last.common.entity.BaseTimeEntity;
import org.happinessmeta.last.portfolio.dto.UpdatePortfolioComponentDto;
import org.happinessmeta.last.portfolio.dto.sub.FunctionDto;
import org.happinessmeta.last.portfolio.dto.sub.ProblemAndSolutionDto;
import org.happinessmeta.last.portfolio.dto.sub.RefLinkDto;
import org.happinessmeta.last.user.domain.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Table(name = "portfolio_component")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties("resume")
@Entity
public class PortfolioComponent extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_component_id")
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate projectStartDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate projectEndDate;

    // 기술 스택
//    @Enumerated(value = EnumType.STRING)
//    @ElementCollection(fetch = FetchType.LAZY)
//    private List<TechStack> techStack;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "portfolio_component_techStack", joinColumns = @JoinColumn(name = "portfolio_component_id"))
    private List<String> techStack;

    // 내가 구현한 기능
    @OneToMany(mappedBy = "portfolioComponent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectFunction> projectFunction = new ArrayList<>();

    // 링크
    @OneToMany(mappedBy = "portfolioComponent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefLink> links = new ArrayList<>();;

    // 기능 구현에 있어 발생한 문제와 그 해결 과정
    @OneToMany(mappedBy = "portfolioComponent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProblemAndSolution> problemAndSolutions = new ArrayList<>();;

    // 배운 점
    @Lob
    private String takeaway;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "resume_id")
//    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Builder
    public PortfolioComponent(boolean visibility, String themeColor, String projectName, String description,
                              LocalDate projectStartDate, LocalDate projectEndDate, List<String> techStack,
                              List<ProjectFunction> projectFunction,
                              List<RefLink> links, List<ProblemAndSolution> problemAndSolutions,
                              String takeaway
                              ,User user
    ) {
        this.visibility = visibility;
        this.themeColor = themeColor;
        this.projectName = projectName;
        this.description = description;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.techStack = techStack != null ? new ArrayList<>(techStack) : new ArrayList<>();
        this.projectFunction = projectFunction != null ? projectFunction.stream()
                .peek(func -> func.putPortfolioComponent(this))
                .collect(Collectors.toList()) : new ArrayList<>();
        this.links = links != null ? links.stream()
                .peek(func -> func.putPortfolioComponent(this))
                .collect(Collectors.toList()) : new ArrayList<>();
        this.problemAndSolutions = problemAndSolutions != null ? problemAndSolutions.stream()
                .peek(func -> func.putPortfolioComponent(this))
                .collect(Collectors.toList()) : new ArrayList<>();
        this.takeaway = takeaway;
        this.user = user;
    }

    public void updateComponent(UpdatePortfolioComponentDto requestDto, PortfolioComponent targetComponent, User user) {
        this.visibility = requestDto.visibility();
        this.themeColor = requestDto.themeColor();
        this.projectName = requestDto.projectName();
        this.description = requestDto.description();
        this.projectStartDate = requestDto.projectStartDate();
        this.projectEndDate = requestDto.projectEndDate();
        if (techStack != null) {
            putTechStack(requestDto.techStack());
        } else {
            this.techStack = new ArrayList<>();
        }
        if (links != null) {
            this.links.clear();
            List<RefLink> newLinks = requestDto.links().stream()
                    .map(RefLinkDto::toEntity)
                    .peek(link -> link.putPortfolioComponent(targetComponent))
                    .collect(Collectors.toList());
            this.links.addAll(newLinks);
        } else {
            this.links = new ArrayList<>();
        }
        if (projectFunction != null) {
            this.projectFunction.clear();
            List<ProjectFunction> newProjectFunction = requestDto.projectFunction().stream()
                    .map(FunctionDto::toEntity)
                    .peek(func -> func.putPortfolioComponent(targetComponent))
                    .collect(Collectors.toList());
            this.projectFunction.addAll(newProjectFunction);
        } else {
            this.projectFunction = new ArrayList<>();
        }
        if (problemAndSolutions != null) {
            this.problemAndSolutions.clear();
            List<ProblemAndSolution> newProblemsAndSolutions = requestDto.problemAndSolutions().stream()
                    .map(ProblemAndSolutionDto::toEntity)
                    .peek(pns -> pns.putPortfolioComponent(targetComponent))
                    .collect(Collectors.toList());
            this.problemAndSolutions.addAll(newProblemsAndSolutions);
        } else {
            this.problemAndSolutions = new ArrayList<>();
        }

        this.takeaway = requestDto.takeaway();
        this.user = user;
    }

    public void putTechStack(List<String> techStack) {
        this.techStack = techStack;
    }


    public void putFunctions(List<ProjectFunction> projectFunctions) {
        this.projectFunction = projectFunctions;
    }

    public void putLinks(List<RefLink> links) {
        this.links = links;
    }

    public void putProblemsAndSolutions(List<ProblemAndSolution> problemsAndSolutions) {
        this.problemAndSolutions = problemsAndSolutions;
    }

    public void putUser(User user) {
        this.user = user;
    }
}
