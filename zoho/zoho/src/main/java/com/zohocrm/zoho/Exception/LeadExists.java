package com.zohocrm.zoho.Exception;

public class LeadExists extends RuntimeException{

    public LeadExists(String message) {
        super(message);
    }
}
