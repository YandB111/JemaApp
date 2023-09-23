package com.jema.app.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EllustionMachineErrorResponse {
	private boolean success;
    private String message;
    private Date lastDate;

}
