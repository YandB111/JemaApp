/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 15-July-2023
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

import com.jema.app.entities.ElutionMachine;

@Repository
public interface ElutionMachineRepository extends CrudRepository<ElutionMachine, String> {

	@Transactional
	@Modifying
	@Query(value = "UPDATE ElutionMachine SET functional_type = :functional_type  WHERE id IN :id ")
	int updateFunctionalType(@Param("id") String id, @Param("functional_type") String functionalType);

	@Transactional
	@Modifying
	@Query(value = "UPDATE elution_machine SET deleted = '1' WHERE id IN ?1 ", nativeQuery = true)
	int delete(@Param("id") List<String> idsArrays);
}
