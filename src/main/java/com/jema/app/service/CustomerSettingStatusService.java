/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 14-Jun-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.dto.CustomerSettingStatusListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.CustomerSettingStatus;

public interface CustomerSettingStatusService {

	public Long save(CustomerSettingStatus customerSettingStatus);

	public List<CustomerSettingStatusListView> findAll(PageRequestDTO pageRequestDTO);

	public CustomerSettingStatus findById(Long id);
	
	public int delete(List<Long> idsArrays);
	
	public int updateStatus(int status, List<Long> idsArrays);
}
