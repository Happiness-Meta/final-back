package org.happinessmeta.last.portfolio.domain.repository;

import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PortfolioComponentRepositoryTest {

    @Autowired
    PortfolioComponentRepository repository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("포트폴리오 요소 저장_불러오기")
    public void saveAndFindAll(){
        // given
        String name = "Palette*";
        String color = "#C1C1C1";
        boolean visibility = true;

        repository.save(
                PortfolioComponent.builder()
                        .projectName(name)
                        .themeColor(color)
                        .isContained(visibility)
                        .build()
        );

        // when
        List<PortfolioComponent> portfolioComponentList = repository.findAll();

        // then
        PortfolioComponent component = portfolioComponentList.get(0);
//        System.out.println(">>>>>>>>>>>>>>" + component.getId().toString());
        assertThat(component.getProjectName()).isEqualTo(name);
        assertThat(component.getThemeColor()).isEqualTo(color);
    }
}