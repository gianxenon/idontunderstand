package com.example.lmtapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class frag_CredLoan_transProcs extends Fragment     {
    private  String insertionUrl = "https://hellorandroid.000webhostapp.com/android_phpcon/deb_transac.php";
    private RequestQueue requestQueue;
    ListView list_Views;
    public static ArrayList<procdata_list> lists= new ArrayList<>();
    MyProcessAdapter adapaterLists;
    String usr_id ,usr_code,usr_fullname,usr_cpnumber,usr_address,usr_birthdate,usr_emailadd;
    procdata_list listPojosa;
    EditText edt_Period;
    EditText edt_terms;
    EditText edt_Int;
    EditText edt_prinAmount;
    EditText deb_fn, deb_cpnum,deb_emls,deb_adrs ;
    String ofChoice = "";
    NavigationView navigationView;
    //computation Variables
        int terms;
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
        data_constructor dataConstructor = new data_constructor(usr_id ,usr_code,usr_fullname,usr_cpnumber,usr_address,usr_birthdate,usr_emailadd);
       //
        list_Views = view.findViewById(R.id.list_tableView);

        //debtor_info
        deb_fn = view.findViewById(R.id.deb_fn);
        deb_cpnum =  view.findViewById(R.id.deb_cpnum);
        deb_emls =  view.findViewById(R.id.deb_emls);
        deb_adrs =  view.findViewById(R.id.deb_adrs);
        //loan fields

        edt_terms = view.findViewById(R.id.proc_loan_edtTerm);
        edt_Int = view.findViewById(R.id.proc_loan_edtInt);
        edt_prinAmount = view.findViewById(R.id.proc_loan_edtPrinAmount);
        Button btn_reset = view.findViewById(R.id.btn_reset);
        Button btn_breakdown = view.findViewById(R.id.btn_process);
        Spinner spinner_log = view.findViewById(R.id.proc_loan_edtPeriod);
/*
        deb_fn.setText(dataconstructor.getUsr_fullname());
        deb_cpnum.setText(dataconstructor.getUsr_cpnumber());
        deb_emls.setText(dataconstructor.usr_emailadd);
        deb_adrs.setText(dataconstructor.usr_address);
*/
        //spinner

        final ArrayList<String> choice = new ArrayList<>();
        choice.add("Type of Terms");
        choice.add("Years (Monthly Payment)");
        choice.add("Months (Monthly Payment)");
        choice.add("Months (Daily Payment");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, choice);
        spinner_log.setAdapter(adapter);
        spinner_log.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ofChoice = choice.get(position);
                Toast.makeText(getContext(),"you selected " + ofChoice,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //

        adapaterLists = new MyProcessAdapter(getContext(),lists);
        list_Views.setAdapter(adapaterLists);
        // debtor info
        deb_fn = view.findViewById(R.id.deb_fn);
        deb_emls =view.findViewById(R.id.deb_emls);
        deb_adrs = view.findViewById(R.id.deb_adrs);
        deb_cpnum = view.findViewById(R.id.deb_cpnum);
        deb_fn.setText(usr_fullname);
        deb_emls.setText(usr_emailadd);
        deb_adrs.setText(usr_address);
        deb_cpnum.setText(usr_cpnumber);

    btn_breakdown.setOnClickListener(v -> {
    if(edt_terms.getText().toString().equals("") && edt_Period.getText().toString().equals("") && edt_prinAmount.getText().toString().equals("") && edt_Int.getText().toString().equals("")){
        Toast.makeText(getContext(),"Fields cannot be empty",Toast.LENGTH_SHORT).show();

    }else {

        listShow();
    }
    });

btn_reset.setOnClickListener(v -> {
    lists.clear();
    adapaterLists.notifyDataSetChanged();
});
        return  view;
    }
    //noOfPeriod = Integer.parseInt(edt_Period.getText().toString());

    private void listShow(){
        terms = Integer.parseInt(edt_terms.getText().toString());
        interest_rate = Double.parseDouble(edt_Int.getText().toString());
        principal_amount = Double.parseDouble(edt_prinAmount.getText().toString());

        if(ofChoice.equals("Years (Monthly Payment)")) {
            double n = terms * 12;
            double r = (interest_rate / 100) / n;
            double b = 1 + r;
            double x = (float) Math.pow(b, -n);
            double payFormula = (r / (1 - (x))) * -principal_amount; //(r / (1 - (1 + r)^-N)) * -pv
            double prinFormula;
            double inteFormula;
            double bal = payFormula * n;
            double e;
            double t;
            for (int i = 1; i <= n; i++) {
                DecimalFormat df2 = new DecimalFormat("#.####");
                e = (float) Math.pow(1 + r, i - 1);
                t = (float) Math.pow(1 + r, i - 1);
                prinFormula = payFormula + (payFormula * (e - 1) / r + principal_amount * (t)) * r;
                inteFormula = (-(payFormula * (e - 1) / r + principal_amount * (t)) * r);
                bal = bal - payFormula;
                listPojosa = new procdata_list(String.valueOf(i), df2.format(Math.abs(payFormula)), df2.format(Math.abs(prinFormula)), df2.format(Math.abs(inteFormula)), df2.format(Math.abs(bal)));
                lists.add(listPojosa);

            }
            adapaterLists.notifyDataSetChanged();
        }else if(ofChoice.equals("Months (Monthly Payment)")){

            double n=(terms / (double) 12)*12;
            double r = (interest_rate / 100) / n;
            double b = 1 + r;
            double x = (float) Math.pow(b, -n);
            double payFormula = (r / (1 - (x))) * -principal_amount; //(r / (1 - (1 + r)^-N)) * -pv
            double prinFormula;
            double inteFormula;
            double bal = payFormula * n;
            double e;
            double t;

            for (int i = 1; i <= n; i++) {
                DecimalFormat df2 = new DecimalFormat("#.####");
                e = (float) Math.pow(1 + r, i - 1);
                t = (float) Math.pow(1 + r, i - 1);
                prinFormula = payFormula + (payFormula * (e - 1) / r + principal_amount * (t)) * r;
                inteFormula = (-(payFormula * (e - 1) / r + principal_amount * (t)) * r);
                bal = bal - payFormula;
                listPojosa = new procdata_list(String.valueOf(i), df2.format(Math.abs(payFormula)), df2.format(Math.abs(prinFormula)), df2.format(Math.abs(inteFormula)), df2.format(Math.abs(bal)));
                lists.add(listPojosa);
            }
            adapaterLists.notifyDataSetChanged();
        }else{
            int d=360;
            int k=12;
            double c=(terms/ (double) k);
            double n=c*d;
            double r = (interest_rate / 100) / n;
            double b = 1 + r;
            double x = (float) Math.pow(b, -n);
            double payFormula = (r / (1 - (x))) * -principal_amount; //(r / (1 - (1 + r)^-N)) * -pv
            double prinFormula;
            double inteFormula;
            double bal = payFormula * n;
            double e;
            double t;
            for (int i = 1; i <= n; i++) {
                DecimalFormat df2 = new DecimalFormat("#.####");
                e = (float) Math.pow(1 + r, i - 1);
                t = (float) Math.pow(1 + r, i - 1);
                prinFormula = payFormula + (payFormula * (e - 1) / r + principal_amount * (t)) * r;
                inteFormula = (-(payFormula * (e - 1) / r + principal_amount * (t)) * r);
                bal = bal - payFormula;
                listPojosa = new procdata_list(String.valueOf(i), df2.format(Math.abs(payFormula)), df2.format(Math.abs(prinFormula)), df2.format(Math.abs(inteFormula)), df2.format(Math.abs(bal)));
                lists.add(listPojosa);

            }
            adapaterLists.notifyDataSetChanged();
        }

    }


    private void sendData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, insertionUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1")) {



                    } else {
                        Toast.makeText(getContext(),"Registration Failed", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(getContext(), "Error Occured" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "error on responce Volley Error", Toast.LENGTH_LONG).show();
            }
        }){
            public Map<String , String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
    for(int i = 0 ; i < lists.size() ;i++){

        data_constructor  dataConstructor =  new data_constructor (usr_id ,usr_code,usr_fullname,usr_cpnumber,usr_address,usr_birthdate,usr_emailadd);
        dataConstructor.setUsr_id(usr_id);
        dataConstructor.setUsr_code(usr_code);
        dataConstructor.setUsr_fullname(usr_fullname);
        dataConstructor.setUsr_cpnumber(usr_cpnumber);
        dataConstructor.setUsr_address(usr_address);
        dataConstructor.setUsr_birthdate(usr_birthdate);
        dataConstructor.setUsr_emailadd(usr_emailadd);
                params.put("cred_code",   );


        }
                return  params;
            }

        };

        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
        requestQueue.add(stringRequest);
    }

}