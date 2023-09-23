/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 16-Apr-2023
*
*/

package com.jema.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.PagingAndSortingRepository;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jema.app.entities.Employee;
import com.jema.app.entities.SalaryDetails;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

	Page<Employee> findByNameContainingIgnoreCase(String name, Pageable pageable);

	Page<Employee> findAll(Pageable pageable);


	@Transactional
	@Modifying
	@Query(value = "UPDATE Employee SET status = :status  WHERE id IN :id ")
	int updateEmployeeStatus(@Param("id") Long id, @Param("status") int status);

	@Query(value = "select count(*) from Employee e where e.name ILIKE (?1%)", nativeQuery = true)
	Long getCount(@Param("name") String name);

	@Transactional
	@Modifying
	@Query(value = "UPDATE SalaryDetails SET basicSalary = :basicSalary  WHERE id IN :id ")
	int updateEmployeeBasicSalary(@Param("id") Long id, @Param("basicSalary") Long basicSalary);


	@Query("SELECT e.salaryDetails.totalSalary FROM Employee e WHERE e.id = :employeeId")
	Double findTotalSalaryByEmployeeId(@Param("employeeId") Long employeeId);

	@Query("SELECT SUM(e.salaryDetails.totalSalary) FROM Employee e")
	Double calculateTotalSalarySum();

	boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

	boolean existsByContactIgnoreCaseAndIdNot(String contact, Long id);

	boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

	boolean existsByEmployeeIdIgnoreCaseAndIdNot(String employeeId, Long id);

	boolean existsByEmailAndIdNot(String email, Long id);

	boolean existsByNameAndIdNot(String name, Long id);

	boolean existsByEmployeeIdAndIdNot(String employeeId, Long id);

	boolean existsByContactAndIdNot(String contact, Long id);

	


}
