/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 11-Jun-2023
*
*/

package com.jema.app.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jema.app.entities.CustomerOrderStatusHistory;

@Repository
public interface CustomerOrderStatusHistoryRepository  extends CrudRepository<CustomerOrderStatusHistory, Long> {

	List<CustomerOrderStatusHistory> findByCustomerOrderId(String id);
}
