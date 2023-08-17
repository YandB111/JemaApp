/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 21-May-2023
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
import com.jema.app.dto.EmployeeListView;
import com.jema.app.dto.InventorySettingQuantityLimitListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.InventorySettingQuantityLimit;
import com.jema.app.repositories.InventorySettingQuantityLimitRepository;
import com.jema.app.service.InventorySettingQuantityLimitService;
import com.jema.app.utils.AppUtils;

@Service
public class InventorySettingQuantityLimitServiceImpl implements InventorySettingQuantityLimitService {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;

	@Autowired
	InventorySettingQuantityLimitRepository inventorySettingQuantityLimitRepository;

	@Override
	public Long save(InventorySettingQuantityLimit inventorySetting) {
		// TODO Auto-generated method stub
		return inventorySettingQuantityLimitRepository.save(inventorySetting).getId();
	}

	@Override
	public InventorySettingQuantityLimit findById(Long id) {
		// TODO Auto-generated method stub
		Optional<InventorySettingQuantityLimit> inventorySetting = inventorySettingQuantityLimitRepository.findById(id);
		if (inventorySetting.isPresent()) {
			return inventorySetting.get();
		}
		return null;
	}

	@Override
	public InventorySettingQuantityLimit findByChemical(Long chemical) {
		// TODO Auto-generated method stub
		return inventorySettingQuantityLimitRepository.findByChemical(chemical);

	}

	@Override
	public int delete(List<Long> idsArrays) {
		// TODO Auto-generated method stub
		inventorySettingQuantityLimitRepository.deleteAllById(idsArrays);
		return 1;
	}

	@Override
	public List<InventorySettingQuantityLimitListView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, i.id as id, c.id as chemical_id, c.name as chemical_name, i.quantity as quantity " 
				+ "from inventory_setting_quantity_limit i "
				+ "left join chemical c on c.id = i.chemical";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where c.name ilike '%" + pageRequestDTO.getKeyword().trim() + "%'";
		}

		baseBuery = baseBuery
				+ " group by c.name, c.id, i.id, i.quantity order by i.id DESC";
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

		List<InventorySettingQuantityLimitListView> dataList = AppUtils.parseTuple(tuples, InventorySettingQuantityLimitListView.class, gson);
//				attendanceView.convertIntoDTO(tuples);

		return dataList;

	}

}
