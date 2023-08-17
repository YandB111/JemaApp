/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 15-Jun-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.dto.ElutionCustomerListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.ElutionCustomer;

public interface ElutionCustomerService {

	public String save(ElutionCustomer elutionCustomer);
	
	public List<ElutionCustomerListView> findAll(PageRequestDTO pageRequestDTO, Integer status);
	
	public ElutionCustomer findById(String id);
	
	public int delete(List<String> ids);

	public int updateStatus(String id, Integer status);
	
	public int block(String id, Integer status);
}
