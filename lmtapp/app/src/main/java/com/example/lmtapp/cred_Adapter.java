package com.example.lmtapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentViewHolder;

import java.util.ArrayList;

import static com.example.lmtapp.R.layout.loan_tableview;

public class cred_Adapter extends BaseAdapter {
    public Context context;
    public ArrayList<cred_listDatageter> listDatageters;

    public cred_Adapter(Context context, ArrayList<cred_listDatageter> listDatageter) {
        this.context = context;
        this.listDatageters = listDatageter;
    }

    @Override
    public int getCount() {
        return listDatageters.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(this.context).inflate(loan_tableview,parent,false);

        TextView row1 =  convertView.findViewById(R.id.txt_rows1);
        TextView  row2 = convertView.findViewById(R.id.txt_rows2);
        TextView  row3 = convertView.findViewById(R.id.txt_rows3);
        TextView  row4 = convertView.findViewById(R.id.txt_rows4);
        TextView  row5 =  convertView.findViewById(R.id.txt_rows5);
        TextView  row6 = convertView.findViewById(R.id.txt_rows6);
        row1.setText(listDatageters.get(position).getRow1());
        row2.setText(listDatageters.get(position).getRow2());
        row3.setText(listDatageters.get(position).getRow3());
        row4.setText(listDatageters.get(position).getRow4());
        row5.setText(listDatageters.get(position).getRow5());
       row6.setText(listDatageters.get(position).getRow6());

        return convertView;
    }
}
