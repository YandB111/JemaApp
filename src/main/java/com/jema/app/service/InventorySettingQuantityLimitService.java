/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 21-May-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.dto.InventorySettingQuantityLimitListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.InventorySettingQuantityLimit;

public interface InventorySettingQuantityLimitService {

	public Long save(InventorySettingQuantityLimit inventorySetting);
	
	public InventorySettingQuantityLimit findById(Long id);
	
	public InventorySettingQuantityLimit findByChemical(Long chemical);
	
	public int delete(List<Long> idsArrays);
	
	public List<InventorySettingQuantityLimitListView> findAll(PageRequestDTO pageRequestDTO);
}
