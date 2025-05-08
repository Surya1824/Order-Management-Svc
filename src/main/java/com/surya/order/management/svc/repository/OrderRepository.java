package com.surya.order.management.svc.repository;

import com.surya.order.management.svc.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface OrderRepository extends JpaRepository<OrderDetails, Long> {

}
