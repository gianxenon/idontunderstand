package com.example.lmtapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class frag_CredLoan_transProcs extends Fragment {

    ListView list_Views;
    public static ArrayList<procdata_list> lists= new ArrayList<>();
    MyProcessAdapter adapaterLists;
    String usr_id ,usr_code,usr_fullname,usr_cpnumber,usr_address,usr_birthdate,usr_emailadd;
    procdata_list listPojosa;
    EditText edt_Period;
    EditText edt_terms;
    EditText edt_Int;
    EditText edt_prinAmount;
    //computation Variables
        int terms;
        int noOfPeriod;
        double interest_rate;
        double principal_amount;

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
        //loan fields
        edt_Period = view.findViewById(R.id.proc_loan_edtPeriod);
        edt_terms = view.findViewById(R.id.proc_loan_edtTerm);
        edt_Int = view.findViewById(R.id.proc_loan_edtInt);
        edt_prinAmount = view.findViewById(R.id.proc_loan_edtPrinAmount);
        Button btn_reset = view.findViewById(R.id.btn_reset);
        Button btn_breakdown = view.findViewById(R.id.btn_process);



        //
        adapaterLists = new MyProcessAdapter(getContext(),lists);
        list_Views.setAdapter(adapaterLists);

        EditText txt = view.findViewById(R.id.deb_fn);
        txt.setText(usr_fullname);
btn_breakdown.setOnClickListener(v -> {
    if(edt_terms.getText().toString().equals("") && edt_Period.getText().toString().equals("") && edt_prinAmount.getText().toString().equals("") && edt_Int.getText().toString().equals("")){
        Toast.makeText(getContext(),"Fields cannot be empty",Toast.LENGTH_SHORT).show();

    }else {
        terms = Integer.parseInt(edt_terms.getText().toString());
        noOfPeriod = Integer.parseInt(edt_terms.getText().toString());
        interest_rate = Double.parseDouble(edt_Int.getText().toString());
        principal_amount = Double.parseDouble(edt_prinAmount.getText().toString());
        listShow();
    }
});

btn_reset.setOnClickListener(v -> {
    lists.clear();
    adapaterLists.notifyDataSetChanged();
});
        return  view;
    }


    private void listShow(){

       int nper = terms * 12 /noOfPeriod;

       for(int i = 0; i <= nper;i++){
           DecimalFormat df2 = new DecimalFormat("#.####");
           double int_paid = principal_amount * interest_rate;
           double prin_paid = principal_amount /nper;
           double ttl_payment = prin_paid + int_paid;
           double balance = principal_amount - prin_paid;
           principal_amount = balance;
           nper -= 1;
           listPojosa = new procdata_list(String.valueOf(i), df2.format(ttl_payment), df2.format(prin_paid), df2.format(int_paid), df2.format(balance));
           lists.add(listPojosa);
       }


        adapaterLists.notifyDataSetChanged();

    }

}