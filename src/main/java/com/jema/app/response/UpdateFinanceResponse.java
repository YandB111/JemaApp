package com.jema.app.response;

import com.jema.app.entities.FinanceAddRecordRequest;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class UpdateFinanceResponse {
	private boolean success;
    private String message;
    private FinanceAddRecordRequest updatedRecord;
}
