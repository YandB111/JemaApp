package com.jema.app.response;

import com.jema.app.entities.Employee;

import lombok.Data;

@Data
public class SalaryPaymentResponse {
	private boolean success;
    private String message;

	public void setData(Employee updatedEmployee) {
		// TODO Auto-generated method stub
		
	}

}
