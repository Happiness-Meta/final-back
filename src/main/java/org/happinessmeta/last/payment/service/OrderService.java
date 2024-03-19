package org.happinessmeta.last.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happinessmeta.last.payment.domain.Order;
import org.happinessmeta.last.payment.domain.Payment;
import org.happinessmeta.last.payment.dto.CreateOrderDto;
import org.happinessmeta.last.payment.repository.OrderRepository;
import org.happinessmeta.last.payment.repository.PaymentRepository;
import org.happinessmeta.last.payment.service.type.ItemType;
import org.happinessmeta.last.user.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public String createOrder(User user, CreateOrderDto request) {
        Payment savedPayment = createPayment(request);

        Order savedOrder = createOrder(user, request, savedPayment);

        return savedOrder.getOrderUid();
    }

    // 결제 대기 생성 메서드
    @NotNull
    private Payment createPayment(CreateOrderDto request) {
        Payment payment = new Payment();
        Payment savedPayment = payment.createPayment(request.itemPrice());
        paymentRepository.save(savedPayment);
        return savedPayment;
    }

    // 주문 생성 저장 메서드
    @NotNull
    private Order createOrder(User user, CreateOrderDto request, Payment savedPayment) {
        Order order = new Order();
        Order savedOrder = order.createOrder(user, savedPayment, request.itemPrice(), request.itemName(), createOrderUid(request));
        orderRepository.save(savedOrder);
        return savedOrder;
    }

    // 주문 번호 생성 메서드
    private String createOrderUid(CreateOrderDto request) {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
//        TODO: 확장성을 고려 ->  matchItemNumber() 추가 필요
//        TODO: 주문 시 주문 가격과 상품 가격이 동일한지 검증 필요 + OrderPriceNotEqualsException 예외 처리
        String itemNumber = ItemType.BASIC_TICKET.getItemNumber();
        String uid = UUID.randomUUID().toString().substring(0, 8);


        return currentDate + "-" + itemNumber + "-" + uid;
    }
}
