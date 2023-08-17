package com.jema.app.response;

import lombok.Data;

@Data
public class PasswordChangeResponse {
	 private boolean success;
	    private String message;

	    public PasswordChangeResponse(boolean success, String message) {
	        this.success = success;
	        this.message = message;
	    }

}
