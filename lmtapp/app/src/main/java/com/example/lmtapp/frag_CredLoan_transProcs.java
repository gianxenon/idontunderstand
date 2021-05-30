package com.example.lmtapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class frag_CredLoan_transProcs extends Fragment      {

    private  String insertionUrl = "https://hellorandroid.000webhostapp.com/android_phpcon/deb_transac.php";
    private RequestQueue requestQueue;
    ListView list_Views;
    public static ArrayList<procdata_list> lists= new ArrayList<>();
    MyProcessAdapter adapaterLists;
    String usr_id ,usr_code,usr_fullname,usr_cpnumber,usr_address,usr_birthdate,usr_emailadd,usr_imageUrl,deb_openpos;
    procdata_list listPojosa;
    EditText edt_Period;
    EditText edt_terms;
    EditText edt_Int;
    EditText edt_prinAmount;
    EditText deb_fn, deb_cpnum,deb_emls,deb_adrs ;
    String ofChoice = "";
    String temp = "";
    String cred_codes;
    String lenders_id,lenders_code;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    int pos ;
    //computation Variables
    ArrayList<data_constructor> listss = new ArrayList<>();
            int terms;
        double interest_rate;
        double principal_amount;


    public frag_CredLoan_transProcs(String deb_openpos,String lenders_id, String lenders_code,String usr_id, String usr_code, String usr_fullname, String usr_cpnumber, String usr_address, String usr_birthdate, String usr_emailadd, String usr_imageUrl ) {
        this.lenders_code = lenders_code;
        this.lenders_id = lenders_id;
        this.usr_id = usr_id;
        this.usr_code = usr_code;
        this.usr_fullname = usr_fullname;
        this.usr_cpnumber = usr_cpnumber;
        this.usr_address = usr_address;
        this.usr_birthdate = usr_birthdate;
        this.usr_emailadd = usr_emailadd;
        this.usr_imageUrl = usr_imageUrl;
        this.deb_openpos = deb_openpos;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view =   inflater.inflate(R.layout.fragment_frag__cred_loan_trans_procs, container, false);



       //
        list_Views = view.findViewById(R.id.list_tableView);

        requestQueue = Volley.newRequestQueue(getContext());

        try {
            FileInputStream fin = getActivity().openFileInput("file.txt");
            int c;

            while( (c = fin.read()) != -1){
                temp = temp + (char) c;
            }

            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cred_codes = temp;

  /*
        try {
            FileOutputStream fOut = getActivity().openFileOutput("file.txt",Context.MODE_PRIVATE);


            fOut.write(usr_code.getBytes());
            fOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 */

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
        Button btn_process = view.findViewById(R.id.btn_saveProcess);
        Spinner spinner_log = view.findViewById(R.id.proc_loan_edtPeriod);


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
            public void onNothingSelected(AdapterView<?> parent) { }});

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
        usr_code = usr_cpnumber;

        //btn to process loan (send to database)
        btn_process.setOnClickListener(v -> {
            /*
            for (int i = 0; i <= lists.size() - 1; i++) {
                listss.add(new data_constructor(cred_codes,usr_code,"not paid",ofChoice,ofChoice,edt_terms.getText().toString(),
                lists.get(i).getRow1(),lists.get(i).getRow2(), lists.get(i).getRow3(),lists.get(i).getRow4(),lists.get(i).getRow5()));
            }
            cred_codes,deb_code, deb_OpenPos,typeofterms,deb_lengthterm,PAYMENY_METHOD,CAPITAL,INTEREST;
             */
            listss.add(new data_constructor(usr_id,lenders_code,usr_code,deb_openpos,ofChoice,ofChoice,String.valueOf(terms),edt_prinAmount.getText().toString(),edt_Int.getText().toString()));
             sendData();
            });
        //end of => btn to process loan (send to database)


    //btn to show Breakdown
    btn_breakdown.setOnClickListener(v -> {
    if(edt_terms.getText().toString().equals("") && edt_Period.getText().toString().equals("") && edt_prinAmount.getText().toString().equals("") && edt_Int.getText().toString().equals("") && ofChoice.equals("Type of Terms")){
        Toast.makeText(getContext(),"Fields cannot be empty",Toast.LENGTH_SHORT).show();
    }else { listShow(); } });
    //end of => btn to show Breakdown

    //btn to clear fields
    btn_reset.setOnClickListener(v -> {
    lists.clear();
    adapaterLists.notifyDataSetChanged();
    });
    //end of => btn to clear fields
    lists.clear();
        return  view;
    }//oncreateView




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
                listPojosa = new procdata_list(String.valueOf(i), df2.format(Math.abs(payFormula)), df2.format(Math.abs(prinFormula)), df2.format(Math.abs(inteFormula)), df2.format(Math.abs(bal)),lenders_id,usr_id,deb_openpos,lenders_code,usr_code,"-");
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
                listPojosa = new procdata_list(String.valueOf(i), df2.format(Math.abs(payFormula)), df2.format(Math.abs(prinFormula)), df2.format(Math.abs(inteFormula)), df2.format(Math.abs(bal)),lenders_id,usr_id,deb_openpos,lenders_code,usr_code,"-");
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
                String pay_stat = "--";
                listPojosa = new procdata_list(String.valueOf(i), df2.format(Math.abs(payFormula)), df2.format(Math.abs(prinFormula)), df2.format(Math.abs(inteFormula)), df2.format(Math.abs(bal)),lenders_id,usr_id,deb_openpos,lenders_code,usr_code,"-");
                lists.add(listPojosa);
            }

            adapaterLists.notifyDataSetChanged();
        }
    }//end of listshow


    private void sendData(){

            StringRequest stringRequest = new StringRequest(Request.Method.POST, insertionUrl, response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1")) {

                        Toast.makeText(getContext(), "loan Tracsaction Successful", Toast.LENGTH_SHORT).show();

                    } else if(success.equals("3")){
                        Toast.makeText(getContext(), "Empty post", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "loan Tracsaction Failed" + response, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.d("respo1","Length :- "+ response);
                    Toast.makeText(getContext(), "Error Occured sad " + e, Toast.LENGTH_SHORT).show();
                }
            }, error -> Toast.makeText(getContext(), "error on responce Volley Error", Toast.LENGTH_LONG).show()) {
                public Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    String data = new Gson().toJson(lists);
                    String data2 = new Gson().toJson(listss);
                    params.put("debLoanInfo", data2);
                    params.put("terms", data);
                    Log.d("DATA_",data2);
                    Log.d("termsa",params.toString());
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }


}


