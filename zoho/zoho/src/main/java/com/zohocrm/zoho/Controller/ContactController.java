package com.zohocrm.zoho.Controller;

import com.zohocrm.zoho.Payload.ContactDto;
import com.zohocrm.zoho.Service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
public class ContactController {


    private ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }


    // http://localhost:8080/api/contact?leadId=
    @PostMapping("/{leadId}")
    public ResponseEntity<?> CreateContact(@PathVariable String leadId){
        ContactDto dto = contactService.createContact(leadId);
        return  new ResponseEntity<>(dto , HttpStatus.CREATED);

    }


}
