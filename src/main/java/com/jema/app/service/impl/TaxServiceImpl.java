/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 23-Apr-2023
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
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.TaxListView;
import com.jema.app.entities.Tax;
import com.jema.app.repositories.TaxRepository;
import com.jema.app.service.TaxService;
import com.jema.app.utils.AppUtils;

@Service
public class TaxServiceImpl implements TaxService {

	@Autowired
	TaxRepository taxRepository;
	
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;

	@Override
	public Long save(Tax tax) {
		// TODO Auto-generated method stub
		return taxRepository.save(tax).getId();
	}

	@Override
	public List<TaxListView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, i.name as name, i.id as id, "
				+ "i.description as description, i.applicable as applicable,  i.applicable_value as applicable_value, "
				+ "i.status as status, t.name as type_name,  i.type as type, "
				+ "i.company_contribute as company_contribute, i.company_applicable as company_applicable, "
				+ "i.company_applicable_value as company_applicable_value "
				+ "from tax i "
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

		List<TaxListView> dataList = AppUtils.parseTuple(tuples, TaxListView.class, gson);

		return dataList;

	}

	@Override
	public Tax findById(Long id) {
		// TODO Auto-generated method stub
		Optional<Tax> tax = taxRepository.findById(id);
		if (tax.isPresent()) {
			return tax.get();
		}
		return null;
	}

	@Override
	public int delete(List<Long> idsArrays) {
		// TODO Auto-generated method stub
		taxRepository.deleteAllById(idsArrays);
		return 1;
	}

	@Override
	public int updateStatus(int status, List<Long> idsArrays) {
		// TODO Auto-generated method stub
		taxRepository.updateStatus(status, idsArrays);
		return 1;
	}
}
