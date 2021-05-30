package com.example.lmtapp;

public class data_constructor {

    String usr_id,cred_codes,deb_code, deb_OpenPos,typeofterms,deb_lengthterm,PAYMENY_METHOD,CAPITAL,INTEREST;

    public data_constructor(String usr_id,String cred_codes, String deb_code, String deb_OpenPos, String typeofterms,String PAYMENY_METHOD ,String deb_lengthterm , String CAPITAL, String INTEREST) {
        this.cred_codes = cred_codes;
        this.deb_code = deb_code;
        this.deb_OpenPos = deb_OpenPos;
        this.typeofterms = typeofterms;
        this.deb_lengthterm = deb_lengthterm;
        this.PAYMENY_METHOD = PAYMENY_METHOD;
        this.CAPITAL = CAPITAL;
        this.INTEREST = INTEREST;
        this.usr_id = usr_id;
    }

    public String getUsr_id() {
        return usr_id;
    }

    public void setUsr_id(String usr_id) {
        this.usr_id = usr_id;
    }

    public String getCred_codes() {
        return cred_codes;
    }

    public void setCred_codes(String cred_codes) {
        this.cred_codes = cred_codes;
    }

    public String getDeb_code() {
        return deb_code;
    }

    public void setDeb_code(String deb_code) {
        this.deb_code = deb_code;
    }

    public String getDeb_OpenPos() {
        return deb_OpenPos;
    }

    public void setDeb_OpenPos(String deb_OpenPos) {
        this.deb_OpenPos = deb_OpenPos;
    }

    public String getTypeofterms() {
        return typeofterms;
    }

    public void setTypeofterms(String typeofterms) {
        this.typeofterms = typeofterms;
    }

    public String getDeb_lengthterm() {
        return deb_lengthterm;
    }

    public void setDeb_lengthterm(String deb_lengthterm) {
        this.deb_lengthterm = deb_lengthterm;
    }

    public String getPAYMENY_METHOD() {
        return PAYMENY_METHOD;
    }

    public void setPAYMENY_METHOD(String PAYMENY_METHOD) {
        this.PAYMENY_METHOD = PAYMENY_METHOD;
    }

    public String getCAPITAL() {
        return CAPITAL;
    }

    public void setCAPITAL(String CAPITAL) {
        this.CAPITAL = CAPITAL;
    }

    public String getINTEREST() {
        return INTEREST;
    }

    public void setINTEREST(String INTEREST) {
        this.INTEREST = INTEREST;
    }
}
