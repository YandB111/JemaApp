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
import org.springframework.stereotype.Service;

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
		   String name = customer.getName();
	        String email = customer.getEmail();

	        // Check if an Employee with the given name or email already exists
	        if (mCustomerRepository.existsName(name) && mCustomerRepository.existsEmail(email)) {
	            throw new IllegalArgumentException("Customer with the same name and email already exists.");
	        } else if (mCustomerRepository.existsName(name)) {
	            throw new IllegalArgumentException("Customer with the same name already exists.");
	        } else if (mCustomerRepository.existsEmail(email)) {
	            throw new IllegalArgumentException("Customer with the same email already exists.");
	        }

	        Customer savedCustomer = mCustomerRepository.save(customer);
	        return savedCustomer.getId();
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
}
