package com.jema.app.response;

import java.util.Date;

import lombok.Data;

@Data   
public class ResponseErrorChemical {
	private Date timestamp;
	private int status;
	private String error;
	private String message;

}
