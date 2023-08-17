/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 12-May-2023
*
*/

package com.jema.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jema.app.entities.Salary;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {

	@Transactional
	@Modifying
	@Query(value = "UPDATE Salary  SET status=?1  WHERE id IN ?2 ", nativeQuery = true)
	int updateStatus(@Param("status") int status, @Param("id") List<Long> idsArrays);
}
