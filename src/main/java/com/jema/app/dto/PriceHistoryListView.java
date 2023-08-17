/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 10-Jun-2023
*
*/

package com.jema.app.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PriceHistoryListView {

	@SerializedName("chemical_id")
	private Long chemicalId;

	@SerializedName("chemical_name")
	private String chemicalName;

	@SerializedName("chemical_code")
	String chemicalCode;
	
	@SerializedName("total_price")
	Double totalPrice;
	
	Double price;

	String comment;

	@SerializedName("createtime")
	Date createTime;
	
	@Column(name = "quantity")
	Long quantity;
	
	@JsonIgnore
	private Long total;

	

}
