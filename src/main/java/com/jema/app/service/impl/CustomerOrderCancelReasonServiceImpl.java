/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 10-Jun-2023
*
*/

package com.jema.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jema.app.entities.CustomerOrderCancelReason;
import com.jema.app.repositories.CustomerOrderCancelReasonRepository;
import com.jema.app.service.CustomerOrderCancelReasonService;

@Service
public class CustomerOrderCancelReasonServiceImpl implements CustomerOrderCancelReasonService{

	@Autowired
	CustomerOrderCancelReasonRepository mCustomerOrderCancelReasonRepository;
	
	@Override
	public Long save(CustomerOrderCancelReason mCustomerOrderCancelReason) {
		// TODO Auto-generated method stub
		return mCustomerOrderCancelReasonRepository.save(mCustomerOrderCancelReason).getId();
	}

	@Override
	public List<CustomerOrderCancelReason> findById(String id) {
		// TODO Auto-generated method stub
		return mCustomerOrderCancelReasonRepository.findByCustomerOrderId(id);
	}

}
