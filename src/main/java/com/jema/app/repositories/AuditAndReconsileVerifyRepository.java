package com.jema.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jema.app.dto.AuditAndReconsileDTO;
import com.jema.app.entities.AuditAndReconsileVerify;

@Repository
public interface AuditAndReconsileVerifyRepository extends CrudRepository<AuditAndReconsileVerify, Long> {

	Optional<AuditAndReconsileVerify> findById(String id);

	List<AuditAndReconsileVerify> findByVerified(String verified);

}
