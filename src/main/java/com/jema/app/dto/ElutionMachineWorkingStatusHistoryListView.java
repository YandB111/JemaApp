/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 22-Jul-2023
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
public class ElutionMachineWorkingStatusHistoryListView {

	@SerializedName("id")
	private Long id;
	
	@SerializedName("machine_id")
	private String machine_id;
	
	@SerializedName("name")
	String name;
	
	@SerializedName("image")
	String image;

	@SerializedName("functional_type")
	String functionalType;
	
	@SerializedName("createtime")
	private Date createTime;

	@JsonIgnore
	private Long total;
}
