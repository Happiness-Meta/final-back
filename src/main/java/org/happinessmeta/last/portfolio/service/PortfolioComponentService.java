package org.happinessmeta.last.portfolio.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;
import org.happinessmeta.last.portfolio.domain.repository.PortfolioComponentRepository;
import org.happinessmeta.last.portfolio.dto.CreatePortfolioComponentDto;
import org.happinessmeta.last.portfolio.dto.UpdatePortfolioComponentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortfolioComponentService {

    private final PortfolioComponentRepository portfolioComponentRepository;

    @Transactional
    public Long createPortfolioComponent(CreatePortfolioComponentDto requestDto) {
        return portfolioComponentRepository.save(requestDto.toEntity()).getId();
    }

    public Long updatePortfolioComponent(Long id, UpdatePortfolioComponentDto requestDto) {
        PortfolioComponent targetComponent = portfolioComponentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not Found -> id: " + id));

        // TODO: Exception -> PortfolioNotFoundException

        // TODO: advice 추가
//        @ExceptionHandler(PortfolioComponentNotFoundException.class)
//        @ResponseStatus(HttpStatus.BAD_REQUEST)
//        public Result portfolioComponentNotFound(){
//            return responseService.handleFailResult(400, "해당 파일이 존재하지 않습니다");
//        }

        targetComponent.update(requestDto);
        return id;



    }
}
