package com.jema.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jema.app.entities.ReconsileVerifySystem;
import com.jema.app.repositories.ReconsileAndVerifySystemRepo;
import com.jema.app.service.ReconsileVerifyService;

@Service
public class ReconsileVerifySystemImpl implements ReconsileVerifyService {

	@Autowired
	ReconsileAndVerifySystemRepo reconsileAndVerifySystemRepo;

	@Override
	public List<ReconsileVerifySystem> getAllRecords() {
		return (List<ReconsileVerifySystem>) reconsileAndVerifySystemRepo.findAll();
	}

}
