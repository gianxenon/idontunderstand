package com.example.lmtapp;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class dashbboard_fragment extends Fragment {
    private onFragmentBtnSelected listener;
    ViewFlipper viewFlipper ;
    ListView listView;
    public static ArrayList<ListPojos> list= new ArrayList<ListPojos>();;
    MyAdapter adapaterList;
    Spinner regQ_spinner1;
    String ofChoice = "";
    ListPojos listPojosa;
    ImageView cred;

    private  String insertionUrl = "https://hellorandroid.000webhostapp.com/android_phpcon/fecth.php";
    private RequestQueue requestQueue;
    private static final String TAG= registration.class.getSimpleName();
    int success;
    private  String TAG_SUCCESS = "success";
    private  String TAG_MESSAGE = "message";
    private  String tag_json_obj= "json_obj_req";

    String usr_id ,usr_code,usr_fullname,usr_cpnumber,usr_address,usr_birthdate,usr_emailadd,usr_username,usr_password;

    public dashbboard_fragment(String usr_id, String usr_code, String usr_fullname, String usr_cpnumber, String usr_address, String usr_birthdate, String usr_emailadd, String usr_username, String usr_password) {
        this.usr_id = usr_id;
        this.usr_code = usr_code;
        this.usr_fullname = usr_fullname;
        this.usr_cpnumber = usr_cpnumber;
        this.usr_address = usr_address;
        this.usr_birthdate = usr_birthdate;
        this.usr_emailadd = usr_emailadd;
        this.usr_username = usr_username;
        this.usr_password = usr_password;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashbboard_fragment,container,false);

        cred = view.findViewById(R.id.imageView4);
        cred.setOnClickListener(v -> listener.onButtonSelected());
        regQ_spinner1=view.findViewById(R.id.spinner);
        int[] images = {R.drawable.ad1 ,R.drawable.ad2, R.drawable.ad3,R.drawable.ad4};
        viewFlipper = view.findViewById(R.id.v_flipper);
        for(int image : images){
            flipper_image(image);
        }
        //spinner
        final ArrayList<String> choice = new ArrayList<>();
        choice.add("CREDITORS");
        choice.add("DEBTORS");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, choice);
        regQ_spinner1.setAdapter(adapter);
        regQ_spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ofChoice = choice.get(position);
               // Toast.makeText(getContext().getApplicationContext(),"you selected " + ofChoice,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//end of spinner




        listView = view.findViewById(R.id.list_view);


        adapaterList = new MyAdapter(getActivity(), list);
        listView.setAdapter(adapaterList);
        if(list.size() == 0){
            //listener.onButtonSelected();
        }
        listShow();
        return  view;
    }//oncreate


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof onFragmentBtnSelected){

            listener = (onFragmentBtnSelected) context;
        }else {
            throw  new ClassCastException(context.toString() + "must implement listener");
        }
    }

    public interface onFragmentBtnSelected{
        void onButtonSelected();
    }


    public void flipper_image(int image) {
        ImageView imageView = new ImageView(getActivity());
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(getActivity(), android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getActivity(), android.R.anim.slide_out_right);
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
                 usr_id = sads.getString("usr_id");
                 usr_code = sads.getString("usr_code");
                 usr_fullname = sads.getString("usr_fullname");
                 usr_cpnumber = sads.getString("usr_cpnumber");
                 usr_address = sads.getString("usr_address");
                 usr_birthdate = sads.getString("usr_birthdate");
                 usr_emailadd = sads.getString("usr_emailadd");
                 listPojosa = new ListPojos(usr_fullname,usr_cpnumber,R.drawable.profilepic);
                 list.add(listPojosa);
                 adapaterList.notifyDataSetChanged();

             }
         } else {
             Toast.makeText(getContext().getApplicationContext(), "error Fetching data for list", Toast.LENGTH_SHORT).show();
         }
     } catch (Exception e) {
         Toast.makeText(getContext().getApplicationContext(), "Fetching in database error" + e, Toast.LENGTH_LONG).show();
     }
 }, (VolleyError error) -> {
     Toast.makeText(getContext().getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
 });
        requestQueue= Volley.newRequestQueue(getContext().getApplicationContext());
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
        requestQueue.add(stringRequest);
    }
    }
