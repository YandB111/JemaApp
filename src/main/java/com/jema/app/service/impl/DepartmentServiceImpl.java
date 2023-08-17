/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 08-Apr-2023
*
*/

package com.jema.app.service.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;
import com.jema.app.dto.ChemicalDTO;
import com.jema.app.dto.DepartmentDTO;
import com.jema.app.dto.DepartmentView;
import com.jema.app.dto.PageRequestDTO;

import com.jema.app.entities.Department;
import com.jema.app.repositories.DepartmentRepository;
import com.jema.app.service.DepartmentService;
import com.jema.app.utils.AppUtils;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;

	@Autowired
	DepartmentRepository departmentRepository;

	@Override
	@Transactional
	public Long save(Department department) {
		String code = department.getCode();
		String name = department.getName();

		boolean codeExists = departmentRepository.existsByCodeIgnoreCase(code);
		boolean nameExists = departmentRepository.existsByNameIgnoreCase(name);

		if (codeExists) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Department with the same code already exists");
		}

		if (nameExists) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Department with the same name already exists");
		}

		Department savedDepartment = departmentRepository.save(department);
		return savedDepartment.getId();
	}

	@Override
	public Page<Department> findAllByName(String name, Pageable pageable) {
		// TODO Auto-generated method stub
		return departmentRepository.findByNameContainingIgnoreCase(name, pageable);
	}

	@Override
	public Page<Department> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return departmentRepository.findAll(pageable);
	}

	@Override
	public List<DepartmentView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, d.name as name, d.id as id, "
				+ "d.code as code, d.managedby as managedby,  e.name as managedbyname, "
				+ "d.status as status, d.branch as branch,  b.name as branchname " + "from department d "
				+ "left join branch b on d.branch = b.id " + "left join employee e on d.managedby = e.id ";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where d.name ilike '%" + pageRequestDTO.getKeyword().trim() + "%'";
		}

		baseBuery = baseBuery
				+ " group by d.name, d.id, d.code, d.managedby, e.name, d.status, d.branch, b.name order by d.id DESC";
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

		List<DepartmentView> dataList = AppUtils.parseTuple(tuples, DepartmentView.class, gson);
//		attendanceView.convertIntoDTO(tuples);

		return dataList;

	}

	@Override
	public Department findById(Long id) {
		// TODO Auto-generated method stub
		Optional<Department> branch = departmentRepository.findById(id);
		if (branch.isPresent()) {
			return branch.get();
		}
		return null;
	}

	@Override
	@Transactional
	public int deleteDepartment(List<Long> idsArrays) {
		// TODO Auto-generated method stub
		departmentRepository.deleteAllById(idsArrays);
		return 1;
	}

	@Override
	public int updateDepartmentStatus(Long id, Integer status) {
		// TODO Auto-generated method stub
		return departmentRepository.updateDepartmentStatus(id, status);
	}

	@Override
	public Long getCount(String name) {
		// TODO Auto-generated method stub
		if (name == null || name.trim().isEmpty()) {
			return departmentRepository.count();
		} else {
			return departmentRepository.getCount(name);
		}

	}

	@Override
	public ResponseEntity<?> updateDepartment(Long id, DepartmentDTO departmentDTO) {
		Department existingDep = departmentRepository.findById(id).orElse(null);

		if (existingDep != null) {
			// Proceed with the update logic
			updateDepartmenFields(existingDep, departmentDTO);

			// Save the modified chemical entity
			Department updatedDepartment = departmentRepository.save(existingDep);

			// Return the updated chemical
			return ResponseEntity.ok(updatedDepartment);
		} else {
			// Handle the situation where the chemical with the given id doesn't exist
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chemical not found with ID: " + id);
		}
	}

	@Override
	public void updateDepartmenFields(Department department, DepartmentDTO departmentDTO) {
		int appendCounter = 1;

		// Check if the new code already exists
		boolean newCodeExists = departmentRepository.existsByCodeIgnoreCaseAndIdNot(departmentDTO.getCode(),
				department.getId());

		while (newCodeExists) {
			// Append "_<appendCounter>" to the new code
			departmentDTO.setCode(departmentDTO.getCode() + "_" + appendCounter);
			appendCounter++;

			newCodeExists = departmentRepository.existsByCodeIgnoreCaseAndIdNot(departmentDTO.getCode(),
					department.getId());
		}

		// Check if the new name already exists
		boolean newNameExists = departmentRepository.existsByNameIgnoreCaseAndIdNot(departmentDTO.getName(),
				department.getId());

		while (newNameExists) {
			// Append "_<appendCounter>" to the new name
			departmentDTO.setName(departmentDTO.getName() + "_" + appendCounter);
			appendCounter++;

			newNameExists = departmentRepository.existsByNameIgnoreCaseAndIdNot(departmentDTO.getName(),
					department.getId());
		}

		// Update other fields from chemicalDTO as needed
		department.setCode(departmentDTO.getCode());
		department.setName(departmentDTO.getName());
		department.setBranch(departmentDTO.getBranch());
		department.setManagedBy(departmentDTO.getManagedBy());
		department.setStatus(departmentDTO.getStatus());
		// ... update other fields as needed
	}

}
