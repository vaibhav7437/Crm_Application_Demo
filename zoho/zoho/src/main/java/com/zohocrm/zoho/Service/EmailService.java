package com.zohocrm.zoho.Service;

import com.zohocrm.zoho.Entity.Email;
import com.zohocrm.zoho.Payload.EmailDto;

public interface EmailService {
    public EmailDto sendEmail(EmailDto emailDto);
}
