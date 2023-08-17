/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 07-Jun-2023
*
*/

package com.jema.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jema.app.entities.OrderReason;

@Repository
public interface OrderReasonRepository extends JpaRepository<OrderReason, Long> {

}
