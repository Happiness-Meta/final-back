package org.happinessmeta.last.payment.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.happinessmeta.last.user.domain.User;

@Getter
@Table(name = "orders")
@Entity
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

//    상품 가격
    private int price;
    private String itemName;

//    주문 번호
    private String orderUid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    public Order createOrder(Payment payment, int price, String itemName, String orderUid) {
        Order order = new Order();
        order.user = user;
        order.payment = payment;
        order.price = price;
        order.itemName = itemName;
        order.orderUid = orderUid;

        return order;
    }
}