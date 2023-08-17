/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 17-May-2023
*
*/

package com.jema.app.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Component
@Data
public class EmployeeListView {

	private Long id;

	String name;

	@SerializedName("employee_id")
	String employeeId;

	String contact;

	String email;

	@SerializedName("designation")
	String designation;

	@SerializedName("department")
	private String department;

	@SerializedName("branch")
	private String branch;

	@SerializedName("image")
	private String image;
	
	@SerializedName("status")
	int status;

	@JsonIgnore
	private Long total;
}
