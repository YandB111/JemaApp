/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 23-Apr-2023
*
*/

package com.jema.app.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "tax")
@Entity
@Data
@Getter
@Setter
@JsonIgnoreProperties(value = { "createTime", "updateTime" }, allowGetters = true)
public class Tax {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	String name;
	
	@Column(name = "description")
	String description;

	@Column(name = "applicable")
	String applicable;

	@Column(name = "applicable_value")
	Double applicableValue;

	@JoinColumn(name = "type")
	Long type;

	@Column(name = "company_contribute")
	int companyContribute;
	
	@Column(name = "status")
	int status;

	@Column(name = "company_applicable")
	String companyApplicable;

	@Column(name = "company_applicable_value")
	Double companyApplicableValue;

	@Column(name = "createTime", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createTime;

	@Column(name = "updateTime", nullable = true, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updateTime;
}
