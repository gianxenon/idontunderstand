package com.example.lmtapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class cred_Adapter extends BaseAdapter {
    public Context context;
    public ArrayList<cred_listDatageter> listDatageters;

    public cred_Adapter(Context context, ArrayList<cred_listDatageter> listDatageters) {
        this.context = context;
        this.listDatageters = listDatageters;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(this.context).inflate(R.layout.loan_tableview,parent,false);
        TextView row1 = (TextView) convertView.findViewById(R.id.textView14);
        TextView  row2 = (TextView) convertView.findViewById(R.id.textView16);
        TextView  row3 = (TextView) convertView.findViewById(R.id.textView18);
        TextView  row4 = (TextView) convertView.findViewById(R.id.textView20);
        TextView  row5 = (TextView) convertView.findViewById(R.id.textView27);
        TextView  row6 = (TextView) convertView.findViewById(R.id.textView28);
        row1.setText(listDatageters.get(position).getRow1());
        row2.setText(listDatageters.get(position).getRow2());
        row3.setText(listDatageters.get(position).getRow3());
        row4.setText(listDatageters.get(position).getRow4());
        row5.setText(listDatageters.get(position).getRow5());
        row6.setText(listDatageters.get(position).getRow6());

        return convertView;
    }
}
