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

import com.jema.app.entities.ElutionOrderCancelReason;
import com.jema.app.repositories.ElutionOrderCancelReasonRepository;
import com.jema.app.service.ElutionOrderCancelReasonService;

@Service
public class ElutionOrderCancelReasonServiceImpl implements ElutionOrderCancelReasonService{

	@Autowired
	ElutionOrderCancelReasonRepository mElutionOrderCancelReasonRepository;
	
	@Override
	public Long save(ElutionOrderCancelReason mElutionOrderCancelReason) {
		// TODO Auto-generated method stub
		return mElutionOrderCancelReasonRepository.save(mElutionOrderCancelReason).getId();
	}

	@Override
	public List<ElutionOrderCancelReason> findById(String id) {
		// TODO Auto-generated method stub
		return mElutionOrderCancelReasonRepository.findByElutionOrderId(id);
	}

}
