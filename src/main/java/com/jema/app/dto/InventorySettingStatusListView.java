/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 26-May-2023
*
*/

package com.jema.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class InventorySettingStatusListView {

	private Long id;

	@SerializedName("name")
	String name;
	
	@SerializedName("description")
	String description;

	@SerializedName("refer_to_department")
	int referToDepartment;
	
	@SerializedName("department")
	Long department;
	
	@SerializedName("department_name")
	String departmentName;

	@SerializedName("can_add_department")
	int canAddDocument;
	
	@SerializedName("can_add_comment")
	int canAddComment;	
	
	@SerializedName("status")
	int status;
	
	@JsonIgnore
	private Long total;
}
