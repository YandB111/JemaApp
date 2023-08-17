/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 20-May-2023
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

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "chemical")
@Entity
@Data
@Getter
@Setter
@JsonIgnoreProperties(value = { "createTime", "updateTime" }, allowGetters = true)
public class Chemical {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "image")
	String image;

	@Column(name = "name")
	String name;

	@Column(name = "code")
	String code;

	@Column(name = "description")
	String description;

	@Column(name = "price")
	Double price;

	@Column(name = "quantity")
	Long quantity;

	@Column(name = "MSSD")
	String MSSD;

	@Column(name = "hs_code")
	String hsCode;

	@Column(name = "expireDate")
	@Temporal(TemporalType.DATE)
	Date expireDate;

	@Column(name = "status")
	int status;
	
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
