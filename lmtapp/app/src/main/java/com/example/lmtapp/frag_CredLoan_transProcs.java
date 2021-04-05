package com.example.lmtapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class frag_CredLoan_transProcs extends Fragment {

    ListView list_Views;
    public static ArrayList<procdata_list> lists= new ArrayList<procdata_list>();
    MyProcessAdapter adapaterLists;
    String usr_id ,usr_code,usr_fullname,usr_cpnumber,usr_address,usr_birthdate,usr_emailadd;
    procdata_list procdataList;
    public frag_CredLoan_transProcs(String usr_id, String usr_code, String usr_fullname, String usr_cpnumber, String usr_address, String usr_birthdate, String usr_emailadd) {
        this.usr_id = usr_id;
        this.usr_code = usr_code;
        this.usr_fullname = usr_fullname;
        this.usr_cpnumber = usr_cpnumber;
        this.usr_address = usr_address;
        this.usr_birthdate = usr_birthdate;
        this.usr_emailadd = usr_emailadd;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view =   inflater.inflate(R.layout.fragment_frag__cred_loan_trans_procs, container, false);

        list_Views = view.findViewById(R.id.list_tableView);


        adapaterLists = new MyProcessAdapter(getContext(),lists);

        list_Views.setAdapter(adapaterLists);
        listShow();
        EditText txt = view.findViewById(R.id.deb_fn);
        txt.setText(usr_fullname);
        return  view;
    }


    private void listShow(){
        lists.add(  procdataList = new procdata_list("dasda","asdada","asdasda","asdasda"));
        lists.add(  procdataList = new procdata_list("dasda","asdada","asdasda","asdasda"));
        lists.add(  procdataList = new procdata_list("dasda","asdada","asdasda","asdasda"));
        lists.add(  procdataList = new procdata_list("dasda","asdada","asdasda","asdasda"));
        lists.add(  procdataList = new procdata_list("dasda","asdada","asdasda","asdasda"));
        lists.add(  procdataList = new procdata_list("dasda","asdada","asdasda","asdasda"));
        lists.add(  procdataList = new procdata_list("dasda","asdada","asdasda","asdasda"));
        lists.add(  procdataList = new procdata_list("dasda","asdada","asdasda","asdasda"));
        lists.add(  procdataList = new procdata_list("dasda","asdada","asdasda","asdasda"));
        lists.add(  procdataList = new procdata_list("dasda","asdada","asdasda","asdasda"));
        lists.add(  procdataList = new procdata_list("dasda","asdada","asdasda","asdasda"));
        lists.add(  procdataList = new procdata_list("dasda","asdada","asdasda","asdasda"));
        lists.add(  procdataList = new procdata_list("dasda","asdada","asdasda","asdasda"));
        lists.add(  procdataList = new procdata_list("dasda","asdada","asdasda","asdasda"));
        lists.add(  procdataList = new procdata_list("dasda","asdada","asdasda","asdasda"));
        lists.add(  procdataList = new procdata_list("dasda","asdada","asdasda","asdasda"));
        lists.add(  procdataList = new procdata_list("dasda","asdada","asdasda","asdasda"));
        lists.add(  procdataList = new procdata_list("dasda","asdada","asdasda","asdasda"));

    }

}