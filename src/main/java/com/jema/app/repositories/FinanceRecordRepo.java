package com.jema.app.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jema.app.entities.ElutionAccountInvoice;
import com.jema.app.entities.FinanceAddRecordRequest;

@Repository
public interface FinanceRecordRepo extends CrudRepository<FinanceAddRecordRequest, Long> {

	@Modifying
	void deleteById(@Param("id") Long id);

	Page<FinanceAddRecordRequest> findAll(Pageable pageable);

	List<FinanceAddRecordRequest> findByPaymentTypeContainingIgnoreCase(String paymentType);

	List<FinanceAddRecordRequest> findByRecordTypeContainingIgnoreCase(String recordType);

	List<FinanceAddRecordRequest> findByStatus(String status);

	List<FinanceAddRecordRequest> findByAddType(String addType);

	List<FinanceAddRecordRequest> findAll(Specification<FinanceAddRecordRequest> spec);

	@Query("SELECT SUM(f.amount) FROM FinanceAddRecordRequest f WHERE f.recordType = 'Income'")
	Double calculateTotalIncome();

	@Query("SELECT SUM(f.amount) FROM FinanceAddRecordRequest f WHERE f.recordType = 'Expense'")
	Double calculateTotalExpense();

	
	
}