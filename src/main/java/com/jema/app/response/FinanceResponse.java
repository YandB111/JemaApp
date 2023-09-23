package com.jema.app.response;

import java.util.List;

import com.jema.app.entities.FinanceAddRecordRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceResponse {
	 private List<FinanceAddRecordRequest> records;
	    private long totalCount;
}
