package com.example.lmtapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class creditors_uis extends Fragment {
   //private static final String TAG = "creditors_uis";
    private creditors_uis.onchoice listeners;
    ListView listView;
    public static ArrayList<ListPojos> list= new ArrayList<>();
    MyAdapter adapaterList;

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


        adapaterList = new MyAdapter(getActivity(), list);
        listView.setAdapter(adapaterList);



        listShow();

        imageView.setOnClickListener(v -> {

            dialog_custom dialog_customs = new dialog_custom(lenders_id,lenders_code);
            dialog_customs.show(getFragmentManager(),"dialog_custom");

    });

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
                            usr_imageUrl = sads.getString("usr_imageUrl");
                            listPojosa = new ListPojos(deb_fn,deb_cpnum,usr_imageUrl  );
                            list.add(listPojosa);
                            adapaterList.notifyDataSetChanged();
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
                    params.put("usr_code", lenders_code);

                    return params;
                }
            };
            requestQueue= Volley.newRequestQueue(getContext());
          stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
            requestQueue.add(stringRequest);
        }
    }


