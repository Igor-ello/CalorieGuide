package com.obsessed.calorieguide.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.obsessed.calorieguide.R;

public class NavBarFragment extends Fragment {

	private final View viewFragment;

	public NavBarFragment(View viewFragment) {
		this.viewFragment = viewFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_nav_bar, container, false);
		NavController navController = Navigation.findNavController(viewFragment);

		view.findViewById(R.id.home_icon).setOnClickListener(v -> {
			navController.popBackStack(R.id.mainFragment, true);
			navController.navigate(R.id.mainFragment);
			Log.d("nav", "Переход на mainFragment");
		});

		view.findViewById(R.id.profile_icon).setOnClickListener(v -> {
			navController.popBackStack(R.id.mainFragment, false);
			navController.navigate(R.id.profileFragment);
			Log.d("nav", "Переход на profileFragment");
		});

		view.findViewById(R.id.recipe_icon).setOnClickListener(v -> {
			navController.popBackStack(R.id.mainFragment, false);
			navController.navigate(R.id.libraryFragment);
			Log.d("nav", "Переход на libraryFragment");
		});

		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}
}