/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 28-Mar-2023
*
*/

package com.jema.app.service;

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

}
