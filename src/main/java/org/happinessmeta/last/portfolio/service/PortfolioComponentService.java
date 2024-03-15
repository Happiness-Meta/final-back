package org.happinessmeta.last.portfolio.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happinessmeta.last.common.exception.PortfolioComponentNotFoundException;
import org.happinessmeta.last.common.exception.UserNotFoundException;
import org.happinessmeta.last.portfolio.domain.entity.MyFunction;
import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;
import org.happinessmeta.last.portfolio.domain.entity.ProblemAndSolution;
import org.happinessmeta.last.portfolio.domain.entity.RefLink;
import org.happinessmeta.last.portfolio.domain.repository.PortfolioComponentRepository;
import org.happinessmeta.last.portfolio.dto.CreatePortfolioComponentDto;
import org.happinessmeta.last.portfolio.dto.UpdatePortfolioComponentDto;
import org.happinessmeta.last.portfolio.dto.sub.FunctionDto;
import org.happinessmeta.last.portfolio.dto.sub.ProblemAndSolutionDto;
import org.happinessmeta.last.portfolio.dto.sub.RefLinkDto;
import org.happinessmeta.last.user.domain.User;
import org.happinessmeta.last.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortfolioComponentService {
    private final UserRepository userRepository;

    private final PortfolioComponentRepository portfolioComponentRepository;

    @Transactional
    public Long createPortfolioComponent(CreatePortfolioComponentDto requestDto, String email) {

        // TODO: 어느 이력서에 들어갈 포트폴리오 요소인가를 알아야 함.
        // TODO: ERD 수정
//        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        PortfolioComponent component = portfolioComponentRepository.save(requestDto.toEntity());
        return component.getId();
    }

    @Transactional
    public void updatePortfolioComponent(Long id, UpdatePortfolioComponentDto requestDto) {
        PortfolioComponent targetComponent = portfolioComponentRepository.findById(id)
                .orElseThrow(PortfolioComponentNotFoundException::new);

        targetComponent.updateComponent(requestDto, targetComponent);
    }

    @Transactional
    public void deletePortfolioComponent(Long id) {
        PortfolioComponent targetComponent = portfolioComponentRepository.findById(id)
                .orElseThrow(PortfolioComponentNotFoundException::new);

        portfolioComponentRepository.delete(targetComponent);
    }
}
