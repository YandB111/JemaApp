/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 08-Apr-2023
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

import com.jema.app.entities.Department;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {

	Page<Department> findByNameContainingIgnoreCase(String name, Pageable pageable);

	Page<Department> findAll(Pageable pageable);

	@Transactional
	@Modifying
	@Query(value = "UPDATE Department SET status = :status  WHERE id IN :id ")
	int updateDepartmentStatus(@Param("id") Long id, @Param("status") int status);

	@Query(value = "select count(*) from Department d where d.name ILIKE (?1%)", nativeQuery = true)
	Long getCount(@Param("name") String name);

	boolean existsByCodeIgnoreCaseAndIdNot(String code, Long id);

	boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

	
	boolean existsByCodeIgnoreCase(String code);

    boolean existsByNameIgnoreCase(String name);

}