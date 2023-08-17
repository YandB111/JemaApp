/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 25-May-2023
*
*/

package com.jema.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class TaxListView {

	private Long id;

	@SerializedName("name")
	String name;
	
	@SerializedName("description")
	String description;

	@SerializedName("applicable")
	String applicable;

	@SerializedName("applicable_value")
	Double applicableValue;

	@SerializedName("type")
	Long type;
	
	@SerializedName("type_name")
	String typeName;

	@SerializedName("company_contribute")
	int companyContribute;
	
	@SerializedName("status")
	int status;

	@SerializedName("company_applicable")
	String companyApplicable;

	@SerializedName("company_applicable_value")
	Double companyApplicableValue;

	@JsonIgnore
	private Long total;

}
