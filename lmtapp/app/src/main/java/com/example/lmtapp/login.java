package com.example.lmtapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.util.logging.Level.CONFIG;

public class login extends AppCompatActivity {
    private View deroc;
    Button btn_login;
    TextView clkalbe_tvw;
    String usr,pas ="";
    EditText uname,pword;
    private  String insertionUrl = "https://hellorandroid.000webhostapp.com/android_phpcon/login.php";
    private RequestQueue requestQueue;
    private static final String TAG= login.class.getSimpleName();
    private  String tag_json_obj= "json_obj_req";
    String usr_id ,usr_code,usr_fullname,usr_cpnumber,usr_address,usr_birthdate,usr_emailadd,usr_username,usr_password,usr_imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        clkalbe_tvw=findViewById(R.id.textView3);
        uname = findViewById(R.id.edt_logUname);
        pword = findViewById(R.id.edt_logLogin);

        clkalbe_tvw.setOnClickListener(v -> {
            Intent intent = new Intent(login.this,registration.class);
            startActivity(intent);
            finish();
        });

        btn_login = findViewById(R.id.btn_logLogin);
        btn_login.setOnClickListener(v -> {
            usr=uname.getText().toString();
            pas = pword.getText().toString();
        if(usr.isEmpty() || pas.isEmpty())
        {
            Toast.makeText(login.this,"Field Cannot be Empty",Toast.LENGTH_SHORT).show();
            btn_login.setClickable(true);
        }else {
            btn_login.setClickable(false);
           sendUrl();
            btn_login.setClickable(true);
        }
        });

        //fullscreen code
        deroc = getWindow().getDecorView();
        deroc.setOnSystemUiVisibilityChangeListener(visibility -> {
            if(visibility == 0){
                deroc.setSystemUiVisibility(hideSystembars());
            }
        });
    }///onCreate end
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            deroc.setSystemUiVisibility(hideSystembars());
        }

    }

    private int hideSystembars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }
    private void sendUrl(){

        StringRequest stringRequest  = new StringRequest(Request.Method.POST, insertionUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    String success = jobj.getString("success");
                    JSONArray sad = jobj.getJSONArray("user_info");
                        if (success.equals("1")) {
                        for (int i =0;  i < jobj.length()-1; i++) {
                            JSONObject sads = sad.getJSONObject(i);
                             usr_id = sads.getString("usr_id");
                             usr_code = sads.getString("usr_code");
                             usr_fullname = sads.getString("usr_fullname");
                             usr_cpnumber = sads.getString("usr_cpnumber");
                             usr_address = sads.getString("usr_address");
                             usr_birthdate = sads.getString("usr_birthdate");
                             usr_emailadd = sads.getString("usr_emailadd");
                             usr_username = sads.getString("usr_username");
                             usr_password = sads.getString("usr_password");
                             usr_imageUrl= sads.getString("usr_imageUrl");

                        }

                        Toast.makeText(login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(login.this, dashboard.class);
                            intent.putExtra("usr_id",usr_id);
                            intent.putExtra("usr_code",usr_code);
                            intent.putExtra("usr_fullname",usr_fullname);
                            intent.putExtra("usr_cpnumber",usr_cpnumber);
                            intent.putExtra("usr_address",usr_address);
                            intent.putExtra("usr_emailadd",usr_emailadd);
                            intent.putExtra("usr_username",usr_username);
                            intent.putExtra("usr_password",usr_password);
                            intent.putExtra("usr_imageUrl",usr_imageUrl);
                            startActivity(intent);
                            finish();

                    } else {
                        Toast.makeText(login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(login.this, "000WEBHOST Error Occured" + e, Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(login.this,  "000WEBHOST Error:"+ error.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
        }){
            public  Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String,String>();
                params.put("usr_username",usr);
                params.put("usr_password",pas);
                return params;
            }
        };
        requestQueue= Volley.newRequestQueue(login.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
        requestQueue.add(stringRequest);
    }
}