/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 22-Jul-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.entities.ElutionMachineServiceEntity;
import com.jema.app.dto.ElutionMachineServiceListView;
import com.jema.app.dto.PageRequestDTO;

public interface ElutionMachineServiceService {

	public Long save(ElutionMachineServiceEntity mElutionMachineServiceEntity);
	
	public List<ElutionMachineServiceListView> findAll(PageRequestDTO pageRequestDTO);
}
