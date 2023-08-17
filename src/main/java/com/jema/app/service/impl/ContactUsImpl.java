package com.jema.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jema.app.entities.ContactUs;
import com.jema.app.repositories.ContactUsRepo;
import com.jema.app.service.ContactService;

@Service
public class ContactUsImpl implements ContactService {

	@Autowired
	private ContactUsRepo contactUsRepo;

	@Override
	public ContactUs saveTermCondition(String contactUs) {
		ContactUs contactUsref = new ContactUs();
		contactUsref.setContactContent(contactUs);
		return contactUsRepo.save(contactUsref);

	}

}
