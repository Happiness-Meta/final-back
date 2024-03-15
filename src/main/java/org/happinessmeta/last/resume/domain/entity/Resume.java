package org.happinessmeta.last.resume.domain.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.happinessmeta.last.common.entity.BaseTimeEntity;
import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;
import org.happinessmeta.last.user.domain.User;

import java.util.List;

@Entity
@Getter
@Table(name = "resume")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Resume extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//     이력서 주인 -> 사용자
     @OneToOne
     @JoinColumn(name = "user_id")
     private User user;

//    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<PortfolioComponent> portfolioComponents;

    // 본명
    String name;

    // 학력,
    // Bachelor of Science in Computer Engineering
    @ElementCollection(fetch = FetchType.LAZY)
    List<String> education;

    //자격증
    @ElementCollection(fetch = FetchType.LAZY)
    List<String> certificate;

    //  교육
    @ElementCollection(fetch = FetchType.LAZY)
    List<String> activities;

    // 수상 내역
    @ElementCollection(fetch = FetchType.LAZY)
    List<String> awards;

    public void putUser(User user){
        this.user = user;
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
