package org.happinessmeta.last.payment.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happinessmeta.last.common.exception.*;
import org.happinessmeta.last.payment.domain.Order;
import org.happinessmeta.last.payment.dto.OrderCancelRequest;
import org.happinessmeta.last.payment.dto.PaymentCallbackRequest;
import org.happinessmeta.last.payment.dto.PaymentRequest;
import org.happinessmeta.last.payment.repository.OrderRepository;
import org.happinessmeta.last.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final IamportClient iamportClient;

    public PaymentRequest findRequestDto(String orderUid) {
        Order order = orderRepository.findOrderAndPaymentAndMember(orderUid)
                .orElseThrow(() -> new OrderNotFoundException("해당 주문이 존재하지 않습니다."));

        return PaymentRequest.builder()
                .buyerName(order.getUser().getUsername())
                .buyerEmail(order.getUser().getEmail())
//                .buyerAddress(order.getUser().getAddress())
                .paymentAmount(order.getPayment().getPaymentAmount())
                .itemName(order.getItemName())
                .orderUid(order.getOrderUid())
                .build();
    }

    public IamportResponse<Payment> paymentByCallback(PaymentCallbackRequest request) {
        try{
            // 결제 단건 조회(아임포트)
            IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(request.impUid());
            // 주문내역 조회
            Order order = orderRepository.findOrderAndPayment(request.orderUid())
                    .orElseThrow(() -> new OrderDetailNotFoundException("주문 내역이 없습니다."));

            // 결제 완료가 아니면
            if(!iamportResponse.getResponse().getStatus().equals("paid")) {
                // 주문, 결제 삭제
                orderRepository.delete(order);
                paymentRepository.delete(order.getPayment());

                throw new PaymentNotCompletedException("결제 미완료");
            }

            // DB에 저장된 결제 금액
            int price = order.getPayment().getPaymentAmount();
            // 실 결제 금액
            int iamportPrice = iamportResponse.getResponse().getAmount().intValue();

            // 결제 금액 검증
            if(iamportPrice != price) {
                // 주문, 결제 삭제
                orderRepository.delete(order);
                paymentRepository.delete(order.getPayment());

                // 결제금액 위변조로 의심되는 결제금액을 취소(아임포트)
                iamportClient.cancelPaymentByImpUid(new CancelData(iamportResponse.getResponse().getImpUid(), true, new BigDecimal(iamportPrice)));

                log.info("PaymentAmountIllegalException userId = {}", order.getUser().getUsername());
                throw new PaymentAmountIllegalException("결제금액 위변조 의심");
            }

            // 결제 상태 변경
            //TODO: refactor apidAt, address, email add
            order.getPayment().changePaymentBySuccess(request.status(), request.impUid(), request.payMethod(), request.cardName(), request.cardNumber(), request.currency());

            return iamportResponse;

        }catch (IamportResponseException | IOException e) {
            throw new PaymentProcessingException("결제 비즈니스 로직 중 예외 발생");
        }
    }


    public IamportResponse<Payment> paymentCancel(
//                                        User user,
                                        OrderCancelRequest request) {
        try {
            CancelData cancelData = new CancelData(request.impUid(), true);

            return iamportClient.cancelPaymentByImpUid(cancelData);
        } catch (IamportResponseException | IOException e) {
            throw new PaymentProcessingException("결제 취소 비즈니스 로직 중 예외 발생");
        }
    }
}
