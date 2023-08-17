package com.jema.app.response;

/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 19-Mar-2023
*
*/

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ErrorResponse {

	private Date timestamp;
	private int status;
	private Object error;
	private String message;

	public ErrorResponse getInstance(String message, HttpStatus status) {
		this.status = status.value();
		this.error = status.getReasonPhrase();
		this.message = message;
		this.timestamp = new Date();
		return this;
	}
	
	public ErrorResponse getInstance(String message, Object error, HttpStatus status) {
		this.status = status.value();
		this.error = error;
		this.message = message;
		this.timestamp = new Date();
		return this;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public int getStatus() {
		return status;
	}

	public Object getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setError(Object error) {
		this.error = error;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
