package com.example.lmtapp;

public class cred_listDatageter {
    private String row1 ;
    private String row2;
    private String row3;
    private String row4;
    private String row5;
    private String row6;
//
    public cred_listDatageter(String row1, String row2, String row3, String row4, String row5, String row6) {
        this.row1 = row1;
        this.row2 = row2;
        this.row3 = row3;
        this.row4 = row4;
        this.row5 = row5;
       this.row6 = row6;
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

    public String getRow6() {
       return row6;
   }
}
