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

import com.jema.app.entities.InventoryStatusHistory;
import com.jema.app.repositories.InventoryStatusHistoryRepository;
import com.jema.app.service.InventoryStatusHistoryService;

@Service
public class InventoryStatusHistoryServiceImpl implements InventoryStatusHistoryService {

	@Autowired
	InventoryStatusHistoryRepository inventoryStatusHistoryRepository;

	@Override
	public Long save(InventoryStatusHistory inventoryStatusHistory) {
		// TODO Auto-generated method stub
		return inventoryStatusHistoryRepository.save(inventoryStatusHistory).getId();
	}

	@Override
	public List<InventoryStatusHistory> findById(String inventoryRequestId) {
		// TODO Auto-generated method stub
		return inventoryStatusHistoryRepository.findByInventoryRequestId(inventoryRequestId);
	}


}
