/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 23-Apr-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.TaxListView;
import com.jema.app.entities.Tax;

public interface TaxService {

	public Long save(Tax tax);

	public List<TaxListView> findAll(PageRequestDTO pageRequestDTO);

	public Tax findById(Long id);
	
	public int delete(List<Long> idsArrays);
	
	public int updateStatus(int status, List<Long> idsArrays);
}
