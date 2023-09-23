/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 16-Jul-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.dto.ElutionMachineListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.ElutionMachine;

public interface ElutionMachineService {

	public String save(ElutionMachine elutionMachine);
	
	public List<ElutionMachineListView> findAll(PageRequestDTO pageRequestDTO);
	
	public int updateFunctionalType(String id,String functionalType);
	
	public int delete(List<String> ids);
	
	public ElutionMachine findById(String id);
}
