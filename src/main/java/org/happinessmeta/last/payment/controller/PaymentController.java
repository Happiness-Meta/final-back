package org.happinessmeta.last.payment.controller;

import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happinessmeta.last.payment.dto.PaymentCallbackRequest;
import org.happinessmeta.last.payment.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/payment")
@Tag(name = "결제 기능", description = "Order API")
@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("http://localhost:3000") // 프론트 테스트를 위한 cors 설정
public class PaymentController {
    private final PaymentService paymentService;

    @Operation(summary = "test", description = "")
    @GetMapping("/{id}")
    public String payment(){
        return "payment";
    }

    @Operation(summary = "결제 완료 후 서버에 결제 검증 요청", description = "")
    @ResponseBody
    @PostMapping
    public ResponseEntity<IamportResponse<Payment>> validationPayment(@RequestBody PaymentCallbackRequest request) {
        log.info("request = {}", request);
        IamportResponse<Payment> iamportResponse = paymentService.paymentByCallback(request);
        log.info("결제 응답={}", iamportResponse.getResponse().toString());

        // TODO: 결제 완료시 user 권한 수정 필요

        return new ResponseEntity<>(iamportResponse, HttpStatus.OK);
    }
}
