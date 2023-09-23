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

import com.jema.app.entities.ElutionOrderStatusHistory;
import com.jema.app.repositories.ElutionOrderStatusHistoryRepository;
import com.jema.app.service.ElutionOrderStatusHistoryService;

@Service
public class ElutionOrderStatusHistoryServiceImpl implements ElutionOrderStatusHistoryService {

	@Autowired
	ElutionOrderStatusHistoryRepository mElutionOrderStatusHistoryRepository;

	@Override
	public Long save(ElutionOrderStatusHistory mElutionOrderStatusHistory) {
		// TODO Auto-generated method stub
		return mElutionOrderStatusHistoryRepository.save(mElutionOrderStatusHistory).getId();
	}

	@Override
	public List<ElutionOrderStatusHistory> findById(String mElutionOrderId) {
		// TODO Auto-generated method stub
		return mElutionOrderStatusHistoryRepository.findByOrderId(mElutionOrderId);
	}

}
