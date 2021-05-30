package com.example.lmtapp;

public class procdata_list {
    private String row1,row2,row3,row4,row5 ;
    private String cred_id,	deb_id,deb_OpenPos,cred_code,deb_code,payment_status;

    public procdata_list(String row1, String row2, String row3, String row4, String row5, String cred_id, String deb_id, String deb_OpenPos, String cred_code, String deb_code,String payment_status) {
        this.row1 = row1;
        this.row2 = row2;
        this.row3 = row3;
        this.row4 = row4;
        this.row5 = row5;
        this.cred_id = cred_id;
        this.deb_id = deb_id;
        this.deb_OpenPos = deb_OpenPos;
        this.cred_code = cred_code;
        this.deb_code = deb_code;
       this.payment_status = payment_status;
    }

    public String getRow1() {
        return row1;
    }

    public String getRow2() {
        return row2;
    }

    public String getRow3() {
        return row3;
    }

    public String getRow4() {
        return row4;
    }

    public String getRow5() {
        return row5;
    }

    public void setRow1(String row1) {
        this.row1 = row1;
    }

    public void setRow2(String row2) {
        this.row2 = row2;
    }

    public void setRow3(String row3) {
        this.row3 = row3;
    }

    public void setRow4(String row4) {
        this.row4 = row4;
    }

    public void setRow5(String row5) {
        this.row4 = row5;
    }

    public String getCred_id() {
        return cred_id;
    }

    public void setCred_id(String cred_id) {
        this.cred_id = cred_id;
    }

    public String getDeb_id() {
        return deb_id;
    }

    public void setDeb_id(String deb_id) {
        this.deb_id = deb_id;
    }

    public String getDeb_OpenPos() {
        return deb_OpenPos;
    }

    public void setDeb_OpenPos(String deb_OpenPos) {
        this.deb_OpenPos = deb_OpenPos;
    }

    public String getCred_code() {
        return cred_code;
    }

    public void setCred_code(String cred_code) {
        this.cred_code = cred_code;
    }

    public String getDeb_code() {
        return deb_code;
    }

    public void setDeb_code(String deb_code) {
        this.deb_code = deb_code;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }
}
