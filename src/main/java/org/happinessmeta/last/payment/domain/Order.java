package org.happinessmeta.last.payment.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.happinessmeta.last.user.domain.User;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createOrderTime;

    public Order createOrder(User user, Payment payment, int price, String itemName, String orderUid) {
        Order order = new Order();
        order.user = user;
        order.payment = payment;
        order.price = price;
        order.itemName = itemName;
        order.orderUid = orderUid;

        return order;
    }
}
