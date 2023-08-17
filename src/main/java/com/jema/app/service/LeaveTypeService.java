/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 01-May-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.entities.LeaveType;

public interface LeaveTypeService {

	public Long save(LeaveType leaveType);

	public LeaveType findByName(String name);

	public List<LeaveType> findAll();

	public LeaveType findById(Long id);

	public int delete(List<Long> idsArrays);

	
}
