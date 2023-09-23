package com.jema.app.response;

import java.util.List;

import com.jema.app.entities.ElutionFinanceAddRecord;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElutionFinanceAddRecordResponse {
	
	 private List<ElutionFinanceAddRecord> records;
	    private long totalCount;

}
