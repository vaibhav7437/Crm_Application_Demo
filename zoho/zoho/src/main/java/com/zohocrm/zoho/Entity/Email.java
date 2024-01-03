package com.zohocrm.zoho.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="emails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    @Id
    private String eid;
    @Column(name = "`to`")
    private String to;
    private String subject;
    private String message;

}
