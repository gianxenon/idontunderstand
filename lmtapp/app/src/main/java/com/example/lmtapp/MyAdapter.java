package com.example.lmtapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public Context context;
    public ArrayList<ListPojos> listpojos;
    public MyAdapter(Context context, ArrayList<ListPojos> listpojos){
        this.context = context;
        this.listpojos = listpojos;
    }

    @Override
    public int getCount() {
        return listpojos.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.custom_list,parent,false);

        TextView title = (TextView) convertView.findViewById(R.id.txt_name);
        TextView  description =  convertView.findViewById(R.id.txt_numvWing);
        ImageView imageView = convertView.findViewById(R.id.img_vCs);
        title.setText(listpojos.get(position).getTitle());
        description.setText(listpojos.get(position).getDescription());
        String url = "https://hellorandroid.000webhostapp.com/android_phpcon/Image/" + listpojos.get(position).getImages();
        Glide.with(convertView.getContext().getApplicationContext()).load(url).into(imageView);
      //  imageView.setImageResource(listpojos.get(position).getImages());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.sendBroadcast(new Intent("call.myfragment.action"));

                Toast.makeText(context, "position " + position, Toast.LENGTH_SHORT).show();

            }
        });

        return convertView;
    }
}
