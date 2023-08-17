/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 23-Jul-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.dto.ElutionMachineWorkingStatusHistoryListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.ElutionMachineWorkingStatusHistory;

public interface ElutionMachineWorkingStatusHistoryService {

	public Long save(ElutionMachineWorkingStatusHistory mElutionMachineWorkingStatusHistory);

	public List<ElutionMachineWorkingStatusHistoryListView> findAll(PageRequestDTO pageRequestDTO);
}
