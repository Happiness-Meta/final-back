package org.happinessmeta.last.portfolio.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happinessmeta.last.common.response.MultipleResult;
import org.happinessmeta.last.common.response.ResponseService;
import org.happinessmeta.last.common.response.SingleResult;
import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;
import org.happinessmeta.last.portfolio.dto.CreatePortfolioComponentDto;
import org.happinessmeta.last.portfolio.dto.UpdatePortfolioComponentDto;
import org.happinessmeta.last.portfolio.dto.UpdateIsContainedDto;
import org.happinessmeta.last.portfolio.dto.response.PortfolioComponentResponse;
import org.happinessmeta.last.portfolio.service.PortfolioComponentService;
import org.happinessmeta.last.user.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//@RequestMapping("/api/v1/portfolio")
@Tag(name = "포트폴리오 요소", description = "PortfolioComponent api")
@Slf4j
@RestController
@RequiredArgsConstructor
public class PortfolioComponentController {

    private final PortfolioComponentService portfolioComponentService;
    private final ResponseService responseService;

    @Operation(summary = "특정 요소 조회", description = "")
    @GetMapping("/api/v1/portfolio/{id}")
    public ResponseEntity<SingleResult<PortfolioComponentResponse>> findComponent(
            @AuthenticationPrincipal User user,
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseService.handleSingleResult(portfolioComponentService.findOnePortfolioComponent(user, id), HttpStatus.OK.value()));
    }

    @Operation(summary = "사용자 포트폴리오 전체 조회", description = "")
    @GetMapping("/api/v1/portfolio/all")
    public ResponseEntity<MultipleResult<PortfolioComponentResponse>> findAllComponent(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseService.handleListResult(portfolioComponentService.findAllPortfolioComponent(user), HttpStatus.OK.value()));
    }

    @Operation(summary = "사용자 공개 포트폴리오 전체 조회", description = "")
    @GetMapping("/api/v1/portfolio/all/public")
    public ResponseEntity<MultipleResult<PortfolioComponentResponse>> findAllPublicComponent(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseService.handleListResult(portfolioComponentService.findAllPublicPortfolioComponent(user), HttpStatus.OK.value()));
    }

    @Operation(summary = "요소 생성", description = "")
    @PostMapping("/api/v1/portfolio")
    public ResponseEntity<SingleResult<Long>> createComponent(
            @AuthenticationPrincipal User user,
            @Validated @RequestBody CreatePortfolioComponentDto requestDto
    ) {
        Long savedId = portfolioComponentService.createPortfolioComponent(requestDto, user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseService.handleSingleResult(savedId, HttpStatus.CREATED.value()));
    }

    @Operation(summary = "요소 수정", description = "")
    @PutMapping("/api/v1/portfolio/{id}")
    public ResponseEntity<SingleResult<Long>> updateComponent(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal User user,
            @Validated @RequestBody UpdatePortfolioComponentDto requestDto
    ) {
        portfolioComponentService.updatePortfolioComponent(id, requestDto, user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseService.handleSingleResult(id, HttpStatus.OK.value()));
    }

    @Operation(summary = "요소 삭제", description = "")
    @DeleteMapping("/api/v1/portfolio/{id}")
    public ResponseEntity<Void> tearDownComponent(
            @PathVariable("id") Long id
    ) {
        portfolioComponentService.deletePortfolioComponent(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "요소 공개 범위 변경", description = "")
    @PatchMapping("/api/v1/portfolio")
    public ResponseEntity<SingleResult<?>> changePortfolioVisibility(
            @AuthenticationPrincipal User user,
            @Validated @RequestBody UpdateIsContainedDto requestDto
    ) {
        portfolioComponentService.updatePortfolioComponentIsContained(requestDto, user);
        return ResponseEntity.status(HttpStatus.OK).body(
                responseService.handleSingleResult("changed isContained", HttpStatus.OK.value())
        );
    }
}
