package org.happinessmeta.last.portfolio.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happinessmeta.last.common.exception.PortfolioComponentNotFoundException;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortfolioComponentService {

    private final PortfolioComponentRepository portfolioComponentRepository;

    @Transactional
    public Long createPortfolioComponent(CreatePortfolioComponentDto requestDto) {

        // TODO: 어느 이력서에 들어갈 포트폴리오 요소인가를 알아야 함.


        PortfolioComponent component = portfolioComponentRepository.save(requestDto.toEntity());
        // TODO: 예외 설정
        PortfolioComponent portfolioComponent = portfolioComponentRepository.findById(component.getId())
                .orElseThrow(() -> new RuntimeException("저장 중 오류 발생"));

        List<MyFunction> myFunctions = requestDto.myFunction().stream()
                .map(FunctionDto::toEntity)
                .peek(func -> func.putPortfolioComponent(portfolioComponent))
                .collect(Collectors.toList());

        List<RefLink> links = requestDto.links().stream()
                .map(RefLinkDto::toEntity)
                .peek(link -> link.putPortfolioComponent(portfolioComponent))
                .collect(Collectors.toList());

        List<ProblemAndSolution> problemsAndSolutions = requestDto.problemAndSolutions().stream()
                .map(ProblemAndSolutionDto::toEntity)
                .peek(link -> link.putPortfolioComponent(portfolioComponent))
                .collect(Collectors.toList());

        portfolioComponent.putFunctions(myFunctions);
        portfolioComponent.putLinks(links);
        portfolioComponent.putProblemsAndSolutions(problemsAndSolutions);

        return portfolioComponent.getId();
    }

    @Transactional
    public void updatePortfolioComponent(Long id, UpdatePortfolioComponentDto requestDto) {
        PortfolioComponent targetComponent = portfolioComponentRepository.findById(id)
                .orElseThrow(PortfolioComponentNotFoundException::new);

        targetComponent.update(requestDto, targetComponent);
    }

    public void deletePortfolioComponent(Long id) {
        PortfolioComponent targetComponent = portfolioComponentRepository.findById(id)
                .orElseThrow(PortfolioComponentNotFoundException::new);
        portfolioComponentRepository.delete(targetComponent);
    }
}
