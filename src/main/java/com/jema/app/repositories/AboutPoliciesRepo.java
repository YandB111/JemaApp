package com.jema.app.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jema.app.entities.AboutPolcies;

@Repository
public interface AboutPoliciesRepo extends CrudRepository<AboutPolcies, Long> {
	@Modifying
	@Query("UPDATE AboutPolcies a SET a.aboutUsContent = :aboutUsContent WHERE a.id = :id")
	void updateAboutUsContent(@Param("id") Long id, @Param("aboutUsContent") String aboutUsContent);

	@Modifying
	@Query("UPDATE AboutPolcies a SET a.contactContent = :contactContent WHERE a.id = :id")
	void updateContactContent(@Param("id") Long id, @Param("contactContent") String contactContent);

	@Modifying
	@Query("UPDATE AboutPolcies a SET a.tContent = :tContent WHERE a.id = :id")
	void updateTContent(@Param("id") Long id, @Param("tContent") String tContent);
	
	@Modifying
    @Query("UPDATE AboutPolcies a SET a.policyContent = :policyContent WHERE a.id = :id")
    void updatePolicyContent(@Param("id") Long id, @Param("policyContent") String policyContent);
	

}
