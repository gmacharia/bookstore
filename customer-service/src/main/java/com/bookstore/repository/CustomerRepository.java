/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.bookstore.repository;

import com.bookstore.entity.CustomerEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kobe
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    
     @Query(value = "SELECT * FROM customer t where mobileNumber = ?1",
            nativeQuery = true)
     List<?> findByMobileNumber(String mobileNumber);
}
