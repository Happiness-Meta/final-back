package org.happinessmeta.last.payment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.happinessmeta.last.common.response.ResponseService;
import org.happinessmeta.last.common.response.SingleResult;
import org.happinessmeta.last.payment.dto.CreateOrderDto;
import org.happinessmeta.last.payment.service.OrderService;
import org.happinessmeta.last.user.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1/order")
@Tag(name = "주문 기능", description = "Order API")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ResponseService responseService;

    @Operation(summary = "주문 단건 조회", description = "")
    @GetMapping
    public String order(){
        return "order";
    }

    @Operation(summary = "주문 생성", description = "")
    @PostMapping
    public ResponseEntity<SingleResult<String>> createOrder(
            @AuthenticationPrincipal User user,
            @Validated @RequestBody CreateOrderDto request
    ){
        String orderUid = orderService.createOrder(user, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseService.handleSingleResult(orderUid, HttpStatus.CREATED.value()));
    }


}
