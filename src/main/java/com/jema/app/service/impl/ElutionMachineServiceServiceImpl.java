/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 22-Jul-2023
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
import com.jema.app.dto.ElutionMachineServiceListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.ElutionMachineServiceEntity;
import com.jema.app.repositories.ElutionMachineRepository;
import com.jema.app.repositories.ElutionMachineServiceRepository;
import com.jema.app.service.ElutionMachineService;
import com.jema.app.service.ElutionMachineServiceService;
import com.jema.app.utils.AppUtils;

@Service
public class ElutionMachineServiceServiceImpl implements ElutionMachineServiceService {

	@Autowired
	ElutionMachineServiceRepository mElutionMachineServiceRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;


	@Autowired
	private ElutionMachineRepository elutionMachineRepository;
	
	@Autowired
	ElutionMachineService mElutionMachineServiceService;


	
	@Override
	public Long save(ElutionMachineServiceEntity mElutionMachineServiceEntity) {
		// TODO Auto-generated method stub
		return mElutionMachineServiceRepository.save(mElutionMachineServiceEntity).getId();
	}

	@Override
	public List<ElutionMachineServiceListView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, m.name as name, m.id as id, "
				+ "m.maintenance_days as maintenance_days, m.image as image, s.service_date as service_date "
				+ "from elution_machine m LEFT JOIN LATERAL ("

				+ " SELECT service_date FROM elution_machine_service WHERE machine_id = m.id "
				+ " ORDER BY service_date DESC LIMIT 1) s ON true ";

		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where m.deleted!=1 and m.name ilike '%" + pageRequestDTO.getKeyword().trim()
					+ "%'";
		} else {
			baseBuery = baseBuery + "  where m.deleted!=1 ";
		}

		baseBuery = baseBuery
				+ " group by  m.name, m.id, m.image, m.maintenance_days, s.service_date order by m.id DESC";
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


		List<ElutionMachineServiceListView> dataList = AppUtils.parseTuple(tuples, ElutionMachineServiceListView.class,
				gson);

//		attendanceView.convertIntoDTO(tuples);

		return dataList;

	}

}

