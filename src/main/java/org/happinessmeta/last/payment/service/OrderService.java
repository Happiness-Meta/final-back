package org.happinessmeta.last.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happinessmeta.last.payment.domain.Order;
import org.happinessmeta.last.payment.domain.Payment;
import org.happinessmeta.last.payment.domain.PaymentStatus;
import org.happinessmeta.last.payment.dto.CreateOrderDto;
import org.happinessmeta.last.payment.dto.OrderResponse;
import org.happinessmeta.last.payment.repository.OrderRepository;
import org.happinessmeta.last.payment.repository.PaymentRepository;
import org.happinessmeta.last.payment.service.type.ItemType;
import org.happinessmeta.last.user.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public OrderResponse createOrder(User user, CreateOrderDto request) {
        Payment savedPayment = createPayment(request);

        Order savedOrder = createOrder(user, request, savedPayment);

        return OrderResponse.builder()
                .itemName(savedOrder.getItemName())
                .amount(savedOrder.getPrice())
                .orderUid(savedOrder.getOrderUid())
                .build();
    }


    // 스케줄링 작업을 통한 결제 미완료 데이터 삭제 메소드
    // 메소드 실행 시간 : 12시
    // 주문 생성 시간이 11시 55분 이전의 데이터를 삭제 한다.
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void proceedDeleteOrder() {
        LocalDateTime deleteTime = LocalDateTime.now().minusMinutes(5);
        log.info("-----proceedOrderDelete Start-----");
        log.info("작업 시간. {}, 삭제 시간 {}", LocalDateTime.now(), deleteTime);
        List<Order> expiredPayments = orderRepository.deleteOrdersByTimeAndPayStatus(deleteTime, PaymentStatus.READY);
        orderRepository.deleteAll(expiredPayments);
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
