/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 01-May-2023
*
*/

package com.jema.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jema.app.entities.LeaveType;
import com.jema.app.repositories.LeaveTypeRepository;
import com.jema.app.service.LeaveTypeService;

@Service
public class LeaveServiceImpl implements LeaveTypeService {

	@Autowired
	LeaveTypeRepository leaveTypeRepository;

	@Override
	public Long save(LeaveType leaveType) {
		// TODO Auto-generated method stub
		return leaveTypeRepository.save(leaveType).getId();
	}

	@Override
	public List<LeaveType> findAll() {
		// TODO Auto-generated method stub
		Sort sortBy = Sort.by(new Sort.Order(Sort.Direction.DESC, "id").ignoreCase());
		return leaveTypeRepository.findAll(sortBy);
	}

	@Override
	public LeaveType findById(Long id) {
		// TODO Auto-generated method stub
		Optional<LeaveType> leaveType = leaveTypeRepository.findById(id);
		if (leaveType.isPresent()) {
			return leaveType.get();
		}
		return null;
	}

	@Override
	public int delete(List<Long> idsArrays) {
		// TODO Auto-generated method stub
		leaveTypeRepository.deleteAllById(idsArrays);
		return 1;
	}

	@Override
	public LeaveType findByName(String name) {
		// TODO Auto-generated method stub
		return leaveTypeRepository.findByName(name);
	}

}
