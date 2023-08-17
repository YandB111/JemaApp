package com.jema.app.dto;

import java.util.List;

import lombok.Data;

@Data
public class SubstractAmountRequestDTO {

	private Long accountId;

	private Long AmountToBePaid;
	private List<Long> employeeIds;
}
