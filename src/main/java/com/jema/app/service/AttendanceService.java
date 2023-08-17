/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 06-May-2023
*
*/

package com.jema.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jema.app.entities.Attendance;

public interface AttendanceService {

	public Long save(List<Attendance> attendances);

	public Page<Attendance> findAll(Pageable pageable);
	
	public Page<Attendance> findAllByName(String name, Pageable pageable);

	public Attendance findById(Long id);
	
	public int markAttendance(int status, List<Long> idsArrays);
	
	public Long getCount(String name);

}
