/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 26-May-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.dto.InventorySettingStatusListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.InventorySettingStatus;

public interface InventorySettingStatusService {

	public Long save(InventorySettingStatus inventorySettingStatus);

	public List<InventorySettingStatusListView> findAll(PageRequestDTO pageRequestDTO);

	public InventorySettingStatus findById(Long id);
	
	public int delete(List<Long> idsArrays);
	
	public int updateStatus(int status, List<Long> idsArrays);
	
}
