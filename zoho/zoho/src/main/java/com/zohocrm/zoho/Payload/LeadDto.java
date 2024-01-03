package com.zohocrm.zoho.Payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeadDto {



    private String lid;

    private String firstName;
    private String lastName;
    private String email;
    private long mobile;
    private String leadType;
    private String address;
    private String designation;
    private String company;
    private String note;





}
