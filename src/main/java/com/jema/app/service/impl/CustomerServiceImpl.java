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

import com.google.gson.Gson;
import com.jema.app.dto.CustomerListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.Customer;
import com.jema.app.entities.Type;
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

		String email = customer.getEmail();
		Type existingTypeWithSameEmail = mCustomerRepository.findByNameIgnoreCase(email);
		if (existingTypeWithSameEmail != null && !existingTypeWithSameEmail.getId().equals(customer.getId())) {
			// A Type with the same name already exists for a different ID, throw a conflict
			// exception
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Customer with the same email already exists");
		}
		Customer savedCustomer = mCustomerRepository.save(customer);
		return String.valueOf(savedCustomer.getId());
	}

	@Override
	public Customer findById(String id) {
		Customer mCustomer = mCustomerRepository.findCustomerById(id);
		return mCustomer;
	}

	@Override
	public int delete(List<String> ids) {

		return mCustomerRepository.delete(ids);
	}

	@Override
	public int updateStatus(List<String> ids, Integer status) {
		return mCustomerRepository.updateStatus(ids, status);
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
		//code vlaue  cannot 
		Query query = null;
		try {
			query = entityManager.createNativeQuery(baseBuery, Tuple.class);
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		// set the maximum number of results to retrieve (i.e., the page size)
		query.setMaxResults(pageRequestDTO.getPageSize());

		// set the index of the first result to retrieve (i.e., the offset)
		query.setFirstResult(pageRequestDTO.getPageNumber() * pageRequestDTO.getPageSize());

		// execute the query and obtain the list of entities for the requested page
		List<Tuple> tuples = query.getResultList();

		List<CustomerListView> dataList = AppUtils.parseTuple(tuples, CustomerListView.class, gson);
		// attendanceView.convertIntoDTO(tuples);

		return dataList;

	}

	@Override
	public int block(List<String> ids, Integer block) {

		return mCustomerRepository.block(ids, block);
	}

	@Override
	public List<Customer> findAll() {

		return (List<Customer>) mCustomerRepository.findAll();
	}

}
