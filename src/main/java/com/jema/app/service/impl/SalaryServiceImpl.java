/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 12-May-2023
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
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.SalariesView;
import com.jema.app.entities.Salary;
import com.jema.app.repositories.SalaryRepository;
import com.jema.app.service.SalaryService;
import com.jema.app.utils.AppUtils;

@Service
public class SalaryServiceImpl implements SalaryService {

	@Autowired
	SalaryRepository salaryRepository;

	@Autowired
	private EntityManager entityManager;
	@Autowired
	private AllowanceServiceImpl allowanceServiceImpl;

	@Autowired
	private Gson gson;

	@Override
	public Long save(List<Salary> salaries) {
		// TODO Auto-generated method stub
		salaryRepository.saveAll(salaries);
		return 1L;
	}

	@Override
	public List<SalariesView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, e.name as name, e.id as emp_id, e.employeeid as employee_id, "
				+ " s.gross as gross, s.deduction as deduction, s.date as date, s.id as id, s.status as status, "
				+ "string_agg(sdd.taxlabel, ',') as taxes " + "from salary s "
				+ "left join employee e on s.emp_id = e.id "
				+ "left join salarydetails sd on sd.id = e.salary_details_employee_id "
				+ "left join salarydeduction sdd on sdd.salary = sd.id";

		if (pageRequestDTO.getKeyword() != null || !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where e.name ilike '%" + pageRequestDTO.getKeyword().trim() + "%'";
		}

		baseBuery = baseBuery
				+ " group by e.name, e.id, e.employeeid, s.gross, s.deduction, s.date, s.status, s.id order by s.id DESC";
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

		List<SalariesView> dataList = AppUtils.parseTuple(tuples, SalariesView.class, gson);

		return dataList;

	}

	@Override
	public List<SalariesView> findByEmployeeId(Long id) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, e.name as name, e.id as emp_id, "
				+ "e.employeeid as employee_id, s.gross as gross, s.deduction as deduction, "
				+ "s.date as date, s.id as id, s.status as status, " + "string_agg(sdd.taxlabel, ',') as taxes "
				+ "from salary s " + "left join employee e on s.emp_id = e.id "
				+ "left join salarydetails sd on sd.id = e.salary_details_employee_id "
				+ "left join salarydeduction sdd on sdd.salary = sd.id " + "where s.emp_id = " + id;

		baseBuery = baseBuery
				+ " group by e.name, e.id, e.employeeid, s.gross, s.deduction, s.date, s.status, s.id order by s.id DESC";
		// create a query to retrieve MyEntity objects
		Query query = null;
		try {
			query = entityManager.createNativeQuery(baseBuery, Tuple.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// execute the query and obtain the list of entities for the requested page
		List<Tuple> tuples = query.getResultList();

		List<SalariesView> dataList = AppUtils.parseTuple(tuples, SalariesView.class, gson);

		return dataList;
	}

	@Override
	public int updateStatus(int status, List<Long> idsArrays) {
		// TODO Auto-generated method stub
		salaryRepository.updateStatus(status, idsArrays);
		return 1;
	}
}
