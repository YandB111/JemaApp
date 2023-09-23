/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 08-Apr-2023
*
*/

package com.jema.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jema.app.dto.DepartmentView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.Department;

public interface DepartmentService {

	public Long save(Department department);

	public Page<Department> findAllByName(String name, Pageable pageable);

	public Page<Department> findAll(Pageable pageable);


	public List<DepartmentView> findAll(PageRequestDTO pageRequestDTO);

	public Department findById(Long id);

	public int deleteDepartment(List<Long> idsArrays);

	public int updateDepartmentStatus(Long id, Integer status);

	public Long getCount(String name);

	Long updateDepartment(Department department);

	public boolean isNameOrCodeExists(String name, String code, Long id);


}
