/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 11-Jun-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.entities.InventoryStatusHistory;

public interface InventoryStatusHistoryService {

	public Long save(InventoryStatusHistory inventoryStatusHistory);

	public List<InventoryStatusHistory> findById(String inventoryRequestId);
	
	
}
