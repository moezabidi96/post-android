package com.example.mini_projet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.mini_projet.fragments.account;
import com.example.mini_projet.fragments.home;
import com.example.mini_projet.fragments.save;
import com.example.mini_projet.fragments.search;
import com.google.firebase.auth.FirebaseAuth;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Page_Principal extends AppCompatActivity {
    ChipNavigationBar chipNavigationBar;
    Fragment fragment = new home();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_principal_page);




        chipNavigationBar = findViewById(R.id.bottom_nav_menu);
        chipNavigationBar.setItemSelected(R.id.bottom_nav_home,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        bottomMenu();

    }

    private void bottomMenu() {


        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {

                switch (i) {
                    case R.id.bottom_nav_home:

                        fragment = new home();
                        break;
                    case R.id.bottom_nav_search:
                        fragment = new search();
                        break;
                    case R.id.bottom_nav_save:
                        fragment = new save();
                        break;
                    case R.id.bottom_nav_account:




                        fragment = new account();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });


    }


}