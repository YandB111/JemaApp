/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 26-May-2023
*
*/

package com.jema.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "vendor_chemical")
@Entity
@Data
@Getter
@Setter
public class VendorChemical {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "chemical_id")
	private Long chemicalID;
}
