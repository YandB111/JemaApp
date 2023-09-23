package com.jema.app.response;

import java.util.Date;

import lombok.Data;
@Data
public class ConflictErrorResponse {
	private int status;
	private String error;
	private String message;
	private Date timestamp;

}
