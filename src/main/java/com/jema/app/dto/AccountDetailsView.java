/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 13-May-2023
*
*/

package com.jema.app.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Component
@Data
public class AccountDetailsView {

	private Long id;

	@SerializedName("account_number")
	String accountNumber;

	@SerializedName("bank_name")
	String bankName;

	@SerializedName("bank_branch")
	String bankBranch;

	@SerializedName("balance")
	Long balance;
	
	@JsonIgnore
	private Long total;
}
