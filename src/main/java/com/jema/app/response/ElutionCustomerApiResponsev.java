package com.jema.app.response;

import lombok.Data;

@Data
public class ElutionCustomerApiResponsev<T> {
	private boolean success;
	private String message;
	private T data;

}
