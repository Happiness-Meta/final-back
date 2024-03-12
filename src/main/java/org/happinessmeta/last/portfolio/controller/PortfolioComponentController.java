package org.happinessmeta.last.portfolio.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happinessmeta.last.portfolio.dto.CreatePortfolioComponentDto;
import org.happinessmeta.last.portfolio.dto.UpdatePortfolioComponentDto;
import org.happinessmeta.last.portfolio.service.PortfolioComponentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//@RequestMapping("/api/v1/portfolio")
@Slf4j
@RestController
@RequiredArgsConstructor
public class PortfolioComponentController {

    private final PortfolioComponentService portfolioComponentService;

    @GetMapping("/api/v1/portfolio/{id}")
    public ResponseEntity<?> findComponent(@PathVariable("id") String id) {
        return null;
    }

    @GetMapping("/api/v1/portfolio/all")
    public ResponseEntity<?> findAllComponent() {
        return null;
    }

    @PostMapping("/api/v1/portfolio")
    public Long createComponent(
            @Validated @RequestBody CreatePortfolioComponentDto requestDto
    ) {
        return portfolioComponentService.createPortfolioComponent(requestDto);
    }

    @PutMapping("/api/v1/portfolio/{id}")
    public Long updateComponent(
            @PathVariable("id") Long id,
            @Validated @RequestBody UpdatePortfolioComponentDto requestDto
    ) {
        return portfolioComponentService.updatePortfolioComponent(id, requestDto);
    }
}
