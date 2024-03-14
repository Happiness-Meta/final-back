package org.happinessmeta.last.resume.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "이력서", description = "résumé(Curriculum Vitae) api")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ResumeController {

    @Operation(summary = "이력서 생성", description = "")
    @PostMapping("/api/v1/resume")
    public ResponseEntity<?> createResume(){
        return null;
    }

    @Operation(summary = "이력서 조회", description = "")
    @GetMapping("/api/v1/resume")
    public ResponseEntity<?> findResume(){
        return null;
    }

    @Operation(summary = "이력서 수정", description = "")
    @PutMapping("/api/v1/resume")
    public ResponseEntity<?> updateResume(){
        return null;
    }

    @Operation(summary = "이력서 삭제", description = "")
    @DeleteMapping("/api/v1/resume")
    public ResponseEntity<?> tearDownResume(){
        return null;
    }
}
