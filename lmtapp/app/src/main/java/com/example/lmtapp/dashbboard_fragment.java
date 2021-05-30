package com.example.lmtapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class dashbboard_fragment extends Fragment  {
    private  onFragmentBtnSelected listener;
    ViewFlipper viewFlipper ;
    ListView listView;
    public static ArrayList<ListPojos> list= new ArrayList<>();
    MyAdapter adapaterList;
    Spinner regQ_spinner1;
    String ofChoice = "";
    ListPojos listPojosa;
    ImageView cred,debt;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    //String temp = "";
   // String cred_codess = "0";
    private  String fetchingUrl = "https://hellorandroid.000webhostapp.com/android_phpcon/fecth.php";
    private RequestQueue requestQueue;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;


    String usr_id ,usr_code,usr_fullname,usr_cpnumber,usr_address,usr_birthdate,usr_emailadd,usr_username,usr_password,usr_imageUrl;

    public dashbboard_fragment(String usr_id, String usr_code, String usr_fullname, String usr_cpnumber, String usr_address, String usr_birthdate, String usr_emailadd, String usr_username, String usr_password,String usr_imageUrl) {
        this.usr_id = usr_id;
        this.usr_code = usr_code;
        this.usr_fullname = usr_fullname;
        this.usr_cpnumber = usr_cpnumber;
        this.usr_address = usr_address;
        this.usr_birthdate = usr_birthdate;
        this.usr_emailadd = usr_emailadd;
        this.usr_username = usr_username;
        this.usr_password = usr_password;
        this.usr_imageUrl = usr_imageUrl;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashbboard_fragment,container,false);




        //creditors View
        cred = view.findViewById(R.id.imageView4);
        cred.setOnClickListener(v -> listener.onButtonSelected());
        //end of method => creditors View

        //debtors View
        debt = view.findViewById(R.id.imageView5);
        debt.setOnClickListener(v -> {
            fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new deb_view());
            fragmentTransaction.addToBackStack(null).commit();

        });
        //end of method => debtors View

        //images (advertisment)
        regQ_spinner1=view.findViewById(R.id.spinner);
        int[] images = {R.drawable.ad1 ,R.drawable.ad2, R.drawable.ad3,R.drawable.ad4};
        viewFlipper = view.findViewById(R.id.v_flipper);
        for(int image : images){
            flipper_image(image);
        }
        //end images (advertisment)

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



        //getting data for rating listView
        listView = view.findViewById(R.id.list_view);
        adapaterList = new MyAdapter(getActivity(), list);
        listView.setAdapter(adapaterList);
        listShow();
        //end of getting data for rating listView

        //method to check the user in rating list
        BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment,new deb_view());
                fragmentTransaction.addToBackStack(null).commit();
            }
        };
        Objects.requireNonNull(getContext()).registerReceiver(mBroadcastReceiver, new IntentFilter("call.myfragment.action")); // This code is in your Activity will be in onCreate() or in onResume() method.
    //end of method to check the user in rating list

        return  view;
    }//oncreate

//creditors view method  connected to dashboard.java
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof onFragmentBtnSelected){

            listener = (onFragmentBtnSelected) context;
        }else {
            throw  new ClassCastException(context.toString() + "must implement listener");
        }
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                //Toast.makeText(getContext(), "back pressed", Toast.LENGTH_SHORT).show();
                if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
                 new exitDialogFragment().show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(),"exitDialogFragment");
                } else {
                    Toast.makeText(getContext(), "Press once again to exit!",
                            Toast.LENGTH_SHORT).show();
                }
                back_pressed = System.currentTimeMillis();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public interface onFragmentBtnSelected{
        void onButtonSelected();
    }
    //end => creditors view method  connected to dashboard.java

    //ads
    public void flipper_image(int image) {
        ImageView imageView = new ImageView(getActivity());
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(getActivity(), android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getActivity(), android.R.anim.slide_out_right);
    }
    //ads

    //fetching  data -> list of user in ratings
    private void listShow() {
        StringRequest stringRequest  = new StringRequest(Request.Method.POST, fetchingUrl, response -> {
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
                 usr_imageUrl = sads.getString("usr_imageUrl");
                 listPojosa = new ListPojos(usr_fullname,usr_cpnumber,usr_imageUrl);
                 list.add(listPojosa);
                 adapaterList.notifyDataSetChanged();

             }
         } else {
             Toast.makeText(getContext(), "error Fetching data for list", Toast.LENGTH_SHORT).show();
         }
     } catch (Exception e) {
         Toast.makeText(getContext(), "Fetching in database error" + e, Toast.LENGTH_LONG).show();
     }
 }, (VolleyError error) -> Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show());
        requestQueue= Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
        requestQueue.add(stringRequest);
    }//end of listShow() =>fetching data for rating list




    }//end of class
