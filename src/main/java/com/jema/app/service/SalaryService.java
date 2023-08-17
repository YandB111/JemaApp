/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 12-May-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.SalariesView;
import com.jema.app.entities.Salary;

public interface SalaryService {

	public Long save(List<Salary> salaries);

	public List<SalariesView> findAll(PageRequestDTO pageRequestDTO);

	public List<SalariesView> findByEmployeeId(Long id);
	
	public int updateStatus(int status, List<Long> idsArrays);
}
