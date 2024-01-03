package com.zohocrm.zoho.Controller;

import com.zohocrm.zoho.Entity.Email;
import com.zohocrm.zoho.Payload.EmailDto;
import com.zohocrm.zoho.Service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }


    @PostMapping()
    public ResponseEntity<?> SendEmail(@RequestBody EmailDto emailDto){
        EmailDto emailDto1 = emailService.sendEmail(emailDto);
        return  new ResponseEntity<>(emailDto1 , HttpStatus.OK);
    }
}
