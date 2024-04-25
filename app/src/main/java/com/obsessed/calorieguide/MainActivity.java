package com.obsessed.calorieguide;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

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