/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 12-May-2023
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
public class SalariesView {

	private Long id;

	private String name;
	
	@SerializedName("employee_id")
	private String employeeId;

	@SerializedName("emp_id")
	private Long empId;

	@SerializedName("gross")
	Long gross;
	
	@SerializedName("status")
	Long status;

	@SerializedName("deduction")
	double deduction;
	
	@SerializedName("taxes")
	double taxes;

	private Date date;

	@JsonIgnore
	private Long total;
}
