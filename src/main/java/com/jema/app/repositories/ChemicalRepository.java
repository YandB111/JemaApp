/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 20-May-2023
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

import com.jema.app.entities.Chemical;

@Repository
public interface ChemicalRepository extends CrudRepository<Chemical, Long> {

	@Transactional
	@Modifying
	@Query(value = "UPDATE Chemical SET status = :status  WHERE id IN :id ")
	int updateStatus(@Param("id") Long id, @Param("status") int status);

	@Transactional
	@Modifying
	@Query(value = "UPDATE Chemical  SET deleted = '1'  WHERE id IN ?1 ", nativeQuery = true)
	int delete(@Param("id") List<Long> idsArrays);

	@Query(value = "SELECT * FROM Chemical WHERE id=?1 and deleted != '1'", nativeQuery = true)
	Chemical findChemicalById(@Param("id") Long id);

	@Query("SELECT COUNT(c) > 0 FROM Chemical c WHERE c.name = :name")
    boolean existsByName(String name);
	
	@Query(value = "SELECT EXISTS(SELECT 1 FROM Chemical WHERE code = :code)", nativeQuery = true)
	boolean existsByCode(@Param("code") String code);
}
