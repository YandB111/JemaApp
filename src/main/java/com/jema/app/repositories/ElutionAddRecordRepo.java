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

import com.jema.app.entities.ElutionFinanceAddRecord;

@Repository
public interface ElutionAddRecordRepo extends CrudRepository<ElutionFinanceAddRecord, Long> {
	@Modifying
	void deleteById(@Param("id") Long id);

	Page<ElutionFinanceAddRecord> findAll(Pageable pageable);

	List<ElutionFinanceAddRecord> findByPaymentTypeContainingIgnoreCase(String paymentType);

	List<ElutionFinanceAddRecord> findByRecordTypeContainingIgnoreCase(String recordType);

	List<ElutionFinanceAddRecord> findByStatus(String status);

	List<ElutionFinanceAddRecord> findByAddType(String addType);

	List<ElutionFinanceAddRecord> findAll(Specification<ElutionFinanceAddRecord> spec);

	@Query("SELECT SUM(f.amount) FROM ElutionFinanceAddRecord f WHERE f.recordType = 'Income'")
	Double calculateTotalIncome();

	@Query("SELECT SUM(f.amount) FROM ElutionFinanceAddRecord f WHERE f.recordType = 'Expense'")
	Double calculateTotalExpense();

	long count();

}
