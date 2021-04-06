package com.example.lmtapp;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyProcessAdapter extends BaseAdapter {
    public Context context;
    public  ArrayList<procdata_list> procdataLists;

    public MyProcessAdapter(Context context, ArrayList<procdata_list> procdataLists) {
        this.context = context;
        this.procdataLists = procdataLists;
    }

    @Override
    public int getCount() {
        return procdataLists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(this.context).inflate(R.layout.loan_proc_list,parent,false);
        TextView  row1 = (TextView) convertView.findViewById(R.id.txt_row1);
        TextView  row2 = (TextView) convertView.findViewById(R.id.txt_row2);
        TextView  row3 = (TextView) convertView.findViewById(R.id.txt_row3);
        TextView  row4 = (TextView) convertView.findViewById(R.id.txt_row4);
        TextView  row5 = (TextView) convertView.findViewById(R.id.txt_row5);
        row1.setText(procdataLists.get(position).getRow1());
        row2.setText(procdataLists.get(position).getRow2());
        row3.setText(procdataLists.get(position).getRow3());
        row4.setText(procdataLists.get(position).getRow4());
        row5.setText(procdataLists.get(position).getRow5());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Position"+ position,Toast.LENGTH_SHORT).show();

            }
        });
        return convertView;
    }
}
