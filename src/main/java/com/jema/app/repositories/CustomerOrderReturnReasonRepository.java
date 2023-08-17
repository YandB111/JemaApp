/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 10-Jun-2023
*
*/

package com.jema.app.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jema.app.entities.CustomerOrderReturnReason;

@Repository
public interface CustomerOrderReturnReasonRepository extends CrudRepository<CustomerOrderReturnReason, Long> {

	List<CustomerOrderReturnReason> findByCustomerOrderId(String id);
}
