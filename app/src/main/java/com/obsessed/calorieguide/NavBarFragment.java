package com.obsessed.calorieguide;

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

public class NavBarFragment extends Fragment {

	private final View viewFragment;

	public NavBarFragment(View viewFragment) {
		this.viewFragment = viewFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("mlg", "onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_nav_bar, container, false);
		Log.d("mlg", "onCreateView");
		NavController navController = Navigation.findNavController(viewFragment);

		view.findViewById(R.id.home_icon).setOnClickListener(v -> {
			navController.navigate(R.id.mainFragment, null,
					new NavOptions.Builder()
							.setPopUpTo(R.id.mainFragment, true)
							.build());
			Log.d("nav", "Переход на mainFragment");
		});

		view.findViewById(R.id.profile_icon).setOnClickListener(v -> {
			navController.navigate(R.id.profileFragment, null,
					new NavOptions.Builder()
							.setPopUpTo(R.id.profileFragment, true)
							.build());
			Log.d("nav", "Переход на profileFragment");
		});

		view.findViewById(R.id.recipe_icon).setOnClickListener(v -> {
			navController.navigate(R.id.libraryFragment, null,
					new NavOptions.Builder()
							.setPopUpTo(R.id.libraryFragment, true)
							.build());

			Log.d("nav", "Переход на libraryFragment");
		});

		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Log.d("mlg", "onViewCreated");
	}
}