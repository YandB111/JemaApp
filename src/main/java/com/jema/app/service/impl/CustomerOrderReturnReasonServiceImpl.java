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

import com.jema.app.entities.CustomerOrderReturnReason;
import com.jema.app.repositories.CustomerOrderReturnReasonRepository;
import com.jema.app.service.CustomerOrderReturnReasonService;

@Service
public class CustomerOrderReturnReasonServiceImpl implements CustomerOrderReturnReasonService{

	@Autowired
	CustomerOrderReturnReasonRepository mCustomerOrderReturnReasonRepository;
	
	@Override
	public Long save(CustomerOrderReturnReason mCustomerOrderReturnReason) {
		// TODO Auto-generated method stub
		return mCustomerOrderReturnReasonRepository.save(mCustomerOrderReturnReason).getId();
	}

	@Override
	public List<CustomerOrderReturnReason> findById(String id) {
		// TODO Auto-generated method stub
		return mCustomerOrderReturnReasonRepository.findByCustomerOrderId(id);
	}

}
