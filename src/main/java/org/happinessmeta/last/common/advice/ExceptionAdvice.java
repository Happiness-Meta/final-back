package org.happinessmeta.last.common.advice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice(basePackages = "org.happinessmeta.last")
public class ExceptionAdvice {
}
