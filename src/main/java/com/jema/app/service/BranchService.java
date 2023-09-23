/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 28-Mar-2023
*
*/

package com.jema.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jema.app.dto.BranchView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.Branch;

public interface BranchService {

	public Long save(Branch branch);

	public Page<Branch> findAllByName(String name, Pageable pageable);

	public Page<Branch> findAll(Pageable pageable);


	public List<BranchView> findAll(PageRequestDTO pageRequestDTO);

	public Branch findById(Long id);

	public int deleteBranch(List<Long> idsArrays);

	public int updateBranchStatus(Long id, Integer status);

	public Long getCount(String name);

	

	boolean isEmailOrNameExists(String email, String name);

	Branch updateBranch(Long id, String newName, String newEmail, String newImage, String newDescription,
			String newLocation, Long newLeader, String newContact, Long newDepartment, Long newTotalEmployee,
			Integer newStatus, Date newStartTime, Date newEndTime);

	public boolean isEmailExistsInOtherBranches(String email, Long id);

	public boolean isNameExistsInOtherBranches(String name, Long id);


}
