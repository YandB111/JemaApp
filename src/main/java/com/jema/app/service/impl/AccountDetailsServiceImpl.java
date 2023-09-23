/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 13-May-2023
*
*/

package com.jema.app.service.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.jema.app.dto.AccountDetailsView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.AccountDetails;

import com.jema.app.entities.Employee;
import com.jema.app.entities.SalaryDetails;
import com.jema.app.repositories.AccountDetailsRepository;
import com.jema.app.repositories.EmployeeRepository;

import com.jema.app.service.AccountDetailsService;
import com.jema.app.utils.AppUtils;

@Service
public class AccountDetailsServiceImpl implements AccountDetailsService {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private EmployeeRepository employeeRepository; // Inject your Employee repository


	@Autowired
	private Gson gson;

	@Autowired
	AccountDetailsRepository accountDetailsRepository;

	@Override
	public Long save(AccountDetails accountDetails) {
		// TODO Auto-generated method stub
		return accountDetailsRepository.save(accountDetails).getId();
	}

	@Override
	public AccountDetails findById(Long id) {
		// TODO Auto-generated method stub
		Optional<AccountDetails> accountDetails = accountDetailsRepository.findById(id);
		if (accountDetails.isPresent()) {
			return accountDetails.get();
		}
		return null;
	}

	@Override
	public int delete(List<Long> idsArrays) {
		// TODO Auto-generated method stub
		accountDetailsRepository.deleteAllById(idsArrays);
		return 1;
	}

	@Override
	public int updateBalance(Long id, Long balance) {
		// TODO Auto-generated method stub
		return accountDetailsRepository.updateBalance(id, balance);
	}

	@Override
	public List<AccountDetailsView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub

		String baseBuery = "select count(*) over() as total, ad.id id, ad.bank_name bank_name, ad.bank_branch bank_branch, ad.account_number account_number, ad.balance balance from account_details ad";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where ad.bank_name ilike '%" + pageRequestDTO.getKeyword().trim() + "%'";
		}

		baseBuery = baseBuery
				+ " group by ad.id, ad.bank_name , ad.bank_branch , ad.account_number , ad.balance order by ad.id DESC";
		// create a query to retrieve MyEntity objects
		Query query = null;
		try {
			query = entityManager.createNativeQuery(baseBuery, Tuple.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// set the maximum number of results to retrieve (i.e., the page size)
		query.setMaxResults(pageRequestDTO.getPageSize());

		// set the index of the first result to retrieve (i.e., the offset)
		query.setFirstResult(pageRequestDTO.getPageNumber() * pageRequestDTO.getPageSize());

		// execute the query and obtain the list of entities for the requested page
		List<Tuple> tuples = query.getResultList();

		List<AccountDetailsView> dataList = AppUtils.parseTuple(tuples, AccountDetailsView.class, gson);
//		attendanceView.convertIntoDTO(tuples);

		return dataList;
	}


	public void subtractAmountFromBalance(Long accountId, Long amount) {
		int rowsUpdated = accountDetailsRepository.updateBalance(accountId, amount);
		if (rowsUpdated != 1) {
			throw new RuntimeException("Failed to update balance.");
		}
	}

	@Override
	@Transactional
	public void calculateAndSaveTotalBalance() {
		List<AccountDetails> accounts = accountDetailsRepository.findAll();

		for (AccountDetails account : accounts) {
			Employee employee = account.getEmployee();
			if (employee != null) {
				SalaryDetails salaryDetails = employee.getSalaryDetails();
				if (salaryDetails != null) {
					salaryDetails.calculateAndSetTotalValue(); // Calculate the totalSalary
					account.setTotalBalance(salaryDetails.getTotalSalary().longValue()); // Set the total_balance
					accountDetailsRepository.save(account); // Save the AccountDetails with the updated total_balance
				}
			}
		}

	}

	public List<Long> getAllBalances() {
		return accountDetailsRepository.getAllBalances();
	}

	public Long calculateTotalBalance() {
		return accountDetailsRepository.calculateTotalBalance();
	}


}
