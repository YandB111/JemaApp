package com.jema.app.response;

import lombok.Data;

@Data
public class PaymentResponse {
	private String message;
	
	private Long totalAmountPaid; // Add this field

    public PaymentResponse(String message, Long totalAmountPaid) { // Update the constructor
        this.message = message;
        this.totalAmountPaid = totalAmountPaid;
    }


	public PaymentResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
