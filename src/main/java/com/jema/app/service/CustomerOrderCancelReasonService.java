/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 10-Jun-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.entities.CustomerOrderCancelReason;

public interface CustomerOrderCancelReasonService {

	public Long save(CustomerOrderCancelReason mCustomerOrderCancelReason);

	public List<CustomerOrderCancelReason> findById(String id);
	
}
