/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 20-May-2023
*
*/

package com.jema.app.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.cognitoidp.model.AliasExistsException;
import com.google.gson.Gson;
import com.jema.app.dto.ChemicalListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.Chemical;
import com.jema.app.entities.Department;
import com.jema.app.repositories.ChemicalRepository;
import com.jema.app.service.ChemicalService;
import com.jema.app.utils.AppUtils;

@Service
public class ChemicalSeviceImpl implements ChemicalService {

	@Autowired
	ChemicalRepository chemicalRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;


	// Save method with the check for existing chemical name
	@Override
	public Long save(Chemical chemical) {
	    String name = chemical.getName();
	    String code = chemical.getCode();

	    // Check name or code already exists
	    if (chemicalRepository.existsByCode(code) && chemicalRepository.existsByName(name)) {
            throw new IllegalArgumentException("Chemical by or by name already exists.");
        } else if (chemicalRepository.existsByCode(code)) {
            throw new IllegalArgumentException("Chemical exists by code.");
        } else if (chemicalRepository.existsByName(name)) {
            throw new IllegalArgumentException("Chemical with the same name already exists.");
        }


	    Chemical chemicalSave = chemicalRepository.save(chemical);
	    return chemicalSave.getId();
	}


	@Override
	public List<ChemicalListView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, c.name as name, c.id as id, c.status as status, "
				+ "c.description as description, c.code as code, c.quantity as quantity, c.price as price, "
				+ "c.mssd as mssd, c.image as image, c.hs_code as hs_code, c.expiredate as expiredate "
				+ "from chemical c ";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where c.deleted!=1 and c.name ilike '%" + pageRequestDTO.getKeyword().trim()
					+ "%'";
		} else {
			baseBuery = baseBuery + "  where c.deleted!=1 ";
		}

		baseBuery = baseBuery
				+ " group by c.name, c.id, c.status, c.description, c.code, c.quantity, c.price, c.mssd, c.image, c.hs_code, c.expiredate order by c.id DESC";
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

		List<ChemicalListView> dataList = AppUtils.parseTuple(tuples, ChemicalListView.class, gson);
//		attendanceView.convertIntoDTO(tuples);

		return dataList;

	}

	@Override
	public Chemical findById(Long id) {
		// TODO Auto-generated method stub
		Chemical chemical = chemicalRepository.findChemicalById(id);
		return chemical;
	}

	@Override
	public int delete(List<Long> ids) {
		// TODO Auto-generated method stub
		return chemicalRepository.delete(ids);
	}

	@Override
	public int updateStatus(Long id, Integer status) {
		// TODO Auto-generated method stub
		return chemicalRepository.updateStatus(id, status);
	}

}
