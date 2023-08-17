/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 15-Jun-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.dto.CustomerListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.Customer;

public interface CustomerService {

	public String save(Customer customer);
	
	public List<CustomerListView> findAll(PageRequestDTO pageRequestDTO);
	
	public Customer findById(String id);
	
	public int delete(List<String> ids);

	public int updateStatus(String id, Integer status);
	
	public int block(String id, Integer status);
}
