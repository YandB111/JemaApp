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
import com.jema.app.dto.InventorySettingStatusListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.InventorySettingStatus;
import com.jema.app.repositories.InventorySettingStatusRepository;
import com.jema.app.service.InventorySettingStatusService;
import com.jema.app.utils.AppUtils;

@Service
public class InventorySettingStatusServiceImpl implements InventorySettingStatusService {

	@Autowired
	InventorySettingStatusRepository inventorySettingStatusRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;
	
	@Override
	public Long save(InventorySettingStatus inventorySettingStatus) {
		// TODO Auto-generated method stub
		return inventorySettingStatusRepository.save(inventorySettingStatus).getId();
	}

	@Override
	public List<InventorySettingStatusListView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, i.name as name, i.id as id, "
				+ "i.description as description, i.refer_to_department as refer_to_department,  i.department as department, "
				+ "i.status as status, d.name as department_name, i.can_add_department as can_add_department, i.can_add_comment as can_add_comment "
				+ "from inventory_setting_status i "
				+ "left join department d on i.department = d.id ";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where i.name ilike '%" + pageRequestDTO.getKeyword().trim() + "%'";
		}

		baseBuery = baseBuery + " group by i.name, i.id, i.description, i.refer_to_department, i.department, i.status, d.name, i.can_add_department, i.can_add_comment order by i.id DESC";
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

		List<InventorySettingStatusListView> dataList = AppUtils.parseTuple(tuples, InventorySettingStatusListView.class, gson);

		return dataList;

	}

	@Override
	public InventorySettingStatus findById(Long id) {
		// TODO Auto-generated method stub
		Optional<InventorySettingStatus> status = inventorySettingStatusRepository.findById(id);
		if (status.isPresent()) {
			return status.get();
		}
		return null;
	}

	@Override
	public int delete(List<Long> idsArrays) {
		// TODO Auto-generated method stub
		inventorySettingStatusRepository.deleteAllById(idsArrays);
		return 1;
	}

	@Override
	public int updateStatus(int status, List<Long> idsArrays) {
		// TODO Auto-generated method stub
		inventorySettingStatusRepository.updateStatus(status, idsArrays);
		return 1;
	}

}
