package com.jema.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jema.app.entities.AdminAddType;
@Repository
public interface AdminAddTypeRepo extends CrudRepository<AdminAddType, Long> {
}