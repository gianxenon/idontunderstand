package com.example.lmtapp;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class cred_view extends Fragment {

    private String insertionUrl = "https://hellorandroid.000webhostapp.com/android_phpcon/debtableview.php";
    private String fetchUrl = "https://hellorandroid.000webhostapp.com/android_phpcon/transhistory.php";
    private RequestQueue requestQueue;

    int term_len;
    double interest;
    double prin_amount;
    ArrayList<cred_listDatageter> lists = new ArrayList<cred_listDatageter>();
    // cred_Adapter adapaterLists;
    cred_listDatageter credListDatageter;
    public String  lenders_code, debtors_code;
    public String   deb_fnss, deb_cpnum,deb_adrs , deb_emls, deb_imgs,  deb_pos,captals,intrests ,termtypes;
    Double balances = 0.00;
    String amountpay = "0";
    String temp = "";
    String cred_codess = "0";
    DecimalFormat df2 = new DecimalFormat("#.####");
    TextView txt_bal;
    String ofChoice = "";
    public cred_view(String debtors_code , String lenders_code) {
        this.lenders_code = lenders_code;
        this.debtors_code = debtors_code;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cred_view, container, false);


        ListView list_Views = view.findViewById(R.id.cred_listView);
        cred_Adapter adapaterLists = new cred_Adapter(getContext(), lists);
        list_Views.setAdapter(adapaterLists);

        //user info fields
        ImageView deb_img = view.findViewById(R.id.imageView6);
        TextView deb_fns = view.findViewById(R.id.edt_fn);
        TextView deb_adrss = view.findViewById(R.id.textView11);
        TextView deb_cntcs = view.findViewById(R.id.textView12);
        TextView deb_emlss = view.findViewById(R.id.textView10);
        Spinner spinner_log = view.findViewById(R.id.spinner2);
        TextView captal = view.findViewById(R.id.edt_prinamount);
        TextView intrest = view.findViewById(R.id.edt_intrate);
        TextView edt_paymeth = view.findViewById(R.id.edt_paymeth);
        TextView edt_bals= view.findViewById(R.id.edt_bal);




        StringRequest stringRequest = new StringRequest(Request.Method.POST, insertionUrl, response -> {

            try {

                JSONObject jobj = new JSONObject(response);
                String success = jobj.getString("success");
                JSONArray user_info = jobj.getJSONArray("user_info");
                JSONArray debtransac = jobj.getJSONArray("debtransac");
                if (success.equals("1")) {
                    for (int i = 0; i < user_info.length(); i++) {
                        JSONObject deb_info = user_info.getJSONObject(i);
                       // deb_fnss = deb_info.getString("deb_fn");
                       // deb_cpnum = deb_info.getString("deb_cpnum");
                       // deb_adrs =deb_info.getString("deb_adrs");
                       // deb_emls =deb_info.getString("EMAIL_ADDRESS");
                       // deb_imgs = deb_info.getString("image");
                        deb_fns.setText(deb_info.getString("deb_fn"));
                        deb_cntcs.setText(deb_info.getString("deb_cpnum"));
                        deb_adrss.setText(deb_info.getString("deb_adrs"));
                        deb_emlss.setText(deb_info.getString("EMAIL_ADDRESS"));
                        String url = "https://hellorandroid.000webhostapp.com/android_phpcon/Image/" + deb_info.getString("image");
                        Glide.with(getContext().getApplicationContext()).load(url).into(deb_img);
                        final ArrayList<String> choice = new ArrayList<>();
                        choice.add("Loan " + deb_info.getString("deb_OpenPos"));
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
                        captal.setText(deb_info.getString("CAPITAL"));
                        intrest.setText(deb_info.getString("INTEREST"));
                        edt_paymeth.setText(deb_info.getString("TERM_TYPE"));

                    }

                for (int i = 0; i < debtransac.length(); i++) {
                    JSONObject sads = debtransac.getJSONObject(i);
                    balances = balances +  Double.parseDouble(sads.getString("deb_payment"));
                    lists.add(new cred_listDatageter(   sads.getString("assumed_payDate"),
                                                        sads.getString("deb_payment"),
                                                        sads.getString("prin_paid"),
                                                        sads.getString("int_paid"),
                                                        sads.getString("assumed_balance"),
                                                        sads.getString("payment_status")));

                }
                    edt_bals.setText(String.valueOf(balances));
                    adapaterLists.notifyDataSetChanged();

                    Toast.makeText(getContext().getApplicationContext(), "Success Fetching data for list", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext().getApplicationContext(), "error Fetching data for list", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(getContext().getApplicationContext(), "Fetching in database error" + e, Toast.LENGTH_LONG).show();
            }
        }, (VolleyError error) -> {
            Toast.makeText(getContext().getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
        }) {
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lenders_code", lenders_code);
                params.put("debtors_code", debtors_code);
                Log.d("codes", params.toString());
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
        requestQueue.add(stringRequest);

        return view;
    }



public void fecthdata(){

}}