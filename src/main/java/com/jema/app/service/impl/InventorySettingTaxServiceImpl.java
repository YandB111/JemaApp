/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 24-May-2023
*
*/

package com.jema.app.service.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;
import com.jema.app.dto.DepartmentView;
import com.jema.app.dto.InventorySettingTaxListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.InventorySettingTax;
import com.jema.app.repositories.InventorySettingTaxRepository;
import com.jema.app.service.InventorySettingTaxService;
import com.jema.app.utils.AppUtils;

@Service
public class InventorySettingTaxServiceImpl implements InventorySettingTaxService {

	@Autowired
	InventorySettingTaxRepository inventorySettingTaxRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;
	
	@Override
	public Long save(InventorySettingTax inventorySettingTax) {
	    // Check if a tax with the same name already exists (ignoring case)
	    String taxName = inventorySettingTax.getName();
	    InventorySettingTax existingTax = inventorySettingTaxRepository.findByNameIgnoreCase(taxName);

	    if (existingTax != null) {
	        // Tax with the same name (ignoring case) already exists, throw a conflict exception
	        throw new ResponseStatusException(HttpStatus.CONFLICT, "Tax with the same name already exists");
	    }

	    // No conflict, save the new tax
	    return inventorySettingTaxRepository.save(inventorySettingTax).getId();
	}

	@Override
	public List<InventorySettingTaxListView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, i.name as name, i.id as id, "
				+ "i.description as description, i.applicable as applicable,  i.applicable_value as applicable_value, "
				+ "i.status as status, t.name as type_name,  i.type as type, "
				+ "i.company_contribute as company_contribute, i.company_applicable as company_applicable, "
				+ "i.company_applicable_value as company_applicable_value "
				+ "from inventory_setting_tax i "
				+ "left join type t on i.type = t.id ";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where i.name ilike '%" + pageRequestDTO.getKeyword().trim() + "%'";
		}

		baseBuery = baseBuery + " group by i.name, i.id, i.description, i.applicable, i.applicable_value, i.status, t.name, i.type, i.company_contribute, i.company_applicable, i.company_applicable_value order by i.id DESC";
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

		List<InventorySettingTaxListView> dataList = AppUtils.parseTuple(tuples, InventorySettingTaxListView.class, gson);

		return dataList;

	}

	@Override
	public InventorySettingTax findById(Long id) {
		// TODO Auto-generated method stub
		Optional<InventorySettingTax> tax = inventorySettingTaxRepository.findById(id);
		if (tax.isPresent()) {
			return tax.get();
		}
		return null;
	}

	@Override
	public int delete(List<Long> idsArrays) {
		// TODO Auto-generated method stub
		inventorySettingTaxRepository.deleteAllById(idsArrays);
		return 1;
	}

	@Override
	public int updateStatus(int status, List<Long> idsArrays) {
		// TODO Auto-generated method stub
		inventorySettingTaxRepository.updateStatus(status, idsArrays);
		return 1;
	}

}
