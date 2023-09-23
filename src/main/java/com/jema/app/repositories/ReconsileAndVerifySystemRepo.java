package com.jema.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jema.app.entities.ReconsileVerifySystem;

@Repository
public interface ReconsileAndVerifySystemRepo extends CrudRepository<ReconsileVerifySystem, Long> {
	Optional<ReconsileVerifySystem> findById(String id);

	List<ReconsileVerifySystem> findByVerified(String verified);
}
