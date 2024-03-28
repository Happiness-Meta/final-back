package org.happinessmeta.last.payment.repository;

import org.happinessmeta.last.payment.domain.Order;
import org.happinessmeta.last.payment.domain.type.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o" +
            " left join fetch o.payment p" +
            " left join fetch o.user u" +
            " where o.orderUid = :orderUid")
    Optional<Order> findOrderAndPaymentAndMember(String orderUid);

    @Query("select o from Order o" +
            " left join fetch o.payment p" +
            " where o.orderUid = :orderUid")
    Optional<Order> findOrderAndPayment(String orderUid);

    @Query("select o from Order o" +
            " left join fetch o.payment p" +
            " left join fetch o.user u" +
            " where o.user.email = :companyEmail")
    Optional<Order> findOrderAndPaymentDetail(String companyEmail);

    @Query("SELECT o FROM Order o WHERE o.payment.status = :status AND o.createOrderTime" + " < :currentTime")
    List<Order> deleteOrdersByTimeAndPayStatus(LocalDateTime currentTime, PaymentStatus status);

    @Query("select o from Order o where o.user.id = :userId and o.payment.status = 'OK'")
    boolean existsByUserIdAndPaymentStatusIsOk(Long userId);

    @Query("select o from Order o where o.user.id = :userId and o.payment.status = 'READY'")
    boolean existsByUserIdAndPaymentStatusIsReady(Long userId);

//    @Query("select o from Order o where o.user.id = :userId")
    @Query("select case when count(o) > 0 then true else false end from Order o where o.user.id = :userId")
    boolean existsByUserIdAndNotPay(Long userId);
}