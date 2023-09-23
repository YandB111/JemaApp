/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 28-Mar-2023
*
*/

package com.jema.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jema.app.entities.Branch;

@Repository
public interface BranchRepository extends CrudRepository<Branch, Long> {

	Page<Branch> findByNameContainingIgnoreCase(String name, Pageable pageable);

	Page<Branch> findAll(Pageable pageable);

	@Transactional
	@Modifying
	@Query(value = "UPDATE Branch SET status = :status  WHERE id IN :id ")
	int updateBranchStatus(@Param("id") Long id, @Param("status") int status);

	@Query(value = "select count(*) from Branch b where b.name ILIKE (?1%)", nativeQuery = true)
	Long getCount(@Param("name") String name);


	boolean existsByName(String name);

	boolean existsByEmail(String email);

	boolean existsByEmailIgnoreCaseOrNameIgnoreCase(String email, String name);

	boolean existsByEmailIgnoreCaseAndIdNot(String newEmail, Long id);

	boolean existsByNameIgnoreCaseAndIdNot(String newName, Long id);

	boolean existsByNameIgnoreCase(String name);

	boolean existsByEmailIgnoreCase(String email);


}
