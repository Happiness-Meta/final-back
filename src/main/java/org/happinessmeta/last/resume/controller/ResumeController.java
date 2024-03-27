package org.happinessmeta.last.resume.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happinessmeta.last.common.response.ResponseService;
import org.happinessmeta.last.common.response.SingleResult;
import org.happinessmeta.last.resume.domain.entity.Resume;
import org.happinessmeta.last.resume.dto.CreateResumeDto;
import org.happinessmeta.last.resume.service.ResumeService;
import org.happinessmeta.last.user.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(name = "이력서", description = "résumé(Curriculum Vitae) api")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;
    private final ResponseService responseService;

    @Operation(summary = "이력서 생성", description = "")
    @PostMapping("/api/v1/resume")
    public ResponseEntity<SingleResult<Long>> createResume(
            @AuthenticationPrincipal User user,
            @Validated @RequestBody CreateResumeDto requestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseService.handleSingleResult(resumeService.createResume(requestDto, user), HttpStatus.CREATED.value()));

    }

    @Operation(summary = "이력서 조회", description = "")
    @GetMapping("/api/v1/resume")
    public ResponseEntity<SingleResult<Resume>> findResume(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseService.handleSingleResult(resumeService.findResume(user), HttpStatus.OK.value()));
    }

    @Operation(summary = "이력서 수정", description = "")
    @PutMapping("/api/v1/resume")
    public ResponseEntity<?> updateResume() {
        return null;
    }

    @Operation(summary = "이력서 삭제", description = "")
    @DeleteMapping("/api/v1/resume")
    public ResponseEntity<?> tearDownResume(
//            @RequestParam("id") Long id
            @AuthenticationPrincipal User user
    ) {
        resumeService.tearResume(user);
        return ResponseEntity.noContent().build();
    }
}
