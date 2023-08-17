/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 15-Jun-2023
*
*/

package com.jema.app.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.cognitoidp.model.AliasExistsException;
import com.google.gson.Gson;
import com.jema.app.dto.CustomerListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.VendorListView;
import com.jema.app.entities.Customer;
import com.jema.app.entities.Employee;
import com.jema.app.repositories.CustomerRepository;
import com.jema.app.service.CustomerService;
import com.jema.app.utils.AppUtils;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository mCustomerRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;

	@Override
	public String save(Customer customer) {

		if (isEmailOrNameExists(customer.getEmail(), customer.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Customer with the same email or name already exists");
		}

		Customer savedCustomer = mCustomerRepository.save(customer);
		return String.valueOf(savedCustomer.getId()); // Convert ID to string
	}

	@Override
	public Customer findById(String id) {
		// TODO Auto-generated method stub
		Customer mCustomer = mCustomerRepository.findCustomerById(id);
		return mCustomer;
	}

	@Override
	public int delete(List<String> ids) {
		// TODO Auto-generated method stub
		return mCustomerRepository.delete(ids);
	}

	@Override
	public int updateStatus(String id, Integer status) {
		// TODO Auto-generated method stub
		return mCustomerRepository.updateStatus(id, status);
	}

	@Override
	public List<CustomerListView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, c.name as name, c.id as id, c.status as status, "
				+ "c.contact as contact, c.address as address, c.block as block, t.name as tax, t.id as tax_id "
				+ "from customer c " + "left join customer_setting_tax t on t.id = c.tax ";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where c.deleted!=1 and c.name ilike '%" + pageRequestDTO.getKeyword().trim()
					+ "%'";
		} else {
			baseBuery = baseBuery + "  where c.deleted!=1 ";
		}

		baseBuery = baseBuery
				+ " group by c.name, c.id, c.status, c.contact, c.address, c.block, t.name, t.id order by c.id DESC";
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

		List<CustomerListView> dataList = AppUtils.parseTuple(tuples, CustomerListView.class, gson);
//		attendanceView.convertIntoDTO(tuples);

		return dataList;

	}

	@Override
	public int block(String id, Integer block) {
		// TODO Auto-generated method stub
		return mCustomerRepository.block(id, block);
	}

	@Override
	public boolean isEmailOrNameExists(String email, String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Customer updateCustomer(String id, String newName, String newEmail) {
		Customer customer = mCustomerRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

		// Check if the new name is different and not already taken
		if (newName != null && !newName.equalsIgnoreCase(customer.getName())) {
			newName = getUniqueName(newName, id);
		}

		// Check if the new email is different and not already taken
		if (newEmail != null && !newEmail.equalsIgnoreCase(customer.getEmail())) {
			newEmail = getUniqueEmail(newEmail, id);
		}

		// Update the email and name with unique values
		if (newEmail != null) {
			customer.setEmail(newEmail);
		}
		if (newName != null) {
			customer.setName(newName);
		}

		return mCustomerRepository.save(customer);
	}

	private String getUniqueName(String newName, String id) {
		int appendCounter = 1;
		String originalName = newName;

		while (isEmailOrNameExists(null, newName, id)) {
			newName = originalName.toLowerCase() + "_" + appendCounter;
			appendCounter++;
		}

		return newName;
	}

	private String getUniqueEmail(String newEmail, String id) {
		int appendCounter = 1;
		String originalEmail = newEmail;

		while (isEmailOrNameExists(newEmail, null, id)) {
			newEmail = originalEmail.toLowerCase() + "_" + appendCounter;
			appendCounter++;
		}

		return newEmail;
	}

	@Override
	public boolean isEmailOrNameExists(String email, String name, String excludeCustomerId) {
		if (email != null && name != null) {
			return mCustomerRepository.existsByEmailIgnoreCaseOrNameIgnoreCaseAndIdNot(email, name, excludeCustomerId);
		} else if (email != null) {
			return mCustomerRepository.existsByEmailIgnoreCaseAndIdNot(email, excludeCustomerId);
		} else if (name != null) {
			return mCustomerRepository.existsByNameIgnoreCaseAndIdNot(name, excludeCustomerId);
		}
		return false;
	}
}
