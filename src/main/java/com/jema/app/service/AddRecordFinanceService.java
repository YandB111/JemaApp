package com.jema.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jema.app.entities.FinanceAddRecordRequest;

public interface AddRecordFinanceService {

	FinanceAddRecordRequest save(FinanceAddRecordRequest record);

	FinanceAddRecordRequest update(Long id, FinanceAddRecordRequest updatedRecord);

	void deleteRecordById(Long id);

	Page<FinanceAddRecordRequest> getAllRecords(Pageable pageable);

	List<FinanceAddRecordRequest> getRecordsByPaymentType(String paymentType);

	List<FinanceAddRecordRequest> getRecordsByRecordType(String recordType);

	List<FinanceAddRecordRequest> getRecordsByStatus(String status);

	List<FinanceAddRecordRequest> getRecordsByAddType(String addType);

	List<FinanceAddRecordRequest> getFilteredRecords(String typeName, String status, String recordType,
			String paymentType, String addType);

	List<FinanceAddRecordRequest> findAllFinanceRecords();

	Double calculateTotalIncome();

	Double calculateTotalExpense();

	Optional<FinanceAddRecordRequest> findById(Long id);

	
}
