package com.zohocrm.zoho.Service;

import com.zohocrm.zoho.Payload.ContactDto;
import com.zohocrm.zoho.Payload.LeadDto;
import com.zohocrm.zoho.Payload.ResponsePage;

public interface ContactService {

    ContactDto createContact(String leadId);


}
