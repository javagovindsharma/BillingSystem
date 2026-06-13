package com.djtraders.billing.repository;

import com.djtraders.billing.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Long> {
    List<Sales> findByCustomerName(String customerName);
}
