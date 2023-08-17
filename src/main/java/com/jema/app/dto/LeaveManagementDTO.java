/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 08-May-2023
*
*/

package com.jema.app.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LeaveManagementDTO {

	private Long id;

	@NotNull(message = "Employee is mandatory")
	private Long employeeId;

	private Date date;

	@NotNull(message = "Leave Type is Mandatory")
	int leaveType;
}
