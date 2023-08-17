package com.jema.app.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentErrorResponse {
	// I use the code showing error in every unique value for code and name both
	private int status;
	private String error;
	private String message;
	private Date timestamp;
}
