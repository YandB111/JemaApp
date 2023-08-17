package com.jema.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "AboutUsTable")
@Entity
@Data
@Getter
@Setter
@JsonIgnoreProperties(value = { "createTime", "updateTime" }, allowGetters = true)
public class AboutPolcies {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@Column(columnDefinition = "TEXT")
	private String aboutUsContent;

	@Column(columnDefinition = "TEXT")
	private String contactContent;

	@Column(columnDefinition = "TEXT")
	private String tContent;

	@Column(columnDefinition = "TEXT")
	private String policyContent;

}