package org.happinessmeta.last.common.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happinessmeta.last.common.exception.*;
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
    // 기본 형태
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
    /*예외 핸들링 커스텀*/
    // 포트폴리오 컴포넌트
    @ExceptionHandler(PortfolioComponentNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Result> portfolioComponentNotFound() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseService.handleFailResult(HttpStatus.BAD_REQUEST.value(), "해당 요소가 존재하지 않습니다"));
    }

    /*회원가입*/
    @ExceptionHandler(ExistUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Result> userAccountAlreadyExist() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseService.handleFailResult(HttpStatus.CONFLICT.value(), "입력하신 이메일이 이미 존재합니다."));
    }
    @ExceptionHandler(EmailPatternException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Result> failInputEmail() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseService.handleFailResult(HttpStatus.BAD_REQUEST.value(), "@를 포함한 이메일 형식만 회원가입 가능합니다."));
    }

    @ExceptionHandler(UserNameDuplicatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Result> nameDuplicated() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseService.handleFailResult(HttpStatus.CONFLICT.value(), "입력하신 닉네임/회사 이름이 이미 존재합니다."));
    }
    @ExceptionHandler(PasswordPatternException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Result> failInputPassword() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseService.handleFailResult(HttpStatus.BAD_REQUEST.value(), "비밀번호는 9자 이상을 입력해주세요"));
    }
    // todo: 요건 한번만 더 고민
    /*로그인*/
    @ExceptionHandler(LoginFailureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Result> loginFail() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseService.handleFailResult(HttpStatus.BAD_REQUEST.value(), "이메일 또는 비밀번호를 잘못 입력하셨습니다."));
    }
}
