/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 10-Jun-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.entities.CustomerOrderReturnReason;

public interface CustomerOrderReturnReasonService {

	public Long save(CustomerOrderReturnReason mCustomerOrderReturnReason);

	public List<CustomerOrderReturnReason> findById(String id);
	
}
