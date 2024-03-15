package org.happinessmeta.last.payment.controller;

import lombok.RequiredArgsConstructor;
import org.happinessmeta.last.payment.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

//    TODO: Company Entity 추가 시 반영
//    private final CompanyService companyService;
    private final OrderService orderService;

    @GetMapping("/order")
    public String order(){
        return "order";
    }

    @PostMapping("/order")
    public String bizOrder(){
        return "order";
    }
}
