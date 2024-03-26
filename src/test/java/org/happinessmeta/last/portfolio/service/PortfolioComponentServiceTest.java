package org.happinessmeta.last.portfolio.service;

import jakarta.persistence.EntityManager;
import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;
import org.happinessmeta.last.portfolio.domain.entity.RefLink;
import org.happinessmeta.last.portfolio.domain.repository.PortfolioComponentRepository;
import org.happinessmeta.last.portfolio.dto.response.PortfolioComponentResponse;
import org.happinessmeta.last.user.domain.User;
import org.happinessmeta.last.user.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {

        User currUser = userRepository.save(
                User.builder()
                        .address("a")
                        .name("n")
                        .industry("!")
                        .techStack(new ArrayList<>())
                        .password("123")
                        .telephone("123")
                        .email("123@")
                        .roles(new ArrayList<>())
                        .build()
        );
        String name = "Palette*";
        String color = "#C1C1C1";
        boolean visibility = true;

        List<PortfolioComponent> ps = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PortfolioComponent portfolioComponent = PortfolioComponent.builder()
                    .user(currUser)
                    .projectName(name + i)
                    .themeColor(color)
                    .isContained(visibility)
                    .techStack(new ArrayList<>())
                    .projectFunction(new ArrayList<>())
                    .link(new ArrayList<>())
                    .problemAndSolution(new ArrayList<>())
                    .build();

            ps.add(portfolioComponent);
        }

        portfolioComponentRepository.saveAll(ps);
    }

    @AfterEach
    void tearDown() {
//        userRepository.deleteAll();
        portfolioComponentRepository.deleteAll();
    }



    @Test
    @DisplayName("여러개를_조회시_Subject가_N1_쿼리가발생한다?????()")
    public void saveAndFindAll() throws Exception {
        // given
        User user = userRepository.findByEmail("123@").orElseThrow(() -> new RuntimeException("이런,,,"));
        List<PortfolioComponent> portfolioComponentList = portfolioComponentService.findAllPortfolio(user);


        // then
        assertThat(portfolioComponentList.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("fetch_join을_사용하면()")
    public void saveAndFindAllFetchJoin() throws Exception {
        // given
        User user = userRepository.findByEmail("123@").orElseThrow(() -> new RuntimeException("이런,,,"));
        List<PortfolioComponentResponse> portfolioComponentList = portfolioComponentService.findAllPortfolioComponent(user);

        // then
        assertThat(portfolioComponentList.size()).isEqualTo(10);
    }
}