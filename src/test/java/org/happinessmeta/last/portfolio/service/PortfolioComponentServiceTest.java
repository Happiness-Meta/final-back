package org.happinessmeta.last.portfolio.service;

import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;
import org.happinessmeta.last.portfolio.domain.repository.PortfolioComponentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PortfolioComponentServiceTest {

    @Autowired
    private PortfolioComponentService portfolioComponentService;

    @Autowired
    private PortfolioComponentRepository portfolioComponentRepository;

    @BeforeEach
    @WithMockUser
    void setUp() {
        String name = "Palette*";
        String color = "#C1C1C1";
        boolean visibility = true;

        List<PortfolioComponent> ps = new ArrayList<>();
        for(int i=0;i<10;i++){
            PortfolioComponent portfolioComponent = PortfolioComponent.builder()
                    .user(user)
                    .projectName(name + i)
                    .themeColor(color)
                    .isContained(visibility)
                    .build();

            ps.add(portfolioComponent);
        }

        portfolioComponentRepository.saveAll(ps);
    }

    @AfterEach
    void tearDown() {
        portfolioComponentRepository.deleteAll();
    }

    @Test
    @DisplayName("여러개를_조회시_Subject가_N1_쿼리가발생한다()")
    public void saveAndFindAll()  throws Exception {
        // given
        List<PortfolioComponent> portfolioComponentList = portfolioComponentService.findAllPortfolio()


        // then
        assertThat(portfolioComponentList.size()).isEqualTo(10);
    }
}