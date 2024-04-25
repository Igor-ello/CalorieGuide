package com.obsessed.calorieguide;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.obsessed.calorieguide.data.Data;

public class SettingsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);

        view.findViewById(R.id.arrow_back).setOnClickListener(v -> {
            navController.popBackStack();
        });

        //Switch
        Switch swAdapterType = view.findViewById(R.id.swAdapterType);
        if (swAdapterType != null) {
            if (Data.getInstance().getAdapterType() == 1) {
                swAdapterType.setChecked(false);
            } else {
                swAdapterType.setChecked(true);
            }
            swAdapterType.setOnCheckedChangeListener(this);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) { // Переключатель включен
            Data.getInstance().setAdapterType(2);
            Log.d("Switch", "Switch is checked");
        } else { // Переключатель выключен
            Data.getInstance().setAdapterType(1);
            Log.d("Switch", "Switch is not checked");
        }
    }
}