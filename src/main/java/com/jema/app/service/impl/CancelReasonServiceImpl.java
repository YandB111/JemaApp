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

import com.jema.app.entities.CancelReason;
import com.jema.app.repositories.CancelReasonRepository;
import com.jema.app.service.CancelReasonService;

@Service
public class CancelReasonServiceImpl implements CancelReasonService{

	@Autowired
	CancelReasonRepository cancelReasonRepository;
	
	@Override
	public Long save(CancelReason cancelReason) {
		// TODO Auto-generated method stub
		return cancelReasonRepository.save(cancelReason).getId();
	}

	@Override
	public List<CancelReason> findById(String id) {
		// TODO Auto-generated method stub
		return cancelReasonRepository.findByInventoryRequestId(id);
	}

}
