package com.example.lmtapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class cred_view extends Fragment {

    private  String insertionUrl = "https://hellorandroid.000webhostapp.com/android_phpcon/debtableview.php";
    private RequestQueue requestQueue;
    public static String deb_fn ,deb_cpnum,deb_emls,deb_adrs,usr_code,deb_code,typeofterm;
   int  term_len;
        double interest;
        double prin_amount;
        ArrayList<cred_listDatageter> lists= new ArrayList<cred_listDatageter>();
   // cred_Adapter adapaterLists;
    cred_listDatageter credListDatageter;
    String name ,number;
    String balances ="";
    public cred_view(String name, String number) {
        this.name = name;
        this.number = number;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cred_view,container,false);

        ListView  list_Views = view.findViewById(R.id.cred_listView);
        cred_Adapter adapaterLists = new cred_Adapter(getContext(),lists);
        list_Views.setAdapter(adapaterLists);
        StringRequest stringRequest  = new StringRequest(Request.Method.POST, insertionUrl, response -> {

            try {

                JSONObject jobj = new JSONObject(response);
                String success = jobj.getString("success");
                JSONArray sad = jobj.getJSONArray("user_info");
                if (success.equals("1")) {
                    for (int i =0;  i < sad.length(); i++) {
                        JSONObject sads = sad.getJSONObject(i);
                        deb_fn = sads.getString("deb_fn");
                        deb_cpnum = sads.getString("deb_cpnum");
                        deb_emls = sads.getString("deb_emls");
                        deb_adrs = sads.getString("deb_adrs");
                        usr_code = sads.getString("usr_code");
                        deb_code = sads.getString("deb_code");
                        typeofterm = sads.getString("typeofterm");
                        term_len =  Integer.parseInt(sads.getString("term_len"));
                        interest = Double.parseDouble(sads.getString("interest"));
                        prin_amount = Double.parseDouble(sads.getString("prin_amount"));

                    }

                    int term_lens  = term_len;
                    double interest_rates = interest;
                    double  principal_amounts = prin_amount;
                    if(typeofterm.equals("Years (Monthly Payment)")) {
                        double n = term_lens * 12;
                        double r = (interest_rates / 100) / n;
                        double b = 1 + r;
                        double x = (float) Math.pow(b, -n);
                        double payFormula = (r / (1 - (x))) * -principal_amounts; //(r / (1 - (1 + r)^-N)) * -pv
                        double prinFormula;
                        double inteFormula;
                        double bal = payFormula * n;
                        double e;
                        double t;
                        DecimalFormat df2 = new DecimalFormat("#.####");
                        balances = String.valueOf(df2.format(Math.abs(bal)));
                        for (int i = 1; i <= n; i++) {
                            e = (float) Math.pow(1 + r, i - 1);
                            t = (float) Math.pow(1 + r, i - 1);
                            prinFormula = payFormula + (payFormula * (e - 1) / r + principal_amounts * (t)) * r;
                            inteFormula = (-(payFormula * (e - 1) / r + principal_amounts * (t)) * r);
                            bal = bal - payFormula;
                            credListDatageter = new cred_listDatageter(String.valueOf(i), df2.format(Math.abs(payFormula)), df2.format(Math.abs(prinFormula)), df2.format(Math.abs(inteFormula)), df2.format(Math.abs(bal)),"-");
                            lists.add(credListDatageter);
                        }
                        adapaterLists.notifyDataSetChanged();

                    }else if(typeofterm.equals("Months (Monthly Payment)")){

                        double n=(term_lens / (double) 12)*12;
                        double r = (interest_rates / 100) / n;
                        double b = 1 + r;
                        double x = (float) Math.pow(b, -n);
                        double payFormula = (r / (1 - (x))) * -principal_amounts; //(r / (1 - (1 + r)^-N)) * -pv
                        double prinFormula;
                        double inteFormula;
                        double bal = payFormula * n;
                        double e;
                        double t;
                        DecimalFormat df2 = new DecimalFormat("#.####");
                        balances = String.valueOf(df2.format(Math.abs(bal)));
                        for (int i = 1; i <= n; i++) {

                            e = (float) Math.pow(1 + r, i - 1);
                            t = (float) Math.pow(1 + r, i - 1);
                            prinFormula = payFormula + (payFormula * (e - 1) / r + principal_amounts * (t)) * r;
                            inteFormula = (-(payFormula * (e - 1) / r + principal_amounts * (t)) * r);
                           bal = bal - payFormula;
                            credListDatageter = new cred_listDatageter(String.valueOf(i), df2.format(Math.abs(payFormula)), df2.format(Math.abs(prinFormula)), df2.format(Math.abs(inteFormula)), df2.format(Math.abs(bal)),"-");
                            lists.add(credListDatageter);
                        }
                        adapaterLists.notifyDataSetChanged();
                    }else{
                        int d=360;
                        int k=12;
                        double c=(term_lens/ (double) k);
                        double n=c*d;
                        double r = (interest_rates / 100) / n;
                        double b = 1 + r;
                        double x = (float) Math.pow(b, -n);
                        double payFormula = (r / (1 - (x))) * -principal_amounts; //(r / (1 - (1 + r)^-N)) * -pv
                        double prinFormula;
                        double inteFormula;
                        double bal = payFormula * n;
                        double e;
                        double t;
                        DecimalFormat df2 = new DecimalFormat("#.####");
                        balances = String.valueOf(df2.format(Math.abs(bal)));
                        for (int i = 1; i <= n; i++) {
                            e = (float) Math.pow(1 + r, i - 1);
                            t = (float) Math.pow(1 + r, i - 1);
                            prinFormula = payFormula + (payFormula * (e - 1) / r + principal_amounts * (t)) * r;
                            inteFormula = (-(payFormula * (e - 1) / r + principal_amounts * (t)) * r);
                            bal = bal - payFormula;
                            credListDatageter = new cred_listDatageter(String.valueOf(i), df2.format(Math.abs(payFormula)), df2.format(Math.abs(prinFormula)), df2.format(Math.abs(inteFormula)), df2.format(Math.abs(bal)),"-");
                            lists.add(credListDatageter);
                        }
                        adapaterLists.notifyDataSetChanged();
                    }
                    TextView txt_fn = view.findViewById(R.id.edt_fn);
                    TextView    txt_adrs =view.findViewById(R.id.textView11);
                    TextView    txt_num = view.findViewById(R.id.textView12);
                    TextView    txt_prinamount =view.findViewById(R.id.edt_prinamount);
                    TextView    txt_rate = view.findViewById(R.id.edt_intrate);
                    TextView    txt_paymeth = view.findViewById(R.id.edt_paymeth);
                    TextView    txt_bal = view.findViewById(R.id.edt_bal);
                    txt_adrs.setText(deb_adrs);
                    txt_num.setText(deb_cpnum);
                    txt_fn.setText(deb_fn);
                    txt_prinamount.setText(String.valueOf(prin_amount));
                    txt_rate.setText(String.valueOf(interest));
                    txt_paymeth.setText(String.valueOf(term_len));
                    txt_bal.setText(String.valueOf(balances));

                    Toast.makeText(getContext().getApplicationContext(), "Success Fetching data for list", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext().getApplicationContext(), "error Fetching data for list", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(getContext().getApplicationContext(), "Fetching in database error" + e, Toast.LENGTH_LONG).show();
            }
        }, (VolleyError error) -> {
            Toast.makeText(getContext().getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
        }){
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                number  += " ";
                params.put("usr_code", number.substring(1,number.lastIndexOf(" ")));
                Log.d("codes",params.toString());

                return params;
            }
        };
        requestQueue= Volley.newRequestQueue(getContext().getApplicationContext());
        //   stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
        requestQueue.add(stringRequest);






        //datafetch();

        return  view;
    }
    public  void datafetch() {

    }

   public void  listshow(String typeofterm,int term_len,double interest,double prin_amount){


   }//end of listshow

}