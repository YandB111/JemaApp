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

import com.google.gson.Gson;
import com.jema.app.dto.CustomerListView;
import com.jema.app.dto.ElutionCustomerListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.ElutionCustomer;
import com.jema.app.repositories.ElutionCustomerRepository;
import com.jema.app.service.ElutionCustomerService;
import com.jema.app.utils.AppUtils;

@Service
public class ElutionCustomerServiceImpl implements ElutionCustomerService {

	@Autowired
	ElutionCustomerRepository mElutionCustomerRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;

	@Override
	public String save(ElutionCustomer mElutionCustomer) {
		// TODO Auto-generated method stub
		return mElutionCustomerRepository.save(mElutionCustomer).getId();
	}

	@Override
	public ElutionCustomer findById(String id) {
		// TODO Auto-generated method stub
		ElutionCustomer mElutionCustomer = mElutionCustomerRepository.findCustomerById(id);
		return mElutionCustomer;
	}

	@Override
	public int delete(List<String> ids) {
		// TODO Auto-generated method stub
		return mElutionCustomerRepository.delete(ids);
	}

	@Override
	public int updateStatus(String id, Integer status) {
		// TODO Auto-generated method stub
		return mElutionCustomerRepository.updateStatus(id, status);
	}

	@Override
	public List<ElutionCustomerListView> findAll(PageRequestDTO pageRequestDTO, Integer status) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, c.name as name, c.id as id, c.status as status, "
				+ "c.contact as contact, c.address as address, c.block as block, c.email as email from elution_customer c ";

		baseBuery = baseBuery + "  where c.deleted!=1 ";

		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " and c.name ilike '%" + pageRequestDTO.getKeyword().trim() + "%'";
		}

		if (status != null) {
			baseBuery = baseBuery + "and status=" + status;
		}

		baseBuery = baseBuery
				+ " group by c.name, c.id, c.status, c.contact, c.address, c.email, c.block order by c.id DESC";
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

		List<ElutionCustomerListView> dataList = AppUtils.parseTuple(tuples, ElutionCustomerListView.class, gson);
//		attendanceView.convertIntoDTO(tuples);

		return dataList;

	}

	@Override
	public int block(String id, Integer block) {
		// TODO Auto-generated method stub
		return mElutionCustomerRepository.block(id, block);
	}
}
