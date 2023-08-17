/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 14-Jun-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.dto.ElutionMachineRawListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.ElutionMachineRaw;

public interface ElutionMachineRawService {

	public Long save(ElutionMachineRaw mElutionMachineRaw);

	public List<ElutionMachineRawListView> findAll(PageRequestDTO pageRequestDTO);

	public ElutionMachineRaw findById(Long id);
	
	public int delete(List<Long> idsArrays);
	
	public int updateStatus(int status, List<Long> idsArrays);
}
