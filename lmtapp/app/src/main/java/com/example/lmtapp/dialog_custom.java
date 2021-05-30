package com.example.lmtapp;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

public class dialog_custom  extends DialogFragment  {

    private EditText editText;
    private Button oks,cls;
    private final String insertionUrl = "https://hellorandroid.000webhostapp.com/android_phpcon/custom_dialog.php";
    private RequestQueue requestQueue;
    private static final String TAG= creditors_uis.class.getSimpleName();
    private  String TAG_SUCCESS = "success";
    private  String TAG_MESSAGE = "message";
    private  String tag_json_obj= "json_obj_req";
    String  deb_openpos,usr_id ,usr_code,usr_fullname,usr_cpnumber,usr_address,usr_birthdate,usr_emailadd,usr_imageUrl;
    public static String edt_code;


    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    String lenders_id,lenders_code;
    public dialog_custom(String lenders_id, String lenders_code) {
        this.lenders_id = lenders_id;
        this.lenders_code = lenders_code;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.codealertdialog,container,false);

        editText = rootview.findViewById(R.id.code_edt);
        oks = rootview.findViewById(R.id.code_btnOk);
        edt_code = editText.getText().toString();
        requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
        oks.setOnClickListener(v -> sendData());


        return rootview;
    }

    private void sendData () {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, insertionUrl, response -> {
            try {

                JSONObject jobj = new JSONObject(response);
               String success = jobj.getString("success");
                JSONArray sad = jobj.getJSONArray("user_info");
                if (success.equals("1")) {
                    for (int i = 0; i < jobj.length() -1; i++) {
                        JSONObject sads = sad.getJSONObject(i);
                        deb_openpos = sads.getString("deb_OpenPos");
                        usr_id = sads.getString("usr_id");
                        usr_code = sads.getString("usr_code");
                        usr_fullname = sads.getString("usr_fullname");
                        usr_cpnumber = sads.getString("usr_cpnumber");
                        usr_address = sads.getString("usr_address");
                        usr_birthdate = sads.getString("usr_birthdate");
                        usr_emailadd = sads.getString("usr_emailadd");
                        usr_imageUrl = sads.getString("usr_imageUrl");

                    }
                    Log.d("chks",response);
                    Toast.makeText(Objects.requireNonNull(getContext()).getApplicationContext(), "Code exist", Toast.LENGTH_SHORT).show();
                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();

                        fragmentTransaction.replace(R.id.container_fragment,new frag_CredLoan_transProcs( deb_openpos, lenders_id,  lenders_code, usr_id,  usr_code,  usr_fullname,  usr_cpnumber,  usr_address,  usr_birthdate,  usr_emailadd,  usr_imageUrl)).addToBackStack(null);
                    fragmentTransaction.commit();
                    //listener.ondialogBtnSelected();
                  getDialog().dismiss();
                } else {

                    Toast.makeText(getContext().getApplicationContext(), "Code doesnt exist", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

                Toast.makeText(getContext().getApplicationContext(), "catch : Error Occured -> " + e, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext().getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
        }) {
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("usr_code", editText.getText().toString());

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        requestQueue.add(stringRequest);
    }
}
