package org.happinessmeta.last.portfolio.domain.repository;

import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;
import org.happinessmeta.last.portfolio.domain.entity.ProjectFunction;
import org.happinessmeta.last.portfolio.domain.entity.RefLink;
import org.happinessmeta.last.user.domain.User;
import org.happinessmeta.last.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FunctionRefLinkJoinTest {
    @Autowired
    PortfolioComponentRepository portfolioComponentRepository;

    @Autowired
    FunctionRepository functionRepository;

    @Autowired
    RefLinkRepository linkRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {

        User user = User.builder()
                .address("a")
                .name("n")
                .industry("!")
                .techStack(new ArrayList<>())
                .password("123")
                .telephone("123")
                .email("123@")
                .roles(new ArrayList<>())
                .build();

        User currUser = userRepository.save(user);
        PortfolioComponent p1 = PortfolioComponent.builder()
                .user(currUser)
                .techStack(new ArrayList<>())
                .projectFunction(new ArrayList<>())
                .link(new ArrayList<>())
                .problemAndSolution(new ArrayList<>())
                .build();
        PortfolioComponent p2 = PortfolioComponent.builder()
                .user(currUser)
                .techStack(new ArrayList<>())
                .projectFunction(new ArrayList<>())
                .link(new ArrayList<>())
                .problemAndSolution(new ArrayList<>())
                .build();
        PortfolioComponent p3 = PortfolioComponent.builder()
                .user(currUser)
                .techStack(new ArrayList<>())
                .projectFunction(new ArrayList<>())
                .link(new ArrayList<>())
                .problemAndSolution(new ArrayList<>())
                .build();
        portfolioComponentRepository.save(p1);
        portfolioComponentRepository.save(p2);
        portfolioComponentRepository.save(p3);



        RefLink link = RefLink.builder()
                .address("www.link.io")
                .description("wanna join")
                .portfolioComponent(p1)
                .build();

        RefLink link2 = RefLink.builder()
                .address("www.link2.io")
                .description("wanna join")
                .portfolioComponent(p1)
                .build();
        RefLink link3 = RefLink.builder()
                .address("www.link3.io")
                .description("so what")
                .portfolioComponent(p2)
                .build();
        List<RefLink> links = new ArrayList<>();
        links.add(link);links.add(link2);links.add(link3);
        linkRepository.saveAllAndFlush(links);

        ProjectFunction projectFunction = ProjectFunction.builder()
                .description("wanna join")
                .contribution(20)
                .portfolioComponent(p1)
                .build();
        ProjectFunction projectFunction2 = ProjectFunction.builder()
                .description("what")
                .contribution(0)
                .portfolioComponent(p3)
                .build();
        ProjectFunction projectFunction3 = ProjectFunction.builder()
                .description("wanna join")
                .contribution(20)
                .portfolioComponent(p2)
                .build();
        List<ProjectFunction> projects = new ArrayList<>();
        projects.add(projectFunction);projects.add(projectFunction2);projects.add(projectFunction3);
        functionRepository.saveAllAndFlush(projects);
    }

    @AfterEach
    void tearDown() {
        linkRepository.deleteAll();
        functionRepository.deleteAll();
    }

    @Test
    @DisplayName("n+1은_언제_발생하는가")
    public void saveAndFindAllNoCondition() throws Exception {
        // given
        List<PortfolioComponent> portfolioComponentList = portfolioComponentRepository.findAll();

        // then
        assertThat(portfolioComponentList.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("조인 결과 보기")
    public void showJoin(){
        // given
        User user  = userRepository.findByEmail("123@").orElseThrow(()->new IllegalArgumentException("이런.."));
        portfolioComponentRepository.findAllByUserFetch(user);

        // when
        List<ProjectFunction> functionList = functionRepository.findJoinWithRefLink();
        List<ProjectFunction> functionListOuter = functionRepository.findLOuterJoinWithRefLink();
        List<ProjectFunction> functionListROuter = functionRepository.findROuterJoinWithRefLink();

        // then
        System.out.println("[INNER]");
        for (ProjectFunction f : functionList)
        {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>" + f.getPortfolioComponent().getId());
            System.out.println(">>>>>>>>>>" + f.getDescription());
        }
        System.out.println();
        System.out.println("[LEFT OUTER]");
        for (ProjectFunction f : functionListOuter)
        {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>" + f.getPortfolioComponent().getId());
            System.out.println(">>>>>>>>>>" + f.getDescription());
        }
        System.out.println();
        System.out.println("[RIGHT OUTER]");
        for (ProjectFunction f : functionListROuter)
        {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>" + f.getPortfolioComponent().getId());
            System.out.println(">>>>>>>>>>" + f.getDescription());
        }


    }
}
