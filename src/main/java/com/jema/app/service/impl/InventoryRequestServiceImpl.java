/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 03-Jun-2023
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
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.jema.app.dto.InventoryRequestListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.InventoryRequest;
import com.jema.app.repositories.InventoryRequestRepository;
import com.jema.app.service.InventoryRequestService;
import com.jema.app.utils.AppUtils;

@Service
public class InventoryRequestServiceImpl implements InventoryRequestService {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;

	@Autowired
	InventoryRequestRepository inventoryRequestRepository;

	@Transactional
	@Override
	public String save(InventoryRequest inventoryRequest) {
		// TODO Auto-generated method stub
		return inventoryRequestRepository.save(inventoryRequest).getId();
	}

	@Override
	public List<InventoryRequestListView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, c.id as chemical_id, c.name as chemical_name, c.image as chemical_image, "
				+ "v.id as vendor_id, v.name as vendor_name, "
				+ "r.id as id, r.status as status, r.status_comment as status_comment, r.returned as returned, r.cancel as cancel, r.price as price, r.quantity as quantity, r.requireddate as requireddate, "
				+ "r.total_tax as total_tax, r.total_price as total_price, r.mark_off as mark_off, r.comment as comment, r.createtime as createtime, o.comment as cancel_comment,t.comment as return_comment "
				+ "from inventory_request r " + "left join chemical c on c.id = r.chemical_id "
				+ "left join vendor v on v.id = r.vendor_id "
				+ "left join return_reason t on t.inventory_request_id = r.id "
				+ "left join cancel_reason o on o.inventory_request_id = r.id ";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where c.name ilike '%" + pageRequestDTO.getKeyword().trim() + "%'";
		}

		baseBuery = baseBuery
				+ " group by c.id, c.name, c.image, v.id, v.name, r.id, r.status,r.status_comment,r.returned, r.cancel, r.mark_off, r.comment, r.createtime,o.comment,t.comment, r.price, r.quantity, r.requireddate, r.total_tax, r.total_price order by r.id DESC";
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

		List<InventoryRequestListView> dataList = AppUtils.parseTuple(tuples, InventoryRequestListView.class, gson);
//		attendanceView.convertIntoDTO(tuples);

		return dataList;

	}

	@Override
	public int updateStatus(String id, Integer status, String comment) {
		// TODO Auto-generated method stub
		return inventoryRequestRepository.updateStatus(id, status, comment);
	}

	@Override
	public InventoryRequest findById(String id) {
		// TODO Auto-generated method stub
		Optional<InventoryRequest> inventoryRequest = inventoryRequestRepository.findById(id);
		if (inventoryRequest.isPresent()) {
			return inventoryRequest.get();
		}
		return null;
	}

	@Override
	public int markOff(String id, String comment, int MarkOff, Date date) {
		// TODO Auto-generated method stub
		return inventoryRequestRepository.markOff(id, comment, MarkOff, date);
	}

	@Override
	public int cancel(String id, int cancel) {
		// TODO Auto-generated method stub
		return inventoryRequestRepository.cancel(id, cancel);
	}

	@Override
	public int returned(String id, int returned) {
		// TODO Auto-generated method stub
		return inventoryRequestRepository.returned(id, returned);
	}

	@Override
	public int updatePrice(String id, Double price, Long quantity, Double total_price, Double total_tax) {
		// TODO Auto-generated method stub
		return inventoryRequestRepository.updatePrice(id, price, quantity, total_price, total_tax);
	}

	@Override
	public List<InventoryRequestListView> findAllByVendorID(Long vendorID, PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, c.id as chemical_id, c.name as chemical_name, c.image as chemical_image, "
				+ "v.id as vendor_id, v.name as vendor_name, "
				+ "r.id as id, r.status as status, r.status_comment as status_comment, r.returned as returned, r.cancel as cancel, r.price as price, r.quantity as quantity, r.requireddate as requireddate, "
				+ "r.total_tax as total_tax, r.total_price as total_price, r.mark_off as mark_off, r.createtime, r.comment as comment "
				+ "from inventory_request r " + "left join chemical c on c.id = r.chemical_id "
				+ "left join vendor v on v.id = r.vendor_id ";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where c.name ilike '%" + pageRequestDTO.getKeyword().trim()
					+ "%' and r.vendor_id = " + vendorID;
		} else {
			baseBuery = baseBuery + " where r.vendor_id = " + vendorID;
		}

		baseBuery = baseBuery
				+ " group by c.id, c.name, c.image, v.id, v.name, r.id, r.status, r.status_comment, r.createtime, r.returned, r.cancel, r.mark_off, r.comment, r.price, r.quantity, r.requireddate, r.total_tax, r.total_price order by r.id DESC";
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

		List<InventoryRequestListView> dataList = AppUtils.parseTuple(tuples, InventoryRequestListView.class, gson);
//		attendanceView.convertIntoDTO(tuples);

		return dataList;

	}

	@Override
	public int updateInvoice(String id, Date invoiceDate, String invoiceURL, String invoiceNumber) {
		// TODO Auto-generated method stub
		return inventoryRequestRepository.updateInvoice(id, invoiceDate, invoiceURL, invoiceNumber);
	}
	

}
