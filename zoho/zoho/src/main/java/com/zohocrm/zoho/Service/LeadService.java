package com.zohocrm.zoho.Service;

import com.zohocrm.zoho.Entity.Lead;
import com.zohocrm.zoho.Payload.LeadDto;
import com.zohocrm.zoho.Payload.ResponsePage;

import java.util.List;

public interface LeadService {

    LeadDto createlead(LeadDto leadDto);

    void deleteRecord(String lid);

   ResponsePage getallLeads(int pageNo, int pageSize, String sortDir, String sortBy);

    List<Lead> getLeadsExcelReports();

   List<LeadDto> findAll();

    List<LeadDto> CsvfindAll();
}
