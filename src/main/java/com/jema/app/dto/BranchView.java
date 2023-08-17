/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 15-May-2023
*
*/

package com.jema.app.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Component
@Data
public class BranchView {

	private Long id;

	String image;

	String name;

	String contact;
	
	String email;

	Long leader;

	@SerializedName("leadername")
	String leaderName;

	@SerializedName("totalemployee")
	Long totalEmployee;

	@SerializedName("status")
	Integer status;
	
	@SerializedName("location")
	String location;

	@JsonIgnore
	private Long total;
}
