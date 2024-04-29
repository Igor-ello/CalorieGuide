package com.obsessed.calorieguide;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

public class MainActivityAuth extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_auth);

        NavController navController = Navigation.findNavController(this, R.id.navHostFragmentAuth);
    }
}