package org.happinessmeta.last.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happinessmeta.last.payment.domain.Order;
import org.happinessmeta.last.payment.domain.Payment;
import org.happinessmeta.last.payment.domain.type.PaymentStatus;
import org.happinessmeta.last.payment.dto.CreateOrderDto;
import org.happinessmeta.last.payment.dto.OrderPaidResponse;
import org.happinessmeta.last.payment.dto.OrderResponse;
import org.happinessmeta.last.payment.dto.OrderResponse.PaymentOrderResponse;
import org.happinessmeta.last.payment.repository.OrderRepository;
import org.happinessmeta.last.payment.repository.PaymentRepository;
import org.happinessmeta.last.payment.service.type.ItemType;
import org.happinessmeta.last.user.domain.User;
import org.happinessmeta.last.user.dto.UserResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public OrderPaidResponse createOrder(User user, CreateOrderDto request) {

//        log.info("1. user >> {}", user.getEmail());
//        log.info("1. user >> {}", user.getId());
//
//        if(orderRepository.existsByUserIdAndNotPay(user.getId())){

//        orderRepository.findByUserId() > auth

            log.info("3. user >> {}", user.getEmail());
            log.info("3. user >> {}", user.getId());

            Payment savedPayment = createPayment(request);

            Order savedOrder = createOrder(user, request, savedPayment);

            UserResponse userDto = UserResponse.toDto(savedOrder.getUser());

            log.info("user service = {}", user.getEmail());
            log.info("savedOrder = {}", savedOrder.getUser().getUsername());
            log.info("userDto = {}", userDto.getName());

            return OrderPaidResponse.builder()
                    .itemName(savedOrder.getItemName())
                    .amount(savedOrder.getPrice())
                    .orderUid(savedOrder.getOrderUid())
                    .user(userDto)
                    .status(savedPayment.getStatus())
                    .build();
//        }
//
//        if(orderRepository.existsByUserIdAndPaymentStatusIsReady(user.getId())){
//
//            log.info("2. user >> {}", user.getEmail());
//            log.info("2. user >> {}", user.getId());
//
//            Optional<Order> findOrderAndPay = orderRepository.findOrderAndPaymentDetail(user.getEmail());
//
//            UserResponse userDto = UserResponse.toDto(user);
//
//            return OrderPaidResponse.builder()
//                    .itemName(findOrderAndPay.orElseThrow().getItemName())
//                    .amount(findOrderAndPay.orElseThrow().getPrice())
//                    .orderUid(findOrderAndPay.orElseThrow().getOrderUid())
//                    .user(userDto)
//                    .status(findOrderAndPay.orElseThrow().getPayment().getStatus())
//                    .build();
//        }
//
//        if(orderRepository.existsByUserIdAndPaymentStatusIsOk(user.getId())){
//
//            throw new RuntimeException("이미 결제가 완료되었습니다.");
//        }
//
//        throw new RuntimeException("주문 생성 중 오류 발생");
    }

    // 주문 조회 refactor 필요
    public OrderResponse findOrder(User user){

        Optional<Order> findOrder = orderRepository.findOrderAndPaymentDetail(user.getEmail());

        //TODO: -> orElse
        if (findOrder.isPresent()) {
            Order order = findOrder.get();
            Payment payment = order.getPayment();
            PaymentOrderResponse paymentDto = null;

            if (payment != null) {
                paymentDto = new PaymentOrderResponse(
                        payment.getPaymentAmount(),
                        payment.getStatus(),
                        payment.getPaymentUid(),
                        payment.getPaidAt()
                );
            } else {
                throw new RuntimeException("결제 정보가 없습니다.");
            }

            return OrderResponse.builder()
                    .itemName(order.getItemName())
                    .orderUid(order.getOrderUid())
                    .payment(paymentDto) // 결제 정보 추가
                    .build();
        } else {
            throw new RuntimeException("주문을 찾을 수 없습니다.");
        }
    }

    // 스케줄링 작업을 통한 결제 미완료 데이터 삭제 메소드
    // 메소드 실행 시간 :00시
    // 주문 생성 시간이 하루 전인 데이터를 삭제 한다.

    // 주문 시간 -> 20분 30분 동안 주문을 안하면 주문 상태 변경

    // TODO: 배치 모듈 분리 필요 & 현재는 READY 상태를 삭제하는 로직

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void proceedDeleteOrder() {
        LocalDateTime deleteTime = LocalDateTime.now().minusDays(1);
        log.info("-----proceedOrderDelete Start-----");
        log.info("작업 시간. {}, 삭제 시간 {}", LocalDateTime.now(), deleteTime);
        List<Order> expiredPayments = orderRepository.deleteOrdersByTimeAndPayStatus(deleteTime, PaymentStatus.CANCEL);
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

    public String createOrderUid(CreateOrderDto request) {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
//        TODO: 확장성을 고려 ->  matchItemNumber() 추가 필요
//        TODO: 주문 시 주문 가격과 상품 가격이 동일한지 검증 필요 + OrderPriceNotEqualsException 예외 처리
        String itemNumber = ItemType.BASIC_TICKET.getItemNumber();
        String uid = UUID.randomUUID().toString().substring(0, 8);


        return currentDate + "-" + itemNumber + "-" + uid;
    }
}
