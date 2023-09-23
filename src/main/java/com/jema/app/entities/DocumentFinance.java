package com.jema.app.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "document_finance")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class DocumentFinance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "file_name")
    private String fileName;

    @Column(name = "upload_date")
    private LocalDateTime uploadDate;
    

}
