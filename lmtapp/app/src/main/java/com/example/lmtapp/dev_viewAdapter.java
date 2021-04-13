package com.example.lmtapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class dev_viewAdapter extends BaseAdapter {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public Context context;
    public ArrayList<ListPojos> listpojos;
    public dev_viewAdapter(Context context, ArrayList<ListPojos> listpojos){
        this.context = context;
        this.listpojos = listpojos;
    }

    private deb_view.OnAddListeners onAddListeners;
    public void setOnAddListeners(deb_view.OnAddListeners onAddListeners) {
        this.onAddListeners = onAddListeners;
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
        imageView.setImageResource(listpojos.get(position).getImages());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddListeners.onAdds(position, title.getText().toString(),description.getText().toString());
            }
        });

        return convertView;
    }


}