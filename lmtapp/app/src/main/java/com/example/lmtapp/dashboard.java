package com.example.lmtapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        dashbboard_fragment.onFragmentBtnSelected,creditors_uis.onchoice {
    private long pressedTime;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    String usr_id ,usr_code,usr_fullname,usr_cpnumber,usr_address,usr_birthdate,usr_emailadd,usr_username,usr_password;
    ImageView logdash;
    TextView  draw_txt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle =getIntent().getExtras();
        assert bundle != null;
        usr_id = bundle.getString("usr_id");
        usr_code = bundle.getString("usr_code");
        usr_fullname = bundle.getString("usr_fullname");
        usr_cpnumber = bundle.getString("usr_cpnumber");
        usr_address = bundle.getString("usr_address");
        usr_birthdate = bundle.getString("usr_birthdate");
        usr_emailadd = bundle.getString("usr_emailadd");
        usr_username = bundle.getString("usr_username");
        usr_password = bundle.getString("usr_password");


        try {
            FileOutputStream fOut = openFileOutput("file.txt",Context.MODE_PRIVATE);


            fOut.write(usr_code.getBytes());
            fOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(this);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //load default Fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment, new dashbboard_fragment( usr_id ,usr_code,usr_fullname,usr_cpnumber,usr_address,usr_birthdate,usr_emailadd,usr_username,usr_password));
        fragmentTransaction.commit();


        View hView =  navigationView.inflateHeaderView(R.layout.drawer_header);
        ImageView imgvw = (ImageView)hView.findViewById(R.id.draw_img4);
        TextView draw_txt = (TextView)hView.findViewById(R.id.drawer_txt1);
        TextView draw_txt2 = (TextView)hView.findViewById(R.id.drawer_txt2);
        draw_txt.setText(usr_fullname);
        draw_txt2.setText(usr_cpnumber);



    }//On Create

//drawer items

    @Override
    public void onBackPressed() {

        if (pressedTime + 5000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (menuItem.getItemId() == R.id.home) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new dashbboard_fragment(usr_id ,usr_code,usr_fullname,usr_cpnumber,usr_address,usr_birthdate,usr_emailadd,usr_username,usr_password));
            fragmentTransaction.commit();
        }

        if (menuItem.getItemId() == R.id.home1) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new dashbboard_fragment(usr_id ,usr_code,usr_fullname,usr_cpnumber,usr_address,usr_birthdate,usr_emailadd,usr_username,usr_password));
            fragmentTransaction.addToBackStack(null).commit();
        }
        return true;
    }


    @Override
    public void onButtonSelected() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, new creditors_uis());
        fragmentTransaction.addToBackStack(null).commit();
    }

    public void onchoices() {
/*
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment,new creditors_ui());
        fragmentTransaction.commit();
*/
    }

    public void ondialogBtnSelected(){
/*
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment,new frag_CredLoan_transProcs());
        fragmentTransaction.commit();
*/
    }
}