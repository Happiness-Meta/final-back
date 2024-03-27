package org.happinessmeta.last.resume.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happinessmeta.last.common.exception.PortfolioComponentNotFoundException;
import org.happinessmeta.last.common.exception.UserNotFoundException;
import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;
import org.happinessmeta.last.portfolio.domain.repository.PortfolioComponentRepository;
import org.happinessmeta.last.resume.domain.entity.Resume;
import org.happinessmeta.last.resume.domain.repository.ResumeRepository;
import org.happinessmeta.last.resume.dto.CreateResumeDto;
import org.happinessmeta.last.user.domain.User;
import org.happinessmeta.last.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final PortfolioComponentRepository portfolioComponentRepository;

    @Transactional
    public Long createResume(CreateResumeDto requestDto, User createUser) {
        Resume newResume = requestDto.toEntity();
        newResume.putUser(createUser);
        Resume savedResume = resumeRepository.save(newResume);

        List<PortfolioComponent> updatePortfolio = portfolioComponentRepository
                .findAllById(requestDto.projectId());

        updatePortfolio.forEach(portfolio -> {
            portfolio.putIsContained(true);
            portfolio.putResume(savedResume);
        });

        return savedResume.getId();
    }

    public Resume findResume(User user) {
        // TODO: 이력서 없음 예외

        return resumeRepository.findByUser(user).orElseThrow(
                ()-> new NoSuchElementException("사용자에 해당하는 이력서를 찾을 수 없습니다.")
        );
    }

    @Transactional
    public void tearResume(User user) {
        resumeRepository.findByUser(user)
                .orElseThrow(() -> new NoSuchElementException("사용자에 해당하는 이력서를 찾을 수 없습니다."));
        resumeRepository.deleteByUser(user);
    }
}
