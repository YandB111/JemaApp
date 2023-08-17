/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 15-Jun-2023
*
*/

package com.jema.app.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

@Table(name = "customer")
@Entity
@Data
@Getter
@Setter
@JsonIgnoreProperties(value = { "createTime", "updateTime" }, allowGetters = true)
public class Customer {

	@Id
	@GeneratedValue(generator = "sequence_cus_id")
	@GenericGenerator(name = "sequence_cus_id", strategy = "com.jema.app.entities.CustomerIdGenerator")
	@Expose
	@Column(name = "id")
	private String id;
	
	@Column(name = "plant_name")
	String plantName;

	@Column(name = "plant_location")
	String plantLocation;

	@Column(name = "name",unique = true)
	String name;

	@Column(name = "contact")
	String contact;

	@Column(name = "alternate_contact")
	String alternateContact;

	@Column(name = "email", unique = true)
	String email;

	@Column(name = "address")
	String address;

	@Column(name = "city")
	String city;

	@Column(name = "state")
	String state;

	@Column(name = "zip")
	String zip;

	@Column(name = "country")
	String country;

	@Column(name = "status")
	int status;
	
	@Column(name = "block")
	int block;
	
	@Column(name = "deleted")
	int deleted;

	@Column(name = "type")
	Long type;
	
	@Column(name = "tax")
	Long tax;

	@Column(name = "accountnumber")
	String accountNumber;

	@Column(name = "bankname")
	String bankName;

	@Column(name = "branchname")
	String branchName;

	@Column(name = "createTime", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createTime;

	@Column(name = "updateTime", nullable = true, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updateTime;

}
