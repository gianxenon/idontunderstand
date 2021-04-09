package com.example.lmtapp;

public class data_constructor {

    String cred_codes,usr_code, payment_date,typeofterms,deb_lengthterm,deb_numberofperiod,deb_payment,deb_prinpaid,deb_intpaid,deb_balance,deb_paymentstat;

    public data_constructor(String cred_codes, String usr_code, String payment_date, String typeofterms, String deb_lengthterm, String deb_numberofperiod, String deb_payment, String deb_prinpaid, String deb_intpaid, String deb_balance, String deb_paymentstat) {
        this.cred_codes = cred_codes;
        this.usr_code = usr_code;
        this.payment_date = payment_date;
        this.typeofterms = typeofterms;
        this.deb_lengthterm = deb_lengthterm;
        this.deb_numberofperiod = deb_numberofperiod;
        this.deb_payment = deb_payment;
        this.deb_prinpaid = deb_prinpaid;
        this.deb_intpaid = deb_intpaid;
        this.deb_balance = deb_balance;
        this.deb_paymentstat = deb_paymentstat;
    }

    public String getCred_codes() {
        return cred_codes;
    }

    public String getUsr_code() {
        return usr_code;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public String getTypeofterms() {
        return typeofterms;
    }

    public String getDeb_lengthterm() {
        return deb_lengthterm;
    }

    public String getDeb_numberofperiod() {
        return deb_numberofperiod;
    }

    public String getDeb_payment() {
        return deb_payment;
    }

    public String getDeb_prinpaid() {
        return deb_prinpaid;
    }

    public String getDeb_intpaid() {
        return deb_intpaid;
    }

    public String getDeb_balance() {
        return deb_balance;
    }

    public String getDeb_paymentstat() {
        return deb_paymentstat;
    }
}
