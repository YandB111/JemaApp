/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 22-Jul-2023
*
*/

package com.jema.app.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "elution_machine_service")
@Entity
@Data
@Getter
@Setter
public class ElutionMachineServiceEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "machine_id")
	String machineId;
	
	@Column(name = "service_date", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date serviceDate;


	// Add the maintaindate field
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "maintaindate")
    private Date maintaindate;

	

}
