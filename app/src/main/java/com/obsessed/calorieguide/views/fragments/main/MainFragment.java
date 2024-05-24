package com.obsessed.calorieguide.views.fragments.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.obsessed.calorieguide.MainActivityApp;
import com.obsessed.calorieguide.MainActivityAuth;
import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.data.callback.user.CallbackRefreshDay;
import com.obsessed.calorieguide.data.local.Data;
import com.obsessed.calorieguide.data.local.load.LoadRemoteData;
import com.obsessed.calorieguide.databinding.FragmentMainBinding;
import com.obsessed.calorieguide.data.local.load.CallbackLoadData;
import com.obsessed.calorieguide.data.local.load.ShPrefs;


public class MainFragment extends Fragment implements CallbackLoadData, CallbackRefreshDay {
    FragmentMainBinding binding;

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
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        BottomNavigationView botNavView = getActivity().findViewById(R.id.bottomNV);
        if (botNavView != null) {
            botNavView.setVisibility(view.VISIBLE);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentMainBinding.bind(view);

        // Загрузка данных из хранилища
        ShPrefs.loadData(requireContext(), this);

        view.findViewById(R.id.btLoop).setOnClickListener(v -> {
            LoadRemoteData.getInstance(requireContext()).loadAll();
        });
    }

    private boolean checkUserLogin() {
        Log.d("MainFragment", "Checking user login status");
        if (Data.getInstance().getUser() == null) {
            Log.d("MainFragment", "User not logged in, navigating to login fragment");
            if (getActivity() != null && getActivity() instanceof MainActivityApp) {
                startActivity(new Intent(getActivity(), MainActivityAuth.class));
                getActivity().finish();
            }
            return false;
        } else {
            Log.d("MainFragment", "User logged in");
            return true;
        }
    }

    @Override
    public void onLoadData() {
        Log.d("MainFragment", "Loading data");
        // Проверка пользователя на наличие авторизации
        if(checkUserLogin()) {
            requireActivity().runOnUiThread(()  -> {
                // Инициализация для отображения данных
                Stats.getInstance().init(binding, requireActivity());
                Stats.getInstance().update();
                Intakes.getInstance().init(binding, requireContext());
            });
        }
    }

    @Override
    public void onRefreshDay() {
        Log.d("MainFragment", "Refreshing data" + Data.getInstance().getDay().toString());
        onLoadData();
    }

}