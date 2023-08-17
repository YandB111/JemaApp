/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 15-Jun-2023
*
*/

package com.jema.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jema.app.entities.ElutionCustomer;

@Repository
public interface ElutionCustomerRepository extends CrudRepository<ElutionCustomer, String> {

	@Transactional
	@Modifying
	@Query(value = "UPDATE ElutionCustomer SET status = :status  WHERE id IN :id ")
	int updateStatus(@Param("id") String id, @Param("status") int status);

	@Transactional
	@Modifying
	@Query(value = "UPDATE elution_customer  SET deleted = '1'  WHERE id IN ?1 ", nativeQuery = true)
	int delete(@Param("id") List<String> idsArrays);
	
	@Query(value = "SELECT * FROM elution_customer WHERE id=?1 and deleted != '1'", nativeQuery = true)
	ElutionCustomer findCustomerById(@Param("id") String id);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE ElutionCustomer SET block = :block  WHERE id IN :id ")
	int block(@Param("id") String id, @Param("block") int block);
}
