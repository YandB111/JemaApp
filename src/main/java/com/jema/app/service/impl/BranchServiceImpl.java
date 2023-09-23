/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 28-Mar-2023
*
*/

package com.jema.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;
import com.jema.app.dto.BranchView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.Branch;
import com.jema.app.repositories.BranchRepository;
import com.jema.app.service.BranchService;
import com.jema.app.utils.AppUtils;

@Service
public class BranchServiceImpl implements BranchService {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;

	@Autowired
	BranchRepository branchRepository;

	@Override
	public Page<Branch> findAllByName(String name, Pageable pageable) {
		// TODO Auto-generated method stub
		return branchRepository.findByNameContainingIgnoreCase(name, pageable);
	}

	@Override
	public Page<Branch> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return branchRepository.findAll(pageable);
	}

	@Override
	public Branch findById(Long id) {
		// TODO Auto-generated method stub
		Optional<Branch> branch = branchRepository.findById(id);
		if (branch.isPresent()) {
			return branch.get();
		}
		return null;
	}

	@Override
	@Transactional
	public int deleteBranch(List<Long> idsArrays) {
		// TODO Auto-generated method stub
		branchRepository.deleteAllById(idsArrays);
		return 1;
	}

	@Override
	public int updateBranchStatus(Long id, Integer status) {
		// TODO Auto-generated method stub
		return branchRepository.updateBranchStatus(id, status);
	}

	@Override
	public Long getCount(String name) {
		// TODO Auto-generated method stub
		if (name == null || name.trim().isEmpty()) {
			return branchRepository.count();
		} else {
			return branchRepository.getCount(name);
		}

	}

	@Override
	public List<BranchView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, b.name as name, b.id as id, "
				+ "b.image as image, b.leader as leader, b.location as location, e.name as leadername, "
				+ "b.status as status, b.contact as contact, b.email as email, b.totalemployee as totalemployee "

				+ "from branch b " + "left join employee e on b.leader = e.id ";

		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where b.name ilike '%" + pageRequestDTO.getKeyword().trim() + "%'";
		}


		baseBuery = baseBuery
				+ " group by b.name, b.id, b.image, b.leader, b.location, e.name, b.status, b.contact, b.email, b.totalemployee order by b.id DESC";

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

		List<BranchView> dataList = AppUtils.parseTuple(tuples, BranchView.class, gson);
//		attendanceView.convertIntoDTO(tuples);

		return dataList;
	}


	@Override
	@Transactional
	public Branch updateBranch(Long id, String newName, String newEmail, String newImage, String newDescription,
			String newLocation, Long newLeader, String newContact, Long newDepartment, Long newTotalEmployee,
			Integer newStatus, Date newStartTime, Date newEndTime) {
		Branch branchChange = branchRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));

		// Check if the new email and new name already exist in the database
		boolean emailExistsInOtherBranches = branchRepository.existsByEmailIgnoreCaseAndIdNot(newEmail, id);
		boolean nameExistsInOtherBranches = branchRepository.existsByNameIgnoreCaseAndIdNot(newName, id);

		if (emailExistsInOtherBranches || nameExistsInOtherBranches) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"Branch with the same email or name already exists in other branches.");
			
		}

		// Update the fields only if the new name is different from the current name
		if (!newName.equalsIgnoreCase(branchChange.getName())) {
			branchChange.setName(newName);
		}

		// Update the fields only if the new email is different from the current email
		if (!newEmail.equalsIgnoreCase(branchChange.getEmail())) {
			branchChange.setEmail(newEmail);
		}

		// Update other fields
		branchChange.setImage(newImage);
		branchChange.setDescription(newDescription);
		branchChange.setLocation(newLocation);
		branchChange.setLeader(newLeader);
		branchChange.setContact(newContact);
		branchChange.setDepartment(newDepartment);
		branchChange.setTotalEmployee(newTotalEmployee);
		branchChange.setStatus(newStatus);
		branchChange.setStartTime(newStartTime);
		branchChange.setEndTime(newEndTime);

		return branchRepository.save(branchChange);
	}

	@Override
	public boolean isEmailOrNameExists(String email, String name) {
		return branchRepository.existsByEmailIgnoreCaseOrNameIgnoreCase(email, name);
	}

	@Override
	@Transactional
	public Long save(Branch branch) {
		String email = branch.getEmail();
		String name = branch.getName();

		boolean emailExists = branchRepository.existsByEmailIgnoreCase(email);
		boolean nameExists = branchRepository.existsByNameIgnoreCase(name);

		if (emailExists || nameExists) {
			String errorMessage = emailExists && nameExists ? "Branch with the same email and name already exists."
					: emailExists ? "Branch with the same email already exists."
							: "Branch with the same name already exists.";

			throw new ResponseStatusException(HttpStatus.CONFLICT, errorMessage);
		}

		return branchRepository.save(branch).getId();
	}

	@Override
	public boolean isEmailExistsInOtherBranches(String email, Long id) {
		// Check if the provided email exists in other branches except for the given
		// branch id
		return branchRepository.existsByEmailIgnoreCaseAndIdNot(email, id);
	}

	@Override
	public boolean isNameExistsInOtherBranches(String name, Long id) {
		// Check if the provided name exists in other branches except for the given
		// branch id
		return branchRepository.existsByNameIgnoreCaseAndIdNot(name, id);
	}


}
