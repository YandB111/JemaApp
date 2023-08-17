/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 14-Jun-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.dto.ElutionCustomerSettingStatusListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.ElutionCustomerSettingStatus;

public interface ElutionCustomerSettingStatusService {

	public Long save(ElutionCustomerSettingStatus customerSettingStatus);

	public List<ElutionCustomerSettingStatusListView> findAll(PageRequestDTO pageRequestDTO);

	public ElutionCustomerSettingStatus findById(Long id);
	
	public int delete(List<Long> idsArrays);
	
	public int updateStatus(int status, List<Long> idsArrays);
}
