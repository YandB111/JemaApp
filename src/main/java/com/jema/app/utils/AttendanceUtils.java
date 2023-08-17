/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 06-May-2023
*
*/

package com.jema.app.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jema.app.entities.Attendance;
import com.jema.app.entities.Employee;
import com.jema.app.service.EmployeeService;

@Component
public class AttendanceUtils {

	@Autowired
	EmployeeService employeeService;

	private static final Logger LOGGER = LoggerFactory.getLogger(AttendanceUtils.class);

	public List<Attendance> getAttendance() {

		List<Employee> mEmployeeList = employeeService.findAll();
		List<Attendance> attendances = getAttendanceData(mEmployeeList);
		return attendances;
	}

	private List<Attendance> getAttendanceData(List<Employee> mEmployeeList) {

		List<Attendance> mAttendanceList = new ArrayList<>();
		Attendance mAttendance;
		for (int i = 0; i < mEmployeeList.size(); i++) {
			mAttendance = new Attendance();

			mAttendance.setEmpId(mEmployeeList.get(i).getId());
			mAttendance.setCreateTime(new Date());
			mAttendance.setUpdateTime(new Date());
			mAttendance.setDate(new Date());
			mAttendance.setLeaveStatus(0);
			mAttendance.setLeaveType(0);

			mAttendanceList.add(mAttendance);
		}

		return mAttendanceList;
	}
}
