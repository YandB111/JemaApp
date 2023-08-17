/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 24-May-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.dto.InventorySettingTaxListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.InventorySettingTax;

public interface InventorySettingTaxService {

	public Long save(InventorySettingTax inventorySettingTax);

	public List<InventorySettingTaxListView> findAll(PageRequestDTO pageRequestDTO);

	public InventorySettingTax findById(Long id);
	
	public int delete(List<Long> idsArrays);
	
	public int updateStatus(int status, List<Long> idsArrays);
}
