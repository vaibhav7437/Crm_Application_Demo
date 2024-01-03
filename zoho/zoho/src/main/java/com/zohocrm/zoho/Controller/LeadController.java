package com.zohocrm.zoho.Controller;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.zohocrm.zoho.Entity.Lead;
import com.zohocrm.zoho.Payload.EmailDto;
import com.zohocrm.zoho.Payload.LeadDto;
import com.zohocrm.zoho.Payload.ResponsePage;
import com.zohocrm.zoho.Service.LeadService;
import com.zohocrm.zoho.Service.impl.DatabasePDFService;
import com.zohocrm.zoho.Service.impl.ExcelHelper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/zoho")
public class LeadController {

    private LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping
    public ResponseEntity<?> createLead(@RequestBody LeadDto leadDto){
        LeadDto leadDto1 = leadService.createlead(leadDto);
        return new ResponseEntity<>( leadDto1, HttpStatus.CREATED);
    }

    @DeleteMapping("/{lid}")
    public ResponseEntity<?>  deleteRecord(@PathVariable("lid") String lid){
        leadService.deleteRecord(lid);
         return new ResponseEntity<>("Lead is deleted" , HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllLeads(
            @RequestParam(value = "pageNo" , defaultValue = "0" ,required = false  )int pageNo,
            @RequestParam(value = "pageSize" , defaultValue = "3" ,required = false  )int pageSize,
            @RequestParam(value = "sortDir" , defaultValue = "Asc" ,required = false  )String sortDir,
            @RequestParam(value = "sortBy" , defaultValue = "firstName" ,required = false  )String sortBy
    ){
       ResponsePage responsePage = leadService.getallLeads(pageNo,pageSize,sortDir,sortBy);
        return new ResponseEntity<>(responsePage ,HttpStatus.OK);
    }

    // http://localhost:8080/api/zoho/excelReport

    @GetMapping("/excelReport")
    public ResponseEntity <Resource> getLeadsExcelReports(){
        List<Lead> leads  = leadService.getLeadsExcelReports();
        ByteArrayInputStream leadsReports =  ExcelHelper.LeadsToExcel(leads);

        String filename = "leads.xlsx";
        InputStreamResource file = new InputStreamResource(leadsReports);

        return  ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body( file);

    }


 //    http://localhost:8080/api/zoho/openpdf/leads
    @GetMapping(value = "/openpdf/leads", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> employeeReport()  throws IOException {
        List<LeadDto> employees =  leadService.findAll();

        ByteArrayInputStream bis = DatabasePDFService.employeePDFReport(employees);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=leads.pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }


 //   http://localhost:8080/api/zoho/csvexport

    @GetMapping("/csvexport")
    public void exportCSV(HttpServletResponse response)
            throws Exception {

        //set file name and content type

        String filename = "Leads-data.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");
        //create a csv writer
        StatefulBeanToCsv<LeadDto>writer = new StatefulBeanToCsvBuilder<LeadDto>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).withSeparator(CSVWriter.DEFAULT_SEPARATOR).withOrderedResults(false)
                .build();
        //write all employees data to csv file
        writer.write(leadService.CsvfindAll());

    }
}







