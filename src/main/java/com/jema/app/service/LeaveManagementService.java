/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 08-May-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.dto.LeaveManagementView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.LeaveManagement;

public interface LeaveManagementService {

	public Long save(LeaveManagement leaveManagement);

	public List<LeaveManagementView> findAll(PageRequestDTO pageRequestDTO);
	
	public List<LeaveManagement> findByEmployeeId(Long id);
}
