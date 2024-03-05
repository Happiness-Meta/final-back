package org.happinessmeta.last.basic.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @deprecated 테스트용이므로 사용하지 않습니다.
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Test Swagger", description = "swagger 시험 컨트롤러")
@Deprecated
public class HelloController {
    @GetMapping("/api/hello")
    public String helloSpring(){
        return "Hello, Swagger";
    }
}
