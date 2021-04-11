package com.example.lmtapp;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class creditors_uis extends Fragment {
   //private static final String TAG = "creditors_uis";
    private creditors_uis.onchoice listeners;
    ListView listView;
    public static ArrayList<ListPojos> list= new ArrayList<ListPojos>();
    MyAdapter adapaterList;
    static FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ImageView imageView;

    String temp1 = "";
    String cred_codes;
    //database
    private  String insertionUrl = "https://hellorandroid.000webhostapp.com/android_phpcon/debtable.php";
    private RequestQueue requestQueue;
    private static final String TAG= creditors_uis.class.getSimpleName();
    int success;
    private  String TAG_SUCCESS = "success";
    private  String TAG_MESSAGE = "message";
    private  String tag_json_obj= "json_obj_req";
    int pos =0;
    String name ="";
    String deb_num="";
    ListPojos listPojosa;
    String deb_fn ,deb_cpnum,deb_emls,deb_adrs,usr_code,deb_code,typeofterm,term_len,interest,prin_amount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creditors_uis,container,false);

        imageView = view.findViewById(R.id.img_fltbtn);
        listView = view.findViewById(R.id.list_view);

        try {
            FileInputStream fin = getActivity().openFileInput("file.txt");
            int c;

            while( (c = fin.read()) != -1){
                temp1 = temp1 + (char) c;
            }

            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        cred_codes = temp1;

        adapaterList = new MyAdapter(getActivity(), list);
        listView.setAdapter(adapaterList);
        adapaterList.setOnAddListener(new OnAddListener() {
            @Override
            public void onAdd(int position, String name, String number) {

                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment,new cred_view(name,number)).addToBackStack(TAG);
                fragmentTransaction.commit();

            }
        });
     //   if(list.size() == 0){
          //  listeners.onchoices();

  //  }
        listShow();

        imageView.setOnClickListener(v -> {

            dialog_custom dialog_customs = new dialog_custom();
            dialog_customs.show(getFragmentManager(),"dialog_custom");

    });

        return view;
    }

    public interface OnAddListener {
        public void onAdd(int position, String name,String number);
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof creditors_uis.onchoice){

            listeners = (creditors_uis.onchoice) context;
        }else {
            throw  new ClassCastException(context.toString() + "must implement listener");
        }
    }

    public interface onchoice{
        void onchoices();

    }


    private void listShow() {

            StringRequest stringRequest  = new StringRequest(Request.Method.POST, insertionUrl, response -> {
                list.clear();
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
                            listPojosa = new ListPojos(deb_fn,deb_cpnum,R.drawable.profilepic);
                            list.add(listPojosa);
                            adapaterList.notifyDataSetChanged();
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
                    params.put("usr_code", cred_codes);
                    Log.d("codes",cred_codes);
                    return params;
                }
            };
            requestQueue= Volley.newRequestQueue(getContext().getApplicationContext());
         //   stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
            requestQueue.add(stringRequest);
        }
    }


