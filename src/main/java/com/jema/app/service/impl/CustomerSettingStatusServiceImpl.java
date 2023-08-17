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
import com.jema.app.dto.CustomerSettingStatusListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.CustomerSettingStatus;
import com.jema.app.repositories.CustomerSettingStatusRepository;
import com.jema.app.service.CustomerSettingStatusService;
import com.jema.app.utils.AppUtils;

@Service
public class CustomerSettingStatusServiceImpl implements CustomerSettingStatusService {

	@Autowired
	CustomerSettingStatusRepository customerSettingStatusRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;

	@Override
	public Long save(CustomerSettingStatus customerSettingStatus) {
		// TODO Auto-generated method stub
		return customerSettingStatusRepository.save(customerSettingStatus).getId();
	}

	@Override
	public List<CustomerSettingStatusListView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, i.name as name, i.id as id, "
				+ "i.description as description, i.refer_to_department as refer_to_department,  i.department as department, "
				+ "i.status as status, d.name as department_name, i.can_add_department as can_add_department, i.can_add_comment as can_add_comment "
				+ "from customer_setting_status i " + "left join department d on i.department = d.id ";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where i.name ilike '%" + pageRequestDTO.getKeyword().trim() + "%'";
		}

		baseBuery = baseBuery
				+ " group by i.name, i.id, i.description, i.refer_to_department, i.department, i.status, d.name, i.can_add_department, i.can_add_comment order by i.id DESC";
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

		List<CustomerSettingStatusListView> dataList = AppUtils.parseTuple(tuples, CustomerSettingStatusListView.class,
				gson);

		return dataList;

	}

	@Override
	public CustomerSettingStatus findById(Long id) {
		// TODO Auto-generated method stub
		Optional<CustomerSettingStatus> status = customerSettingStatusRepository.findById(id);
		if (status.isPresent()) {
			return status.get();
		}
		return null;
	}

	@Override
	public int delete(List<Long> idsArrays) {
		// TODO Auto-generated method stub
		customerSettingStatusRepository.deleteAllById(idsArrays);
		return 1;
	}

	@Override
	public int updateStatus(int status, List<Long> idsArrays) {
		// TODO Auto-generated method stub
		customerSettingStatusRepository.updateStatus(status, idsArrays);
		return 1;
	}

}
