package com.jema.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jema.app.entities.ElutionFinanceAddRecord;

public interface ElutionAddRecordService {
	ElutionFinanceAddRecord save(ElutionFinanceAddRecord record);

	ElutionFinanceAddRecord update(Long id, ElutionFinanceAddRecord updatedRecord);

	void deleteRecordById(Long id);

	Page<ElutionFinanceAddRecord> getAllRecords(Pageable pageable);

	List<ElutionFinanceAddRecord> getRecordsByPaymentType(String paymentType);

	List<ElutionFinanceAddRecord> getRecordsByRecordType(String recordType);

	List<ElutionFinanceAddRecord> getRecordsByStatus(String status);

	List<ElutionFinanceAddRecord> getRecordsByAddType(String addType);

	List<ElutionFinanceAddRecord> getFilteredRecords(String typeName, String status, String recordType,
			String paymentType, String addType);

	List<ElutionFinanceAddRecord> findAllFinanceRecords();

	Double calculateTotalIncome();

	Double calculateTotalExpense();

	Optional<ElutionFinanceAddRecord> findById(Long id);

}
