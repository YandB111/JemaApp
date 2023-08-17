/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 11-Jun-2023
*
*/

package com.jema.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jema.app.entities.CustomerOrderStatusHistory;
import com.jema.app.repositories.CustomerOrderStatusHistoryRepository;
import com.jema.app.service.CustomerOrderStatusHistoryService;

@Service
public class CustomerOrderStatusHistoryServiceImpl implements CustomerOrderStatusHistoryService {

	@Autowired
	CustomerOrderStatusHistoryRepository mCustomerOrderStatusHistoryRepository;

	@Override
	public Long save(CustomerOrderStatusHistory mCustomerOrderStatusHistory) {
		// TODO Auto-generated method stub
		return mCustomerOrderStatusHistoryRepository.save(mCustomerOrderStatusHistory).getId();
	}

	@Override
	public List<CustomerOrderStatusHistory> findById(String mCustomerOrderId) {
		// TODO Auto-generated method stub
		return mCustomerOrderStatusHistoryRepository.findByCustomerOrderId(mCustomerOrderId);
	}

}
