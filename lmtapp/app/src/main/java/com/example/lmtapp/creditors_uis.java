package com.example.lmtapp;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

import java.util.ArrayList;

public class creditors_uis extends Fragment {
   //private static final String TAG = "creditors_uis";
    private creditors_uis.onchoice listeners;
    ListView listView;
    public static ArrayList<ListPojos> list= new ArrayList<ListPojos>();
    MyAdapter adapaterList;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ImageView imageView;

    //database
    private  String insertionUrl = "https://hellorandroid.000webhostapp.com/android_phpcon/fecth.php";
    private RequestQueue requestQueue;
    private static final String TAG= creditors_uis.class.getSimpleName();
    int success;
    private  String TAG_SUCCESS = "success";
    private  String TAG_MESSAGE = "message";
    private  String tag_json_obj= "json_obj_req";
    ListPojos listPojosa;
    String usr_id ,usr_code,usr_fullname,usr_cpnumber,usr_address,usr_birthdate,usr_emailadd,usr_username,usr_password;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creditors_uis,container,false);

        imageView = view.findViewById(R.id.img_fltbtn);
        listView = view.findViewById(R.id.list_view);





        adapaterList = new MyAdapter(getActivity(), list);
        listView.setAdapter(adapaterList);

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
                            Toast.makeText(getContext().getApplicationContext(), "Success Fetching data for list", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext().getApplicationContext(), "error Fetching data for list", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext().getApplicationContext(), "Fetching in database error" + e, Toast.LENGTH_LONG).show();
                }
            }, (VolleyError error) -> {
                Toast.makeText(getContext().getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            });
            requestQueue= Volley.newRequestQueue(getContext().getApplicationContext());
         //   stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
            requestQueue.add(stringRequest);
        }
    }

