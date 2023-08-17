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
public class ElutionCustomerSettingTaxListView {

	private Long id;

	@SerializedName("name")
	String name;
	
	@SerializedName("description")
	String description;

	@SerializedName("applicable")
	String applicable;

	@SerializedName("applicable_value")
	Double applicableValue;

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
