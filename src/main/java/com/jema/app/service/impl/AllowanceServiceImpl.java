/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 28-Apr-2023
*
*/

package com.jema.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jema.app.entities.Allowance;
import com.jema.app.repositories.AllowanceRepository;
import com.jema.app.service.AllowanceService;

@Service
public class AllowanceServiceImpl implements AllowanceService{

	@Autowired
	AllowanceRepository allowanceRepository;
	
	@Override
	public Long save(Allowance allowance) {
		// TODO Auto-generated method stub
		return allowanceRepository.save(allowance).getId();
	}

	@Override
	public List<Allowance> findAll() {
		// TODO Auto-generated method stub
		Sort sortBy = Sort.by(new Sort.Order(Sort.Direction.DESC, "id").ignoreCase());
		return allowanceRepository.findAll(sortBy);
	}

	@Override
	public Allowance findById(Long id) {
		// TODO Auto-generated method stub
		Optional<Allowance> allowance = allowanceRepository.findById(id);
		if (allowance.isPresent()) {
			return allowance.get();
		}
		return null;
	}

	@Override
	public int delete(List<Long> idsArrays) {
		// TODO Auto-generated method stub
		allowanceRepository.deleteAllById(idsArrays);
		return 1;
	}

	@Override
	public int updateStatus(int status, List<Long> idsArrays) {
		// TODO Auto-generated method stub
		allowanceRepository.updateStatus(status, idsArrays);
		return 1;
	}

}
