/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.repository;

import com.bookstore.entity.OrderEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kobe
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {  
     @Query(value = "SELECT * FROM order t where mobileNumber = ?1",
            nativeQuery = true)
     List<?> findByMobileNumber(String mobileNumber);
     
     Optional<?> findByCustomerId(Long customerId);
}
