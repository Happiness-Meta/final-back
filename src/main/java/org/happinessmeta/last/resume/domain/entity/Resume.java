package org.happinessmeta.last.resume.domain.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.happinessmeta.last.common.entity.BaseTimeEntity;
import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;

import java.util.List;

@Entity
@Getter
@Table(name = "resume")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Resume extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이력서 주인 -> 사용자
    // private User user;

//    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<PortfolioComponent> portfolioComponents;

    // 본명

    // 학력, 자격증, 교육, 수상 내역

    @Builder
    public Resume(
//            BasicUser user,
            List<PortfolioComponent> portfolioComponents
    ) {
//        this.user = user;
//        this.portfolioComponents = portfolioComponents;
    }
}
