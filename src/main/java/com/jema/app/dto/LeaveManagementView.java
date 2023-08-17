/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 09-May-2023
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
public class LeaveManagementView {

	private Long id;

	private String name;
	
	@SerializedName("employee_id")
	private String employeeId;

	@SerializedName("emp_id")
	private Long empId;

	@SerializedName("leave_type")
	int leaveType;
	
	@SerializedName("leave_status")
	int leaveStatus;
	
	@SerializedName("leave_type_name")
	String leaveTypeName;
	
	String designation;
	
	String department;

	private Date date;
	
	@JsonIgnore
	private Long total;
}
