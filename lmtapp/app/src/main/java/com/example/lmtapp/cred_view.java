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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class cred_view extends Fragment {

    private  String insertionUrl = "https://hellorandroid.000webhostapp.com/android_phpcon/debtableview.php";
    private RequestQueue requestQueue;
    String deb_fn ,deb_cpnum,deb_emls,deb_adrs,usr_code,deb_code,typeofterm,term_len,interest,prin_amount;

    ListView list_Views;
    public static ArrayList<cred_listDatageter> lists= new ArrayList<>();
    cred_Adapter adapaterLists;
    cred_listDatageter listPojosa;
    String name ,number;

    public cred_view(String name, String number) {
        this.name = name;
        this.number = number;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cred_view,container,false);
        TextView  txt_fn = view.findViewById(R.id.textView8);
        TextView    txt_adrs =view.findViewById(R.id.textView11);
        TextView    txt_num = view.findViewById(R.id.textView12);
        //body header
        txt_fn.setText(deb_fn);
        txt_adrs.setText(deb_adrs);
        txt_num.setText(deb_cpnum);
        //
        TextView txt_prinamount = view.findViewById(R.id.edt_prinamount);
        TextView    txt_intRate = view.findViewById(R.id.edt_intrate);
        TextView    txt_paymethod   = view.findViewById(R.id.edt_intrate);



        list_Views = view.findViewById(R.id.cred_listView);
        adapaterLists = new cred_Adapter(getContext(),lists);
        list_Views.setAdapter(adapaterLists);

        number = number + " ";
        number =  number.substring(0,number.lastIndexOf(" "));


        datafetch();
        return  view;
    }
    private void datafetch() {

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
                        term_len = sads.getString("term_len");
                        interest = sads.getString("interest");
                        prin_amount = sads.getString("prin_amount");

                    }
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
                params.put("usr_code", number);
                Log.d("codes",number);
                return params;
            }
        };
        requestQueue= Volley.newRequestQueue(getContext().getApplicationContext());
        //   stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
        requestQueue.add(stringRequest);
    }
   void  listshow(){

    }
}