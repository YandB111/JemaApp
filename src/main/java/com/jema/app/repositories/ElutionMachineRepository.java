/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 15-July-2023
*
*/

package com.jema.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jema.app.entities.ElutionMachine;

@Repository
public interface ElutionMachineRepository extends CrudRepository<ElutionMachine, String> {

}
