package com.jema.app.exceptions;

import java.util.Date;

import lombok.Data;

@Data
public class DepartmentErrorResponse {
	private int status;
	private String error;
	private String message;
	private Date timestamp;
}