/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 16-Jul-2023
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
import com.jema.app.dto.ElutionMachineListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.ElutionMachine;
import com.jema.app.repositories.ElutionMachineRepository;
import com.jema.app.service.ElutionMachineService;
import com.jema.app.utils.AppUtils;

@Service
public class ElutionMachineServiceImpl implements ElutionMachineService {

	@Autowired
	ElutionMachineRepository mElutionMachineRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;
	
	@Override
	public String save(ElutionMachine elutionMachine) {
		// TODO Auto-generated method stub
		return mElutionMachineRepository.save(elutionMachine).getId();
	}

	@Override
	public List<ElutionMachineListView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, m.name as name, m.id as id, m.slot as slot, "
				+ "m.break_status as break_status, m.maintenance_days as maintenance_days, r.name as raw "
				+ "from elution_machine m "
				+ "left join elution_machine_raw r on r.id = m.raw ";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where m.deleted!=1 and m.name ilike '%" + pageRequestDTO.getKeyword().trim()
					+ "%'";
		} else {
			baseBuery = baseBuery + "  where m.deleted!=1 ";
		}

		baseBuery = baseBuery
				+ " group by  m.name, m.id, m.slot, m.break_status, m.maintenance_days, r.name order by m.id DESC";
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

		List<ElutionMachineListView> dataList = AppUtils.parseTuple(tuples, ElutionMachineListView.class, gson);
//		attendanceView.convertIntoDTO(tuples);

		return dataList;
	}

}
