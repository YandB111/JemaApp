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
import com.jema.app.dto.ElutionMachineRawListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.ElutionMachineRaw;
import com.jema.app.repositories.ElutionMachineRawRepository;
import com.jema.app.service.ElutionMachineRawService;
import com.jema.app.utils.AppUtils;

@Service
public class ElutionMachineRawServiceImpl implements ElutionMachineRawService {

	@Autowired
	ElutionMachineRawRepository mElutionMachineRawRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;

	@Override
	public Long save(ElutionMachineRaw mElutionMachineRaw) {
		// TODO Auto-generated method stub
		return mElutionMachineRawRepository.save(mElutionMachineRaw).getId();
	}

	@Override
	public List<ElutionMachineRawListView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, i.name as name, i.id as id, "
				+ "i.description as description, i.status as status " 
				+ " from elution_machine_raw i ";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where i.name ilike '%" + pageRequestDTO.getKeyword().trim() + "%'";
		}

		baseBuery = baseBuery
				+ " group by i.name, i.id, i.description, i.status order by i.id DESC";
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

		List<ElutionMachineRawListView> dataList = AppUtils.parseTuple(tuples, ElutionMachineRawListView.class, gson);

		return dataList;

	}

	@Override
	public ElutionMachineRaw findById(Long id) {
		// TODO Auto-generated method stub
		Optional<ElutionMachineRaw> status = mElutionMachineRawRepository.findById(id);
		if (status.isPresent()) {
			return status.get();
		}
		return null;
	}

	@Override
	public int delete(List<Long> idsArrays) {
		// TODO Auto-generated method stub
		mElutionMachineRawRepository.deleteAllById(idsArrays);
		return 1;
	}

	@Override
	public int updateStatus(int status, List<Long> idsArrays) {
		// TODO Auto-generated method stub
		mElutionMachineRawRepository.updateStatus(status, idsArrays);
		return 1;
	}

}
