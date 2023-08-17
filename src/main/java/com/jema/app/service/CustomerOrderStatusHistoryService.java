/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 11-Jun-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.entities.CustomerOrderStatusHistory;

public interface CustomerOrderStatusHistoryService {

	public Long save(CustomerOrderStatusHistory mCustomerOrderStatusHistory);

	public List<CustomerOrderStatusHistory> findById(String mCustomerOrderId);
}
