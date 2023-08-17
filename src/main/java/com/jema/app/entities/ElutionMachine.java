/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 16-Jul-2023
*
*/

package com.jema.app.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "elution_machine")
@Entity
@Data
@Getter
@Setter
@JsonIgnoreProperties(value = { "createTime", "updateTime" }, allowGetters = true)
public class ElutionMachine {

	@Id
	@GeneratedValue(generator = "sequence_en_mc_id")
	@GenericGenerator(name = "sequence_en_mc_id", strategy = "com.jema.app.entities.ElutionMachineIdGenerator")
	@Expose
	@Column(name = "id")
	private String id;

	@Column(name = "name")
	String name;

	@Column(name = "raw")
	Long raw;

	@Column(name = "operator")
	Long operator;

	@Column(name = "maintenance_days")
	int maintenanceDays;

	@Column(name = "functional_type")
	String functionalType;

	@Column(name = "break_status")
	Double breakStatus;
	
	@Column(name = "slot")
	Double slot;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "machine")
	private List<MachineWorkingSlots> slots;
	
	@Column(name = "deleted")
	int deleted;

	@Column(name = "createTime", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createTime;

	@Column(name = "updateTime", nullable = true, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updateTime;
}
