package com.obsessed.calorieguide;

import static com.obsessed.calorieguide.save.ShPrefs.loadData;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.obsessed.calorieguide.data.Data;


public class MainFragment extends Fragment {

    public MainFragment() {
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
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadData(requireContext());

        // Проверка пользователя на наличие авторизации
        if(checkUserLogin(view))
            setupNavBarFragment(view);

        view.findViewById(R.id.arrow_back).setOnClickListener(v -> {
            // При выходе из аккаунта устанавливаем значение пользователя в null
            Data.getInstance().setUser(null);
            Navigation.findNavController(view).popBackStack();
            Navigation.findNavController(view).navigate(R.id.loginFragment);
        });
    }

    private boolean checkUserLogin(View view) {
        Log.d("MainFragment", "Checking user login status");
        if (Data.getInstance().getUser() == null) {
            Log.d("MainFragment", "User not logged in, navigating to login fragment");
            Navigation.findNavController(view).popBackStack();
            Navigation.findNavController(view).navigate(R.id.loginFragment);
            return false;
        } else {
            Log.d("MainFragment", "User logged in");
            return true;
        }
    }

    private void setupNavBarFragment(View view) {
        Log.d("MainFragment", "Setting up navigation bar fragment");
        NavBarFragment nvb = new NavBarFragment(view);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_nav_bar, nvb);
        fragmentTransaction.commit();
    }
}