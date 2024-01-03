package com.zohocrm.zoho.Service.impl;

import com.zohocrm.zoho.Entity.Contact;
import com.zohocrm.zoho.Entity.Email;
import com.zohocrm.zoho.Entity.Lead;
import com.zohocrm.zoho.Exception.ContactExists;
import com.zohocrm.zoho.Exception.LeadExists;
import com.zohocrm.zoho.Payload.EmailDto;
import com.zohocrm.zoho.Repository.ContactRepository;
import com.zohocrm.zoho.Repository.EmailRepository;
import com.zohocrm.zoho.Repository.leadRepository;
import com.zohocrm.zoho.Service.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailServiceimpl implements EmailService {



    private JavaMailSender javaMailSender;
    private ModelMapper modelMapper;

    private EmailRepository emailRepository;
    private leadRepository leadRepository;
    private ContactRepository contactRepository;



    public EmailServiceimpl(EmailRepository emailRepository,
                            leadRepository leadRepository,
                            ModelMapper modelMapper,
                            JavaMailSender javaMailSender,
                            ContactRepository contactRepository) {

        this.emailRepository = emailRepository;
        this.leadRepository = leadRepository;
        this.modelMapper = modelMapper;
        this.javaMailSender= javaMailSender;
        this.contactRepository= contactRepository;
    }

    @Override
    public EmailDto sendEmail(EmailDto emailDto) {
        Lead lead = leadRepository.findByEmail(emailDto.getTo()).orElseThrow(
                () -> new LeadExists("Email is not present in registered-" + emailDto.getTo())
        );


//
//        Contact contact = contactRepository.findByEmail(emailDto.getTo()).orElseThrow(
//                () -> new ContactExists("Email is not present in registered-" + emailDto.getTo())
//        );


        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDto.getTo());
        message.setSubject(emailDto.getSubject());
        message.setText(emailDto.getMessage());


        javaMailSender.send(message);
        Email email = maptoEntity(emailDto);
        String eid = UUID.randomUUID().toString();
        email.setEid(eid);
        Email sendEmail =  emailRepository.save(email);
        EmailDto emailDto1 = maptoEmailDto(sendEmail );
        return emailDto1;

    }
    public Email maptoEntity(EmailDto emailDto){
        return modelMapper.map(emailDto , Email.class);
    }
    public EmailDto maptoEmailDto(Email email){
        return modelMapper.map(email , EmailDto.class);
    }
}
