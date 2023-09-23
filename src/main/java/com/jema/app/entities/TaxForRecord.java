package com.jema.app.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Embeddable
@Data
@Entity
public class TaxForRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "taxName")
	private String name;

	@Column(name = "taxValue1")
	private double value1;

	@Column(name = "taxValue2")
	private double value2;
}
