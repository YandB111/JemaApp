/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 24-May-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.dto.CustomerSettingTaxListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.CustomerSettingTax;

public interface CustomerSettingTaxService {

	public Long save(CustomerSettingTax customerSettingTax);

	public List<CustomerSettingTaxListView> findAll(PageRequestDTO pageRequestDTO);

	public CustomerSettingTax findById(Long id);
	
	public int delete(List<Long> idsArrays);
	
	public int updateStatus(int status, List<Long> idsArrays);
}
