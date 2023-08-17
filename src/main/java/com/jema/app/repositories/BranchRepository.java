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
		
	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Branch e WHERE e.name = :name")
	boolean existsName(String name);

	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Branch e WHERE e.email = :email")
	boolean existsEmail(String email);
}
