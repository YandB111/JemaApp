/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 14-Jun-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.dto.ElutionCustomerSettingLeadStatusListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.ElutionCustomerSettingLeadStatus;

public interface ElutionCustomerSettingLeadStatusService {

	public Long save(ElutionCustomerSettingLeadStatus customerSettingLeadStatus);

	public List<ElutionCustomerSettingLeadStatusListView> findAll(PageRequestDTO pageRequestDTO);

	public ElutionCustomerSettingLeadStatus findById(Long id);
	
	public int delete(List<Long> idsArrays);
	
	public int updateStatus(int status, List<Long> idsArrays);
}
