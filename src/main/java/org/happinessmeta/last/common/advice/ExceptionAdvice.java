package org.happinessmeta.last.common.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happinessmeta.last.common.exception.PortfolioComponentNotFoundException;
import org.happinessmeta.last.common.response.ResponseService;
import org.happinessmeta.last.common.response.Result;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestControllerAdvice(basePackages = "org.happinessmeta.last")
@Slf4j
public class ExceptionAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result defaultException(Exception e) {
        log.info(">>>>>>>>>>>>>>>>>>> {}", e.getMessage());
        return responseService.handleFailResult(500, "오류가 발생 하였습니다.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result> handleBadRequestException(MethodArgumentNotValidException e) {
//        StringBuilder errorMessageBuilder = new StringBuilder();
//
//        for (FieldError error : e.getBindingResult().getFieldErrors()) {
//            String fieldName = error.getField();
//            String message = error.getDefaultMessage();
//            errorMessageBuilder.append(fieldName).append(": ").append(message).append(";").append(" ");
//        }
//        String errorMessage = errorMessageBuilder.toString();
//        log.info(errorMessage);

//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(responseService.handleFailResult(HttpStatus.BAD_REQUEST.value(), errorMessage));

        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseService.handleFailResultWithReason(HttpStatus.BAD_REQUEST.value(), errors));
    }

    @ExceptionHandler(PortfolioComponentNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Result> portfolioComponentNotFound() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseService.handleFailResult(HttpStatus.BAD_REQUEST.value(), "해당 요소가 존재하지 않습니다"));
    }

}
