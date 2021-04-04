package com.example.lmtapp;

public class data_constructor {

    String usr_id ,usr_code,usr_fullname,usr_cpnumber,usr_address,usr_birthdate,usr_emailadd;

    public data_constructor(String usr_id, String usr_code, String usr_fullname, String usr_cpnumber, String usr_address, String usr_birthdate, String usr_emailadd) {
        this.usr_id = usr_id;
        this.usr_code = usr_code;
        this.usr_fullname = usr_fullname;
        this.usr_cpnumber = usr_cpnumber;
        this.usr_address = usr_address;
        this.usr_birthdate = usr_birthdate;
        this.usr_emailadd = usr_emailadd;

    }


    public String getUsr_id() {
        return usr_id;
    }

    public String getUsr_code() {
        return usr_code;
    }

    public String getUsr_fullname() {
        return usr_fullname;
    }

    public String getUsr_cpnumber() {
        return usr_cpnumber;
    }

    public String getUsr_address() {
        return usr_address;
    }

    public String getUsr_birthdate() {
        return usr_birthdate;
    }

    public String getUsr_emailadd() {
        return usr_emailadd;
    }



    public void setUsr_id(String usr_id) {
        this.usr_id = usr_id;
    }

    public void setUsr_code(String usr_code) {
        this.usr_code = usr_code;
    }

    public void setUsr_fullname(String usr_fullname) {
        this.usr_fullname = usr_fullname;
    }

    public void setUsr_cpnumber(String usr_cpnumber) {
        this.usr_cpnumber = usr_cpnumber;
    }

    public void setUsr_address(String usr_address) {
        this.usr_address = usr_address;
    }

    public void setUsr_birthdate(String usr_birthdate) {
        this.usr_birthdate = usr_birthdate;
    }

    public void setUsr_emailadd(String usr_emailadd) {
        this.usr_emailadd = usr_emailadd;
    }


}
