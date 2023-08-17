/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 24-May-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.dto.ElutionCustomerSettingTaxListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.ElutionCustomerSettingTax;

public interface ElutionCustomerSettingTaxService {

	public Long save(ElutionCustomerSettingTax customerSettingTax);

	public List<ElutionCustomerSettingTaxListView> findAll(PageRequestDTO pageRequestDTO);

	public ElutionCustomerSettingTax findById(Long id);
	
	public int delete(List<Long> idsArrays);
	
	public int updateStatus(int status, List<Long> idsArrays);
}
