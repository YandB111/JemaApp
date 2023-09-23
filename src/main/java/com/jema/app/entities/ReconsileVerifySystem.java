package com.jema.app.entities;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "ReconsileVerify")
@Entity
@Data
@Getter
@Setter
@JsonIgnoreProperties(value = { "createTime", "updateTime" }, allowGetters = true)
public class ReconsileVerifySystem {

	@Id
	@Column(name = "id")
	private String id; // Custom string ID

	@Column(name = "Records_id")
	private String recordsId; // Custom string ID

	@Column(name = "verified", columnDefinition = "VARCHAR(255) DEFAULT 'no'")
	private String verified;

	@Column(name = "total_price")
	private Double totalPrice;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "payment_Mode")
	private String paymentMode;

	@Column(name = "vendor_Name")
	private String vendorName;

	@Column(name = "create_date_local")
	private LocalDate ForLocalcreateDate;

	@Column(name = "Type")
	private String TypeOfPayment;
}
