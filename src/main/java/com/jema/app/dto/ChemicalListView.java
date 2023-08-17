/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 20-May-2023
*
*/

package com.jema.app.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ChemicalListView {

	Long id;

	String image;

	String name;

	String code;

	String description;

	Double price;

	Long quantity;

	@SerializedName("mssd")
	String MSSD;

	@SerializedName("hs_code")
	String hsCode;

	@SerializedName("expiredate")
	Date expireDate;
	
	int status;
	
	@JsonIgnore
	private Long total;
}
