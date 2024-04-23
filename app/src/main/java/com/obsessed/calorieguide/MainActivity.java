package com.obsessed.calorieguide;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.save.ShPrefs;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ShPrefs shPrefs = new ShPrefs();
        shPrefs.saveUser(Data.getInstance().getUser(), this);
    }
}