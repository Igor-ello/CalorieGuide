package com.obsessed.calorieguide;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.tools.save.ShPrefs;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNV);
        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ShPrefs.saveData(Data.getInstance().getUser(),
                Data.getInstance().getAdapterType(),
                this);
        Log.d("Lifecycle", "onStop: User saved to SharedPreferences");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShPrefs.saveData(Data.getInstance().getUser(),
                Data.getInstance().getAdapterType(),
                this);
        Log.d("Lifecycle", "onDestroy: User saved to SharedPreferences");
    }
}