/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 05-Jun-2023
*
*/

package com.jema.app.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class InventoryRequestListView {

	private String id;

	@SerializedName("chemical_id")
	private Long chemicalId;

	@SerializedName("chemical_name")
	private String chemicalName;

	@SerializedName("chemical_image")
	private String chemicalImage;

	@SerializedName("vendor_id")
	private Long vendorId;

	@SerializedName("vendor_name")
	private String vendorName;

	Double price;

	Long quantity;

	@SerializedName("requireddate")
	Date requiredDate;

	@SerializedName("total_tax")
	Double totalTax;

	@SerializedName("total_price")
	Double totalPrice;

	int status;
	
	int cancel;
	
	@SerializedName("cancel_comment")
	String cancelComment;

	int returned;

	@SerializedName("return_comment")
	String returnComment;
		
	@SerializedName("mark_off")
	int markOff;
	
	@SerializedName("comment")
	String comment;
	
	@SerializedName("status_comment")
	String statusComment;
	
	@SerializedName("createtime")
	private Date createTime;
	
	@JsonIgnore
	private Long total;

}
