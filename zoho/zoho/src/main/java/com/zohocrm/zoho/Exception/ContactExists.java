package com.zohocrm.zoho.Exception;

public class ContactExists extends RuntimeException {
    public ContactExists(String s) {
        super(s);
    }
}
