/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 14-May-2023
*
*/

package com.jema.app.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Component
@Data
public class DepartmentView {

	private Long id;

	String name;

	String code;

	@SerializedName("managedby")
	Long managedBy;
	
	@SerializedName("managedbyname")
	String managedByName;
	
	private Long branch;
	
	@SerializedName("branchname")
	String branchName;

	Integer status;

	@JsonIgnore
	private Long total;
}
