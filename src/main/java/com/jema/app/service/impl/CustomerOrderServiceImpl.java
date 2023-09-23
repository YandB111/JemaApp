/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 03-Jun-2023
*
*/

package com.jema.app.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.jema.app.dto.CustomerOrderDTO;
import com.jema.app.dto.CustomerOrderListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.Customer;
import com.jema.app.entities.CustomerOrder;
import com.jema.app.repositories.AccountDetailsRepository;
import com.jema.app.repositories.CustomerOrderRepository;
import com.jema.app.repositories.CustomerRepository;
import com.jema.app.service.CustomerOrderService;
import com.jema.app.utils.AppUtils;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;

	@Autowired
	CustomerOrderRepository mCustomerOrderRepository;


	@Autowired
	AccountDetailsRepository accountDetailsRepository;

	@Autowired
	CustomerRepository mCustomerRepository;

	
	@Transactional
	@Override
	public String save(CustomerOrder customerOrder) {
		// ... existing code ...

		// Calculate and update TotalAmountReceived
		Double totalAmountReceived = calculateTotalAmountReceived();
		customerOrder.setTotalAmountReceived(totalAmountReceived);

		// Save the customer order
		String id = mCustomerOrderRepository.save(customerOrder).getId();

		return id;

	}

	@Override
	public List<CustomerOrderListView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, c.id as chemical_id, c.name as chemical_name, c.image as chemical_image, "
				+ "v.id as customer_id, v.name as customer_name, "
				+ "r.id as id, r.status as status, r.status_comment as status_comment, r.returned as returned, r.cancel as cancel, r.price as price, r.quantity as quantity, r.orderdate as orderdate, "
				+ "r.total_tax as total_tax, r.total_price as total_price, r.mark_off as mark_off, r.comment as comment, r.createtime as createtime, o.comment as cancel_comment,t.comment as return_comment "
				+ "from customer_order r " + "left join chemical c on c.id = r.chemical_id "
				+ "left join customer v on v.id = r.customer_id "
				+ "left join customer_order_return_reason t on t.customer_order_id = r.id "
				+ "left join customer_order_cancel_reason o on o.customer_order_id = r.id ";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where c.name ilike '%" + pageRequestDTO.getKeyword().trim() + "%'";
		}

		baseBuery = baseBuery
				+ " group by c.id, c.name, c.image, v.id, v.name, r.id, r.status,r.status_comment,r.returned, r.cancel, r.mark_off, r.comment, r.createtime,o.comment,t.comment, r.price, r.quantity, r.orderdate, r.total_tax, r.total_price order by r.id DESC";
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

		List<CustomerOrderListView> dataList = AppUtils.parseTuple(tuples, CustomerOrderListView.class, gson);
//		attendanceView.convertIntoDTO(tuples);

		return dataList;

	}

	@Override
	public int updateStatus(String id, Integer status, String comment) {
		// TODO Auto-generated method stub
		return mCustomerOrderRepository.updateStatus(id, status, comment);
	}

	@Override
	public int markOff(String id, String comment, int MarkOff, Date date) {
		// TODO Auto-generated method stub
		return mCustomerOrderRepository.markOff(id, comment, MarkOff, date);
	}

	@Override
	public int cancel(String id, int cancel) {
		// TODO Auto-generated method stub
		return mCustomerOrderRepository.cancel(id, cancel);
	}

	@Override
	public int returned(String id, int returned) {
		// TODO Auto-generated method stub
		return mCustomerOrderRepository.returned(id, returned);
	}

	@Override
	public CustomerOrder findById(String id) {
		// TODO Auto-generated method stub
		Optional<CustomerOrder> mCustomerOrder = mCustomerOrderRepository.findById(id);
		if (mCustomerOrder.isPresent()) {
			return mCustomerOrder.get();
		}
		return null;
	}

	@Override
	public int updateInvoice(String id, Date invoiceDate, String invoiceURL, String invoiceNumber) {
		// TODO Auto-generated method stub
		return mCustomerOrderRepository.updateInvoice(id, invoiceDate, invoiceURL, invoiceNumber);
	}

	@Override
	public List<CustomerOrderListView> findAllByCustomerID(String customerID, PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, c.id as chemical_id, c.name as chemical_name, c.image as chemical_image, "
				+ "v.id as customer_id, v.name as customer_name, "
				+ "r.id as id, r.status as status, r.status_comment as status_comment, r.returned as returned, r.cancel as cancel, r.price as price, r.quantity as quantity, r.orderdate as orderdate, "
				+ "r.total_tax as total_tax, r.total_price as total_price, r.mark_off as mark_off, r.comment as comment, r.createtime as createtime "
				+ "from customer_order r " + "left join chemical c on c.id = r.chemical_id "
				+ "left join customer v on v.id = r.customer_id ";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where c.name ilike '%" + pageRequestDTO.getKeyword().trim()
					+ "%' and r.customer_id = '" + customerID + "'";
		} else {
			baseBuery = baseBuery + " where r.customer_id = '" + customerID + "'";
		}

		baseBuery = baseBuery
				+ " group by c.id, c.name, c.image, v.id, v.name, r.id, r.status,r.status_comment,r.returned, r.cancel, r.mark_off, r.comment, r.createtime, r.price, r.quantity, r.orderdate, r.total_tax, r.total_price order by r.id DESC";
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

		List<CustomerOrderListView> dataList = AppUtils.parseTuple(tuples, CustomerOrderListView.class, gson);
//		attendanceView.convertIntoDTO(tuples);

		return dataList;

	}

	@Override
	public int updatePrice(String id, Double price, Long quantity, Double total_price, Double total_tax) {
		// TODO Auto-generated method stub
		return mCustomerOrderRepository.updatePrice(id, price, quantity, total_price, total_tax);
	}


	@Override
	public Double calculateTotalAmountReceived() {
		List<CustomerOrder> allOrders = (List<CustomerOrder>) mCustomerOrderRepository.findAll();
		return allOrders.stream().mapToDouble(CustomerOrder::getPrice).sum();
	}

	@Override
	public List<CustomerOrder> findAllCustomerOrders() {
		return (List<CustomerOrder>) mCustomerOrderRepository.findAll();
	}



	

}
