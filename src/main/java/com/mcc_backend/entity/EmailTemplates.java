package com.mcc_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "email_templates")
public class EmailTemplates {

    @Id
    private int id;
    @Column(columnDefinition="TEXT")
    private String template;
    private String subject;
    private String description;
}
