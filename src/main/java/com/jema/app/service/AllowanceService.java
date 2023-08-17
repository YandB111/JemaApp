/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 28-Apr-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.entities.Allowance;

public interface AllowanceService {

	public Long save(Allowance allowance);

	public List<Allowance> findAll();

	public Allowance findById(Long id);
	
	public int delete(List<Long> idsArrays);
	
	public int updateStatus(int status, List<Long> idsArrays);
}
