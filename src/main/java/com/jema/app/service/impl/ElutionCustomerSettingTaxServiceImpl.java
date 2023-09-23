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
import com.jema.app.dto.ElutionCustomerSettingTaxListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.ElutionCustomerSettingTax;
import com.jema.app.repositories.ElutionCustomerSettingTaxRepository;
import com.jema.app.service.ElutionCustomerSettingTaxService;
import com.jema.app.utils.AppUtils;

@Service
public class ElutionCustomerSettingTaxServiceImpl implements ElutionCustomerSettingTaxService {

	@Autowired
	ElutionCustomerSettingTaxRepository mElutionCustomerSettingTaxRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;

	@Override
	public Long save(ElutionCustomerSettingTax mCustomerSettingTax) {
	    // Check if a tax with the same name (case-insensitive) already exists
	    String taxName = mCustomerSettingTax.getName();
	    ElutionCustomerSettingTax existingTax = mElutionCustomerSettingTaxRepository.findByNameIgnoreCase(taxName);
	    
	    if (existingTax != null) {
	        // Tax with a similar name (case-insensitive) already exists, throw a conflict exception
	        throw new ResponseStatusException(HttpStatus.CONFLICT, "Tax with a similar name already exists");
	    }
	    
	    // No conflict, save the new tax
	    return mElutionCustomerSettingTaxRepository.save(mCustomerSettingTax).getId();
	}

	@Override
	public List<ElutionCustomerSettingTaxListView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, i.name as name, i.id as id, "
				+ "i.description as description, i.applicable as applicable,  i.applicable_value as applicable_value, "
				+ "i.status as status, "
				+ "i.company_contribute as company_contribute, i.company_applicable as company_applicable, "
				+ "i.company_applicable_value as company_applicable_value " + "from elution_customer_setting_tax i ";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where i.name ilike '%" + pageRequestDTO.getKeyword().trim() + "%'";
		}

		baseBuery = baseBuery
				+ " group by i.name, i.id, i.description, i.applicable, i.applicable_value, i.status, i.company_contribute, i.company_applicable, i.company_applicable_value order by i.id DESC";
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

		List<ElutionCustomerSettingTaxListView> dataList = AppUtils.parseTuple(tuples, ElutionCustomerSettingTaxListView.class, gson);

		return dataList;

	}

	@Override
	public ElutionCustomerSettingTax findById(Long id) {
		// TODO Auto-generated method stub
		Optional<ElutionCustomerSettingTax> tax = mElutionCustomerSettingTaxRepository.findById(id);
		if (tax.isPresent()) {
			return tax.get();
		}
		return null;
	}

	@Override
	public int delete(List<Long> idsArrays) {
		// TODO Auto-generated method stub
		mElutionCustomerSettingTaxRepository.deleteAllById(idsArrays);
		return 1;
	}

	@Override
	public int updateStatus(int status, List<Long> idsArrays) {
		// TODO Auto-generated method stub
		mElutionCustomerSettingTaxRepository.updateStatus(status, idsArrays);
		return 1;
	}

}
