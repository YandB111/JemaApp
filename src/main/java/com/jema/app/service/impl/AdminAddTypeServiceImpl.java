package com.jema.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.glue.model.EntityNotFoundException;
import com.jema.app.entities.AdminAddType;
import com.jema.app.repositories.AdminAddTypeRepo;
import com.jema.app.service.AddAdminTypeService;

@Service
public class AdminAddTypeServiceImpl implements AddAdminTypeService {
	@Autowired
	private AdminAddTypeRepo addTypeRepository;

	@Override
	public AdminAddType addAddType(AdminAddType addType) {
		return addTypeRepository.save(addType);
	}

	@Override
	public AdminAddType updateAddType(Long id, AdminAddType updatedAddType) {
		if (!addTypeRepository.existsById(id)) {
			throw new EntityNotFoundException("AddType not found with ID: " + id);
		}
		updatedAddType.setId(id);
		return addTypeRepository.save(updatedAddType);
	}

	@Override
	public void deleteAddType(Long id) {
		if (!addTypeRepository.existsById(id)) {
			throw new EntityNotFoundException("AddType not found with ID: " + id);
		}
		addTypeRepository.deleteById(id);
	}

	@Override
	public List<AdminAddType> getAllAddTypes() {
		return (List<AdminAddType>) addTypeRepository.findAll();
	}
	
	
}
