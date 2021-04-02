package com.example.lmtapp;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class dialog_custom extends DialogFragment {
    private EditText editText;
    private Button oks,cls;
  // private static final String TAG = "dialog_custom";


    private  String insertionUrl = "https://hellorandroid.000webhostapp.com/android_phpcon/custom_dialog.php";
    private RequestQueue requestQueue;
    private static final String TAG= dialog_custom.class.getSimpleName();
    int success;
    private  String TAG_SUCCESS = "success";
    private  String TAG_MESSAGE = "message";
    private  String tag_json_obj= "json_obj_req";

    String edt_code;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.codealertdialog,container,false);

        editText = rootview.findViewById(R.id.code_edt);
        oks = rootview.findViewById(R.id.code_btnOk);
        edt_code = editText.getText().toString();
        oks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendData();

            }
        });



        return rootview;
    }
    private void sendData () {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, insertionUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    success = jobj.getInt(TAG_SUCCESS);
                    if (success == 1) {


                        Toast.makeText(getContext().getApplicationContext(),"", Toast.LENGTH_SHORT).show();

                    } else {

                    }
                } catch (Exception e) {

                    Toast.makeText(getContext().getApplicationContext(), "Error Occured" + e, Toast.LENGTH_SHORT).show();

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext().getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
        }) {
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("usr_code", edt_code);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        requestQueue.add(stringRequest);
    }
}
