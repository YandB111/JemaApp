/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 13-May-2023
*
*/

package com.jema.app.dto;

import lombok.Data;

@Data
public class AccountDetailsDTO {

	private Long id;

	String accountNumber;

	String bankName;

	String bankBranch;

	Long balance;
}
