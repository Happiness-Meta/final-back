package org.happinessmeta.last.portfolio.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happinessmeta.last.common.exception.PortfolioComponentNotFoundException;
import org.happinessmeta.last.common.exception.UserNotFoundException;
import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;
import org.happinessmeta.last.portfolio.domain.repository.PortfolioComponentRepository;
import org.happinessmeta.last.portfolio.dto.CreatePortfolioComponentDto;
import org.happinessmeta.last.portfolio.dto.UpdatePortfolioComponentDto;
import org.happinessmeta.last.user.domain.User;
import org.happinessmeta.last.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        if(updateUser != targetComponent.getUser()){
            throw new IllegalArgumentException("사용자 오류");
        }

        targetComponent.updateComponent(requestDto, targetComponent, updateUser);
    }

    @Transactional
    public void deletePortfolioComponent(Long id) {
        PortfolioComponent targetComponent = portfolioComponentRepository.findById(id)
                .orElseThrow(PortfolioComponentNotFoundException::new);

        portfolioComponentRepository.delete(targetComponent);
    }
}
