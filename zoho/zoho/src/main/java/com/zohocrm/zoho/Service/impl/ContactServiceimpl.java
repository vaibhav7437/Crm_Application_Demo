package com.zohocrm.zoho.Service.impl;

import com.zohocrm.zoho.Entity.Contact;
import com.zohocrm.zoho.Entity.Lead;
import com.zohocrm.zoho.Exception.LeadExists;
import com.zohocrm.zoho.Payload.ContactDto;
import com.zohocrm.zoho.Payload.LeadDto;
import com.zohocrm.zoho.Repository.ContactRepository;
import com.zohocrm.zoho.Service.ContactService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zohocrm.zoho.Repository.leadRepository;

import java.util.UUID;

@Service
public class ContactServiceimpl implements ContactService {


    private ModelMapper modelMapper;
    private leadRepository leadRepository;
    private ContactRepository contactRepository;


    public ContactServiceimpl(leadRepository leadRepository,
                              ContactRepository contactRepository
    ,ModelMapper modelMapper) {
        this.leadRepository = leadRepository;
        this.contactRepository= contactRepository;
        this.modelMapper= modelMapper;
    }

    @Override
    public ContactDto createContact(String  leadId) {

       Lead lead = leadRepository.findById(leadId).orElseThrow(
                ()->new LeadExists( "Leads with this id does not exists :"+leadId)
        );
       Contact contact = maptoLeadContact(lead);
       String cid = UUID.randomUUID().toString();
       contact.setCid(cid);
       Contact saveContact  = contactRepository.save(contact);

       if(saveContact.getCid() != null){
           leadRepository.deleteById(lead.getLid());
       }
       ContactDto contactDto = maptoContactDto(saveContact);


        return contactDto;
    }
    Contact maptoLeadContact(Lead lead){
        return modelMapper.map(lead , Contact.class);
    }

    Contact maptoContact(ContactDto contactDto){
        return modelMapper.map(contactDto , Contact.class);
    }

    ContactDto maptoContactDto (Contact contact){
        return modelMapper.map(contact , ContactDto.class);
    }


}
