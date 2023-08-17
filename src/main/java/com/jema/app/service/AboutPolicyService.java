package com.jema.app.service;

import java.util.List;

import com.jema.app.dto.AboutUsDTO;
import com.jema.app.entities.AboutPolcies;
import com.jema.app.entities.AboutPoliciesUpdateRequest;

public interface AboutPolicyService {

	AboutPolcies saveAboutPolicy(AboutPolcies aboutPolicy);

	List<AboutPolcies> getAllAboutPolcies();

	void updateAboutUsContent(Long id, String aboutUsContent);

	void updateContactContent(Long id, String contactContent);

	void updateTContent(Long id, String tContent);

	void updatePolicyContent(Long id, String policyContent);

	AboutPolcies getAboutPolicyById(Long id);

	void updateContent(Long id, String aboutUsContent, String contactContent, String tContent, String policyContent);

	AboutPolcies updateContent(AboutPoliciesUpdateRequest request);


}
