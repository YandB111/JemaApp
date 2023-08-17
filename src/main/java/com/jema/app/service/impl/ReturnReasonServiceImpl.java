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

import com.jema.app.entities.ReturnReason;
import com.jema.app.repositories.ReturnReasonRepository;
import com.jema.app.service.ReturnReasonService;

@Service
public class ReturnReasonServiceImpl implements ReturnReasonService{

	@Autowired
	ReturnReasonRepository mReturnReasonRepository;
	
	@Override
	public Long save(ReturnReason mReturnReason) {
		// TODO Auto-generated method stub
		return mReturnReasonRepository.save(mReturnReason).getId();
	}

	@Override
	public List<ReturnReason> findById(String id) {
		// TODO Auto-generated method stub
		return mReturnReasonRepository.findByInventoryRequestId(id);
	}

}
