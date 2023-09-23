/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 16-Jul-2023
*
*/

package com.jema.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.jema.app.dto.ElutionOrderListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.CustomerOrder;
import com.jema.app.entities.ElutionOrder;
import com.jema.app.repositories.ElutionOrderRepository;
import com.jema.app.service.ElutionOrderService;
import com.jema.app.utils.AppUtils;

@Service
public class ElutionOrderServiceImpl implements ElutionOrderService {

	@Autowired
	ElutionOrderRepository mElutionOrderRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;

	@Override
	public String save(ElutionOrder elutionOrder) {
		// TODO Auto-generated method stub
		return mElutionOrderRepository.save(elutionOrder).getId();
	}

	@Override
	public ElutionOrder findById(String id) {
		// TODO Auto-generated method stub
		Optional<ElutionOrder> order = mElutionOrderRepository.findById(id);
		if (order.isPresent()) {
			return order.get();
		}
		return null;
	}

	@Override
	public List<ElutionOrderListView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, "
				+ "v.id as customer_id, v.name as customer_name, r.carbon_details as carbon_details, "
				+ "r.id as id, r.status as status, r.cancel as cancel, r.date as date, r.time as time, r.loaded_date as loaded_date, r.loaded_time as loaded_time "
				+ "from elution_order r " + "left join elution_customer v on v.id = r.customer_id ";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where c.name ilike '%" + pageRequestDTO.getKeyword().trim() + "%'";
		}

		baseBuery = baseBuery
				+ " group by v.id, v.name, r.id, r.status, r.cancel, r.time, r.date , r.loaded_time,r.carbon_details, r.loaded_date order by r.id DESC";
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

		List<ElutionOrderListView> dataList = AppUtils.parseTuple(tuples, ElutionOrderListView.class, gson);
//		attendanceView.convertIntoDTO(tuples);

		return dataList;

	}
	
	@Override
	public int updateStatus(String id, Integer status) {
		// TODO Auto-generated method stub
		return mElutionOrderRepository.updateStatus(id, status);
	}

	@Override
	public int cancel(String id, int cancel) {
		// TODO Auto-generated method stub
		return mElutionOrderRepository.cancel(id, cancel);
	}

	@Override
	public int updateInvoice(String id, Date invoiceDate, String invoiceURL, String invoiceNumber) {
		// TODO Auto-generated method stub
		return mElutionOrderRepository.updateInvoice(id, invoiceDate, invoiceURL, invoiceNumber);
	}

	@Override
	public Double calculateTotalAmountReceived() {
		List<ElutionOrder> allOrders = (List<ElutionOrder>) mElutionOrderRepository.findAll();
		return allOrders.stream().mapToDouble(ElutionOrder::getPrice).sum();
	}
	
	
	
	@Override
	public List<ElutionOrderListView> findAllByCustomerID(String customerID, PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, "
				+ "v.id as customer_id, v.name as customer_name, r.carbon_details as carbon_details, "
				+ "r.id as id, r.status as status, r.cancel as cancel, r.date as date, r.time as time, r.loaded_date as loaded_date, r.loaded_time as loaded_time "
				+ "from elution_order r " + "left join elution_customer v on v.id = r.customer_id ";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where c.name ilike '%" + pageRequestDTO.getKeyword().trim()
					+ "%' and r.customer_id = '" + customerID + "'";
		} else {
			baseBuery = baseBuery + " where r.customer_id = '" + customerID + "'";
		}

		baseBuery = baseBuery
				+ " group by v.id, v.name, r.id, r.status, r.cancel, r.time, r.date , r.loaded_time,r.carbon_details, r.loaded_date order by r.id DESC";
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

		List<ElutionOrderListView> dataList = AppUtils.parseTuple(tuples, ElutionOrderListView.class, gson);
//		attendanceView.convertIntoDTO(tuples);

		return dataList;

	}
	
	
	
}
