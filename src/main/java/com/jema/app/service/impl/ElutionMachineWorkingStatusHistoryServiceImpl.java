/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 23-Jul-2023
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
import com.jema.app.dto.ElutionMachineWorkingStatusHistoryListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.ElutionMachineWorkingStatusHistory;
import com.jema.app.repositories.ElutionMachineWorkingStatusHistoryRepository;
import com.jema.app.service.ElutionMachineWorkingStatusHistoryService;
import com.jema.app.utils.AppUtils;

@Service
public class ElutionMachineWorkingStatusHistoryServiceImpl implements ElutionMachineWorkingStatusHistoryService {

	@Autowired
	ElutionMachineWorkingStatusHistoryRepository mElutionMachineWorkingStatusHistoryRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;

	@Override
	public Long save(ElutionMachineWorkingStatusHistory mElutionMachineWorkingStatusHistory) {
		// TODO Auto-generated method stub
		return mElutionMachineWorkingStatusHistoryRepository.save(mElutionMachineWorkingStatusHistory).getId();
	}

	@Override
	public List<ElutionMachineWorkingStatusHistoryListView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, e.id as id, m.name as name, m.id as machine_id, "
				+ "m.image as image,e.createtime as createtime, e.functional_type as functional_type " 
				+ " from elution_machine_working_status_history e "
				+ "left join elution_machine m on m.id = e.machine_id ";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where m.deleted!=1 and m.name ilike '%" + pageRequestDTO.getKeyword().trim()
					+ "%'";
		} else {
			baseBuery = baseBuery + "  where m.deleted!=1 ";
		}

		baseBuery = baseBuery
				+ " group by  m.name, m.id, m.image,e.createtime, e.id, e.functional_type order by e.id DESC";
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

		List<ElutionMachineWorkingStatusHistoryListView> dataList = AppUtils.parseTuple(tuples,
				ElutionMachineWorkingStatusHistoryListView.class, gson);

		return dataList;

	}

}
