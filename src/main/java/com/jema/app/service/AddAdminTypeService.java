package com.jema.app.service;

import java.util.List;

import com.jema.app.entities.AdminAddType;

public interface AddAdminTypeService {

	AdminAddType addAddType(AdminAddType addType);

	AdminAddType updateAddType(Long id, AdminAddType updatedAddType);

	void deleteAddType(Long id);

	List<AdminAddType> getAllAddTypes();
}
