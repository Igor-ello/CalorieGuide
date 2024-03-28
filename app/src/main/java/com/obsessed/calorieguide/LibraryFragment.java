package com.obsessed.calorieguide;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.obsessed.calorieguide.retrofit.FoodCall;
import com.obsessed.calorieguide.retrofit.FoodCallback;

public class LibraryFragment extends Fragment implements FoodCallback {
    public LibraryFragment() {
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
        return inflater.inflate(R.layout.fragment_library, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("mlg", "Libra " + String.valueOf(this.hashCode()));

        NavBarFragment nvb = new NavBarFragment(view);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_nav_bar, nvb);
        fragmentTransaction.commit();

        requireActivity().runOnUiThread(() -> {
            FoodCall foodCall = new FoodCall(this); // Передаем экземпляр FoodCallback в конструктор FoodCall
            foodCall.getFoodById(2);
        });
    }

    @Override
    public void onFoodReceived(String foodName) {
        // Обновляем UI с полученным foodName
        TextView textView = requireView().findViewById(R.id.text);
        textView.setText(foodName);
    }
}