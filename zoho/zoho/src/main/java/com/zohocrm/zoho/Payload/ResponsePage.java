package com.zohocrm.zoho.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePage {

    private  List<LeadDto> content;
    private long pageNo;
    private long pageSize;
    private long totalpage;
    private long totalElement;
    private boolean lastpage;



}
