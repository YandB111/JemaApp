package com.jema.app.response;

/* 
*  Project : Jema
*  Author  : Raj Khatri
*  Date    : 10-Mar-2023
*
*/

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class GenericResponse {

	private Date timestamp;
	private int status;
	private String message;

	private Object data;

	public GenericResponse getSuccess(Object t, HttpStatus status) {
		this.timestamp = new Date();
		this.status = status.value();
		this.message = "Success";
		this.data = t;
		return this;
	}

	public GenericResponse getResponse(String message, HttpStatus status) {
		this.timestamp = new Date();
		this.status = status.value();
		this.message = message;
		this.data = null;
		return this;
	}

	public GenericResponse getResponse(Object t, String message, HttpStatus status) {
		this.timestamp = new Date();
		this.status = status.value();
		this.message = message;
		this.data = t;
		return this;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "GenericResponse [timestamp=" + timestamp + ", status=" + status + ", message=" + message + ", data="
				+ data.toString() + "]";
	}
	
}
