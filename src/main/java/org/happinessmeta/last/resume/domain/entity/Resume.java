package org.happinessmeta.last.resume.domain.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.happinessmeta.last.common.entity.BaseTimeEntity;
import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;
import org.happinessmeta.last.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "resume")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Resume extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="resume_id")
    private Long id;

//     이력서 주인 -> 사용자
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<PortfolioComponent> portfolioComponents;

    // 본명
    private String name;

    // 학력,
    // Bachelor of Science in Computer Engineering
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "resume_education", joinColumns = @JoinColumn(name = "resume_id"))
    private List<String> education = new ArrayList<>();

    //자격증
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "resume_certificate", joinColumns = @JoinColumn(name = "resume_id"))
    private List<String> certificate = new ArrayList<>();

    //  교육
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "resume_activities", joinColumns = @JoinColumn(name = "resume_id"))
    private List<String> activities = new ArrayList<>();

    // 수상 내역
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "resume_awards", joinColumns = @JoinColumn(name = "resume_id"))
    private List<String> awards = new ArrayList<>();

    public void putUser(User user){
        this.user = user;
    }

    public void putPortfolio(PortfolioComponent portfolioComponent){
        portfolioComponents.add(portfolioComponent);
    }

    @Builder
    public Resume(
            User user,
            String name,
//            List<PortfolioComponent> portfolioComponents,
            List<String> education,
            List<String> certificate,
            List<String> activities,
            List<String> awards
    ) {
        this.user = user;
//        this.portfolioComponents = portfolioComponents;
        this.name = name;
        this.education = education;
        this.certificate = certificate;
        this.activities = activities;
        this.awards = awards;
    }
}
