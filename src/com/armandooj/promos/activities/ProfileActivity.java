package com.armandooj.promos.activities;

import com.armandooj.promos.R;
import com.parse.ParseUser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProfileActivity extends Fragment {
	
	private ParseUser currentUser;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			// setup everything
		} else {
			
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View root = inflater.inflate(R.layout.profile_activity, null);
		// Button button = (Button) root.findViewById(R.id.login_button);
		// button.setOnClickListener(loginListener);
		
		return root;
	}	

}
