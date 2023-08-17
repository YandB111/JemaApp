package com.jema.app.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.alexaforbusiness.model.NotFoundException;
import com.jema.app.entities.AboutPolcies;
import com.jema.app.entities.AboutPoliciesUpdateRequest;
import com.jema.app.repositories.AboutPoliciesRepo;
import com.jema.app.service.AboutPolicyService;

@Service
public class AboutUseImpl implements AboutPolicyService {

	@Autowired
	private AboutPoliciesRepo aboutPoliciesRepo;

	@Override
	public AboutPolcies saveAboutPolicy(AboutPolcies aboutPolicy) {
		return aboutPoliciesRepo.save(aboutPolicy);
	}

	@Override
	public List<AboutPolcies> getAllAboutPolcies() {
		return (List<AboutPolcies>) aboutPoliciesRepo.findAll();
	}

	@Override
	public AboutPolcies getAboutPolicyById(Long id) {
		return aboutPoliciesRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("AboutPolcies not found with id: " + id));
	}

	@Override
	@Transactional
	public void updateAboutUsContent(Long id, String aboutUsContent) {
		AboutPolcies aboutPolcies = aboutPoliciesRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("AboutPolcies not found with id: " + id));

		aboutPolcies.setAboutUsContent(aboutUsContent);
		aboutPoliciesRepo.save(aboutPolcies);
	}

	@Override
	@Transactional
	public void updateContactContent(Long id, String contactContent) {
		AboutPolcies aboutPolcies = aboutPoliciesRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("AboutPolcies not found with id: " + id));

		aboutPolcies.setContactContent(contactContent);
		aboutPoliciesRepo.save(aboutPolcies);
	}

	@Override
	@Transactional
	public void updateTContent(Long id, String tContent) {
		AboutPolcies aboutPolcies = aboutPoliciesRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("AboutPolcies not found with id: " + id));

		aboutPolcies.setTContent(tContent);
		aboutPoliciesRepo.save(aboutPolcies);
	}

	@Override
	@Transactional
	public void updatePolicyContent(Long id, String policyContent) {
		AboutPolcies aboutPolcies = aboutPoliciesRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("AboutPolcies not found with id: " + id));

		aboutPolcies.setPolicyContent(policyContent);
		aboutPoliciesRepo.save(aboutPolcies);
	}


    @Override
    @Transactional
    public void updateContent(Long id, String aboutUsContent, String contactContent, String tContent, String policyContent) {
        AboutPolcies aboutPolcies = aboutPoliciesRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("AboutPolcies not found with id: " + id));

        if (aboutUsContent != null) {
            aboutPolcies.setAboutUsContent(aboutUsContent);
        }
        if (contactContent != null) {
            aboutPolcies.setContactContent(contactContent);
        }
        if (tContent != null) {
            aboutPolcies.setTContent(tContent);
        }
        if (policyContent != null) {
            aboutPolcies.setPolicyContent(policyContent);
        }

        aboutPoliciesRepo.save(aboutPolcies);
    }
    
    @Override
    @Transactional
    public AboutPolcies updateContent(AboutPoliciesUpdateRequest request) {
        AboutPolcies aboutPolcies = aboutPoliciesRepo.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("AboutPolcies not found with id: " + request.getId()));

        if (request.getAboutUsContent() != null) {
            aboutPolcies.setAboutUsContent(request.getAboutUsContent());
        }
        if (request.getContactContent() != null) {
            aboutPolcies.setContactContent(request.getContactContent());
        }
        if (request.getTContent() != null) {
            aboutPolcies.setTContent(request.getTContent());
        }
        if (request.getPolicyContent() != null) {
            aboutPolcies.setPolicyContent(request.getPolicyContent());
        }

        AboutPolcies updatedPolcies = aboutPoliciesRepo.save(aboutPolcies);
        return updatedPolcies;
    }


}
