package com.jema.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jema.app.entities.AuditAndReconsileVerify;
import com.jema.app.repositories.AuditAndReconsileVerifyRepository;
import com.jema.app.service.AuditAndReconsileVerifyService;

@Service
public class AuditAndReconsileVerifyServiceImpl implements AuditAndReconsileVerifyService {

	@Autowired
	AuditAndReconsileVerifyRepository auditAndReconsileVerifyRepository;

	
	 @Override
	    public List<AuditAndReconsileVerify> getAllRecords() {
	        return (List<AuditAndReconsileVerify>) auditAndReconsileVerifyRepository.findAll();
	    }
	 

}
