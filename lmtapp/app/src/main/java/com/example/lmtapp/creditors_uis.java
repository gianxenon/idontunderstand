package com.example.lmtapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class creditors_uis extends Fragment {
   //private static final String TAG = "creditors_uis";
    private creditors_uis.onchoice listeners;
    ListView listView;
    public static ArrayList<ListPojos> list= new ArrayList<>();
    cred_uiAdapter adapaterList;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ImageView imageView;

    //database
    private  String insertionUrl = "https://hellorandroid.000webhostapp.com/android_phpcon/debtable.php";
    private RequestQueue requestQueue;
    private static final String TAG= creditors_uis.class.getSimpleName();


    ListPojos listPojosa;
    String deb_fn ,deb_cpnum,deb_emls,deb_adrs,usr_code,deb_code,typeofterm,term_len,interest,prin_amount,usr_imageUrl;
    String lenders_id,lenders_code;

    public creditors_uis(String lenders_id, String lenders_code) {
        this.lenders_code = lenders_code;
        this.lenders_id = lenders_id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creditors_uis,container,false);

        imageView = view.findViewById(R.id.img_fltbtn);
        listView = view.findViewById(R.id.list_view);


        adapaterList = new cred_uiAdapter(getActivity(), list);
        listView.setAdapter(adapaterList);



        listShow(lenders_code);
        adapaterList.notifyDataSetChanged();
        imageView.setOnClickListener(v -> {

            dialog_custom dialog_customs = new dialog_custom(lenders_id,lenders_code);
            dialog_customs.show(getFragmentManager(),"dialog_custom");

    });
        //method to check the user in rating list (use to clicked items on the list )
        BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
             String deb_code =   intent.getExtras().getString("code");

                fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment,new cred_view(deb_code,lenders_code));
                fragmentTransaction.addToBackStack(TAG).commit();
            }
        };
        Objects.requireNonNull(getContext()).registerReceiver(mBroadcastReceiver, new IntentFilter("call.cred_uis.action")); // This code is in your Activity will be in onCreate() or in onResume() method.
        //end of method to check the user in rating list
        return view;
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


    private void listShow(String lenders_code) {

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
                            deb_code = sads.getString("deb_code");
                            deb_cpnum = sads.getString("deb_cpnum");
                            usr_imageUrl = sads.getString("usr_imageUrl");
                            listPojosa = new ListPojos(deb_fn,deb_cpnum,usr_imageUrl ,deb_code);
                            list.add(listPojosa);
                            adapaterList.notifyDataSetChanged();
                            Log.d("credchks2",response);
                            }
                        Toast.makeText(getContext(), "Success Fetching data for list", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "You don't have Debtors yet ", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Fetching in database error" + e, Toast.LENGTH_LONG).show();
                }
            }, (VolleyError error) -> Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show()){
                public Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();

                    params.put("lenders_code", lenders_code);
                    Log.d("credchks",lenders_code);
                    return params;
                }
            };
            requestQueue= Volley.newRequestQueue(getContext());
          stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
            requestQueue.add(stringRequest);
        }
    }


