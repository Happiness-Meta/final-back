package org.happinessmeta.last.portfolio.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happinessmeta.last.common.exception.PortfolioComponentNotFoundException;
import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;
import org.happinessmeta.last.portfolio.domain.entity.ProblemAndSolution;
import org.happinessmeta.last.portfolio.domain.entity.ProjectFunction;
import org.happinessmeta.last.portfolio.domain.entity.RefLink;
import org.happinessmeta.last.portfolio.domain.repository.PortfolioComponentRepository;
import org.happinessmeta.last.portfolio.dto.CreatePortfolioComponentDto;
import org.happinessmeta.last.portfolio.dto.UpdateIsContainedDto;
import org.happinessmeta.last.portfolio.dto.UpdatePortfolioComponentDto;
import org.happinessmeta.last.portfolio.dto.response.PortfolioComponentResponse;
import org.happinessmeta.last.portfolio.dto.sub.FunctionDto;
import org.happinessmeta.last.portfolio.dto.sub.ProblemAndSolutionDto;
import org.happinessmeta.last.portfolio.dto.sub.RefLinkDto;
import org.happinessmeta.last.portfolio.utils.PortfolioUtils;
import org.happinessmeta.last.user.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortfolioComponentService {
    private final PortfolioComponentRepository portfolioComponentRepository;

    @Transactional
    public Long createPortfolioComponent(CreatePortfolioComponentDto requestDto, User createUser) {

        // TODO: 어느 이력서에 들어갈 포트폴리오 요소인가를 알아야 함.
        // TODO: ERD 수정
        PortfolioComponent component = portfolioComponentRepository.save(requestDto.toEntity(createUser));
        return component.getId();
    }

    @Transactional
    public void updatePortfolioComponent(Long id, UpdatePortfolioComponentDto requestDto, User updateUser) {
        PortfolioComponent targetComponent = portfolioComponentRepository.findById(id)
                .orElseThrow(PortfolioComponentNotFoundException::new);

        // TODO: 예외 생성 하기
        if(!Objects.equals(updateUser.getUsername(), targetComponent.getUser().getEmail())){
            throw new IllegalArgumentException("사용자 오류");
        }

        targetComponent.updateComponent(requestDto, targetComponent, updateUser);
    }

    @Transactional
    public void updatePortfolioComponentIsContained(UpdateIsContainedDto requestDto, User updateUser) {
        requestDto.selectedComponent().forEach(isContainedDto -> {
            PortfolioComponent portfolioComponent = portfolioComponentRepository.findById(isContainedDto.id())
                    .orElseThrow(PortfolioComponentNotFoundException::new);

            portfolioComponent.putIsContained(isContainedDto.isContained());
        });
    }

    @Transactional
    public void deletePortfolioComponent(Long id) {
        PortfolioComponent targetComponent = portfolioComponentRepository.findById(id)
                .orElseThrow(PortfolioComponentNotFoundException::new);

        portfolioComponentRepository.delete(targetComponent);
    }

    public List<PortfolioComponentResponse> findAllPortfolioComponent(User user) {
        List<PortfolioComponent> portfolioComponent = portfolioComponentRepository.findAllByUserFetch(user);
        return portfolioComponent.stream().map(PortfolioUtils::createPortfolioComponentDto).collect(Collectors.toList());

    }

    public List<PortfolioComponent> findAllPortfolio(User user) {
        return portfolioComponentRepository.findAllByUser(user);
    }

    public List<PortfolioComponentResponse> findAllPublicPortfolioComponent(User user) {
        List<PortfolioComponent> portfolioComponent = portfolioComponentRepository.findAllByUserAndVisibilityIsTrue(user);
        return portfolioComponent.stream().map(PortfolioUtils::createPortfolioComponentDto).collect(Collectors.toList());
    }



    public PortfolioComponentResponse findOnePortfolioComponent(User user, Long portfolioId) {
        PortfolioComponent portfolioComponent = portfolioComponentRepository.findById(portfolioId).
                orElseThrow(PortfolioComponentNotFoundException::new);

        // TODO: 예외 생성 하기
        if(!Objects.equals(user.getUsername(), portfolioComponent.getUser().getEmail())){
            throw new IllegalArgumentException("사용자 오류");
        }
        return PortfolioUtils.createPortfolioComponentDto(portfolioComponent);
    }
}
