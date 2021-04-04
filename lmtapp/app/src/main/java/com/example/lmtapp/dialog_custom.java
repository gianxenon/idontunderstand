package com.example.lmtapp;

import android.content.Context;
import android.content.Intent;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class dialog_custom  extends DialogFragment  {
    private dialog_custom.tofragCREDproces listener;
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
    String usr_id ,usr_code,usr_fullname,usr_cpnumber,usr_address,usr_birthdate,usr_emailadd,usr_username,usr_password;
    public static String edt_code;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.codealertdialog,container,false);

        editText = rootview.findViewById(R.id.code_edt);
        oks = rootview.findViewById(R.id.code_btnOk);
        edt_code = editText.getText().toString();
        requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
        oks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendData();

            }
        });


        return rootview;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof dialog_custom.tofragCREDproces){

            listener = (dialog_custom.tofragCREDproces) context;
        }else {
            throw  new ClassCastException(context.toString() + "must implement listener");
        }
    }
    public interface  tofragCREDproces{
        void ondialogBtnSelected();
    }
    private void sendData () {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, insertionUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jobj = new JSONObject(response);
                   String success = jobj.getString("success");
                    JSONArray sad = jobj.getJSONArray("user_info");
                    if (success.equals("1")) {
                        for (int i = 0; i < jobj.length()  -1 ; i++) {
                            JSONObject sads = sad.getJSONObject(i);
                            usr_id = sads.getString("usr_id");
                            usr_code = sads.getString("usr_code");
                            usr_fullname = sads.getString("usr_fullname");
                            usr_cpnumber = sads.getString("usr_cpnumber");
                            usr_address = sads.getString("usr_address");
                            usr_birthdate = sads.getString("usr_birthdate");
                            usr_emailadd = sads.getString("usr_emailadd");

                        }
                        data_constructor  dataConstructor =  new data_constructor (usr_id ,usr_code,usr_fullname,usr_cpnumber,usr_address,usr_birthdate,usr_emailadd);
                        dataConstructor.setUsr_id(usr_id);
                        dataConstructor.setUsr_code(usr_code);
                        dataConstructor.setUsr_fullname(usr_fullname);
                        dataConstructor.setUsr_cpnumber(usr_cpnumber);
                        dataConstructor.setUsr_address(usr_address);
                        dataConstructor.setUsr_birthdate(usr_birthdate);
                        dataConstructor.setUsr_emailadd(usr_emailadd);

                        Toast.makeText(Objects.requireNonNull(getContext()).getApplicationContext(), "Code exist", Toast.LENGTH_SHORT).show();
                        listener.ondialogBtnSelected();
                      getDialog().dismiss();
                    } else {

                        Toast.makeText(getContext().getApplicationContext(), "Code doesnt exist", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                    Toast.makeText(getContext().getApplicationContext(), "catch : Error Occured -> " + e, Toast.LENGTH_SHORT).show();

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
                params.put("usr_code", editText.getText().toString());

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        requestQueue.add(stringRequest);
    }
}
