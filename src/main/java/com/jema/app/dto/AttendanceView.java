/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 06-May-2023
*
*/

package com.jema.app.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Component
@Data
public class AttendanceView {

	private Long id;

	private String name;

	@SerializedName("employee_id")
	private String employeeId;

	@SerializedName("emp_id")
	private Long empId;

	@SerializedName("leave_status")
	int leaveStatus;

	@SerializedName("leave_type")
	int leaveType;
	
	@SerializedName("leave_type_name")
	String leaveTypeName;

	private Date date;

	String designation;

	String department;

	@JsonIgnore
	private Long total;

}
