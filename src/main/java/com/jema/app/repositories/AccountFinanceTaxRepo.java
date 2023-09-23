package com.jema.app.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jema.app.entities.AccountFinanceTax;

public interface AccountFinanceTaxRepo extends CrudRepository<AccountFinanceTax, Long> {
	List<AccountFinanceTax> findByTaxType(String taxType);

	List<AccountFinanceTax> findAllByOrderByIdDesc();

}
