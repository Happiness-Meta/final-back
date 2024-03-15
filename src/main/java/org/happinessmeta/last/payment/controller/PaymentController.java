package org.happinessmeta.last.payment.controller;

import lombok.RequiredArgsConstructor;
import org.happinessmeta.last.payment.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/payment/{id}")
    public String payment(){
        return "payment";
    }

    @PostMapping("/payment/{id}")
    public String validationPayment(){
        return "validationPayment";
    }
}
