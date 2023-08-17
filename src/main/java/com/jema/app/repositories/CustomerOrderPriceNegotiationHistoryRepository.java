/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 10-Jun-2023
*
*/

package com.jema.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jema.app.entities.CustomerOrderPriceNegotiationHistory;

@Repository
public interface CustomerOrderPriceNegotiationHistoryRepository
		extends CrudRepository<CustomerOrderPriceNegotiationHistory, String> {

}
