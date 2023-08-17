/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 08-May-2023
*
*/

package com.jema.app.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;
import com.jema.app.dto.LeaveManagementView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.LeaveManagement;
import com.jema.app.repositories.LeaveManagementRepository;
import com.jema.app.service.LeaveManagementService;
import com.jema.app.utils.AppUtils;

@Service
public class LeaveManagementServiceImpl implements LeaveManagementService {

	@Autowired
	LeaveManagementRepository leaveManagementRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;

	@Override
	public Long save(LeaveManagement leaveManagement) {
		if (leaveManagementRepository.existsByEmployeeIdAndDateAndLeaveType(leaveManagement.getEmployeeId(),
				leaveManagement.getDate(), leaveManagement.getLeaveType())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Leave type already added for this date");
		}

		LeaveManagement savedLeave = leaveManagementRepository.save(leaveManagement);
		return savedLeave.getId();
	}

	@Override
	public boolean isLeaveEntryExists(Long employeeId, Date date, int leaveType) {
		return leaveManagementRepository.existsByEmployeeIdAndDateAndLeaveType(employeeId, date, leaveType);
	}

	@Override
	public List<LeaveManagementView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub

		String baseBuery = "select count(*) over() as total, e.name as name, e.id as emp_id, "
				+ "e.employeeid as employee_id, e.designation designation, d.name department, "
				+ "lm.leave_type as leave_type, lm.leave_status as leave_status, lt.name as leave_type_name, lm.date as date, lm.id as id "
				+ "from leavemanagement lm " + "left join employee e on lm.employee_id = e.id "
				+ "left join leavetype lt on lm.leave_type = lt.id " + "left join department d on e.department = d.id";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where e.name ilike '%" + pageRequestDTO.getKeyword().trim() + "%'";
		}

		baseBuery = baseBuery
				+ " group by e.name, e.id, e.designation, e.employeeid, d.name, lm.leave_type, lm.leave_status, lt.name, lm.date, lm.id order by lm.id DESC";
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

		List<LeaveManagementView> dataList = AppUtils.parseTuple(tuples, LeaveManagementView.class, gson);
//		attendanceView.convertIntoDTO(tuples);

		return dataList;
	}

	@Override
	public List<LeaveManagement> findByEmployeeId(Long id) {
		// TODO Auto-generated method stub
		return leaveManagementRepository.findByEmployeeId(id);
	}

}
