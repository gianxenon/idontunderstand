package com.example.lmtapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class dialog_payment extends DialogFragment {
    DatePickerDialog.OnDateSetListener onDateSetListener;
    private static final String TAG= dialog_payment.class.getSimpleName();
    private final String insertionUrl = "https://hellorandroid.000webhostapp.com/android_phpcon/transhistory.php";
    public RequestQueue requestQueue;
    String deb_tranid,cred_code,deb_code,amountpay,balance,date;
    String pb;
    String prev_pay ="0";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.pay_dialog,container,false);
        DecimalFormat df2 = new DecimalFormat("#.####");
        EditText payamount = rootview.findViewById(R.id.edt_dialogAmout);
        EditText payDate = rootview.findViewById(R.id.edt_dialogDate);
        Button btn_pay = rootview.findViewById(R.id.btn_dialogPay);

        Bundle mArgs = getArguments();
        assert mArgs != null;
        deb_tranid = mArgs.getString("deb_transid");
         cred_code = mArgs.getString("usr_code");
         deb_code = mArgs.getString("deb_code");
         amountpay = mArgs.getString("amountpay");
         balance = mArgs.getString("balance");
        payamount.setText(df2.format(Math.abs(Double.parseDouble(amountpay))));


        payDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), onDateSetListener, year, month, day);
            datePickerDialog.show();
        });
        onDateSetListener = (view, year, month, dayOfMonth) -> {
            month = month + 1;
             date = dayOfMonth + "/" + month + "/" + year;
            payDate.setText(date);
        };

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData();

            }
        });

        return  rootview;
    }
    public void sendData () {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, insertionUrl, response -> {
            try {

                JSONObject jobj = new JSONObject(response);
                String success = jobj.getString("success");
               // JSONArray sad = jobj.getJSONArray("user_info");
                if (success.equals("1")) {
                    Toast.makeText(getContext(), "Payment Complete", Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();
                } else {
                    Toast.makeText(getContext(), "Payment Denied", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

                Toast.makeText(getContext(), "No Internet Connection-> " + e, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
        }) {
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                DecimalFormat df2 = new DecimalFormat("#.####");
                params.put("deb_transid",deb_tranid);
                params.put("cred_code",cred_code);
                params.put("amountpay", df2.format(Math.abs(Double.parseDouble(amountpay))));
                params.put("paydate",date);
                params.put("balance", df2.format(Math.abs(Math.abs(Double.parseDouble(balance)) - (Double.parseDouble(prev_pay) + Math.abs(Double.parseDouble(amountpay))))));
                params.put("checker","0");

                return params;
            }
        };
        requestQueue= Volley.newRequestQueue(getContext());
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        requestQueue.add(stringRequest);
    }//end senddata

    public void fetchData () {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, insertionUrl, response -> {
            try {

                JSONObject jobj = new JSONObject(response);
                String success = jobj.getString("success");
                JSONArray sad = jobj.getJSONArray("user_info");
                if (success.equals("1")) {
                    for (int i =0;  i < sad.length(); i++) {
                        JSONObject sads = sad.getJSONObject(i);
                        pb = sads.getString("balances");
                        prev_pay = sads.getString("sum_score");
                        Log.d("pb",pb);
                        Log.d("prev",prev_pay);
                    }
                    sendData();
                    Toast.makeText(Objects.requireNonNull(getContext()).getApplicationContext(), "Payment Complete", Toast.LENGTH_SHORT).show();
                 //   getDialog().dismiss();
                } else if(success.equals("3")) {
                    sendData();
                } else {
                    Toast.makeText(getContext(), "Payment Denied", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

                Toast.makeText(getContext(), "fecth No Internet Connection-> " + e, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
        }) {
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //DecimalFormat df2 = new DecimalFormat("#.####");
                params.put("deb_transid",deb_tranid);
                params.put("checker","1");
                return params;
            }
        };
        requestQueue= Volley.newRequestQueue(getContext());
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        requestQueue.add(stringRequest);
    }
}
