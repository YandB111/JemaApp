/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 06-May-2023
*
*/

package com.jema.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jema.app.entities.Attendance;
import com.jema.app.repositories.AttendanceRepository;
import com.jema.app.service.AttendanceService;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	AttendanceRepository attendanceRepository;

	@Override
	public Page<Attendance> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return attendanceRepository.findAll(pageable);
	}

	@Override
	public Long save(List<Attendance> attendances) {
		// TODO Auto-generated method stub
		attendanceRepository.saveAll(attendances);
		return 1L;
	}

	@Override
	public Page<Attendance> findAllByName(String name, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attendance findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int markAttendance(int status, List<Long> idsArrays) {
		// TODO Auto-generated method stub
		attendanceRepository.updateLeaveType(status, idsArrays);
		return 1;
	}

	@Override
	public Long getCount(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
