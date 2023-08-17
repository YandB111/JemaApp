/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 26-May-2023
*
*/

package com.jema.app.service.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.jema.app.dto.ElutionCustomerSettingStatusListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.ElutionCustomerSettingStatus;
import com.jema.app.repositories.ElutionCustomerSettingStatusRepository;
import com.jema.app.service.ElutionCustomerSettingStatusService;
import com.jema.app.utils.AppUtils;

@Service
public class ElutionCustomerSettingStatusServiceImpl implements ElutionCustomerSettingStatusService {

	@Autowired
	ElutionCustomerSettingStatusRepository mElutionCustomerSettingStatusRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;

	@Override
	public Long save(ElutionCustomerSettingStatus mElutionCustomerSettingStatus) {
		// TODO Auto-generated method stub
		return mElutionCustomerSettingStatusRepository.save(mElutionCustomerSettingStatus).getId();
	}

	@Override
	public List<ElutionCustomerSettingStatusListView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, i.name as name, i.id as id, "
				+ "i.description as description, i.refer_to_department as refer_to_department,  i.department as department, "
				+ "i.status as status, d.name as department_name, i.can_add_document as can_add_document, i.can_add_comment as can_add_comment, i.notify_customer as notify_customer "
				+ "from elution_customer_setting_status i " + "left join department d on i.department = d.id ";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where i.name ilike '%" + pageRequestDTO.getKeyword().trim() + "%'";
		}

		baseBuery = baseBuery
				+ " group by i.name, i.id, i.description, i.refer_to_department, i.department, i.status, d.name, i.can_add_document, i.can_add_comment, i.notify_customer order by i.id DESC";
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

		List<ElutionCustomerSettingStatusListView> dataList = AppUtils.parseTuple(tuples, ElutionCustomerSettingStatusListView.class,
				gson);

		return dataList;

	}

	@Override
	public ElutionCustomerSettingStatus findById(Long id) {
		// TODO Auto-generated method stub
		Optional<ElutionCustomerSettingStatus> status = mElutionCustomerSettingStatusRepository.findById(id);
		if (status.isPresent()) {
			return status.get();
		}
		return null;
	}

	@Override
	public int delete(List<Long> idsArrays) {
		// TODO Auto-generated method stub
		mElutionCustomerSettingStatusRepository.deleteAllById(idsArrays);
		return 1;
	}

	@Override
	public int updateStatus(int status, List<Long> idsArrays) {
		// TODO Auto-generated method stub
		mElutionCustomerSettingStatusRepository.updateStatus(status, idsArrays);
		return 1;
	}

}
