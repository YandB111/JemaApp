/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 10-Apr-2023
*
*/

package com.jema.app.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LoginDTO {

	@NotNull
	@NotEmpty(message = "Username is mandatory")
	String username;

	@NotNull
	@NotEmpty(message = "Password is mandatory")
	String password;

}
