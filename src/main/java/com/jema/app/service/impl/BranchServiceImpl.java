/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 28-Mar-2023
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;
import com.jema.app.dto.BranchView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.Branch;
import com.jema.app.entities.Employee;
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
	public Branch updateBranch(Long id, String newName, String newEmail) {
	    Branch branchChange = branchRepository.findById(id)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));

	    boolean nameExists;
	    boolean emailExists;
	    int appendCounter = 1;

	    do {
	        // Check if the new name is different and not already taken
	        nameExists = newName != null && !newName.equalsIgnoreCase(branchChange.getName()) && branchRepository.existsByName(newName);

	        // Check if the new email is different and not already taken
	        emailExists = newEmail != null && !newEmail.equalsIgnoreCase(branchChange.getEmail()) && branchRepository.existsByEmail(newEmail);

	        if (nameExists) {
	            newName = newName.toLowerCase() + "_" + appendCounter;
	            appendCounter++;
	        }
	        if (emailExists) {
	            newEmail = newEmail.toLowerCase() + "_" + appendCounter;
	            appendCounter++;
	        }
	    } while (nameExists || emailExists);

	    // Update the email and name with unique values
	    if (newEmail != null) {
	        branchChange.setEmail(newEmail);
	    }
	    if (newName != null) {
	        branchChange.setName(newName);
	    }

	    return branchRepository.save(branchChange);
	}



	@Override
    public boolean isEmailOrNameExists(String email, String name) {
        return branchRepository.existsByEmailIgnoreCaseOrNameIgnoreCase(email, name);
    }

    @Override
    @Transactional
    public Long save(Branch branch) {
        if (isEmailOrNameExists(branch.getEmail(), branch.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Branch with the same email or name already exists");
        }
        return branchRepository.save(branch).getId();
    }

	


}
