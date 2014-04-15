package com.armandooj.promos.activities;

import com.armandooj.promos.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ProfileActivity extends Fragment {
	
	private ParseUser currentUser;
	
	RelativeLayout loginContainer;
	LinearLayout userContainer;
	TextView userName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View root = inflater.inflate(R.layout.profile_activity, null);
		
		// logout button
		((Button) root.findViewById(R.id.btn_logout)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				logout();
			}
		});
		
		loginContainer = (RelativeLayout) root.findViewById(R.id.login_container);
		userContainer = (LinearLayout) root.findViewById(R.id.user_container);
		userName = (TextView) root.findViewById(R.id.name);
		
		currentUser = ParseUser.getCurrentUser();
		
		// login form
		((Button) root.findViewById(R.id.btn_login)).setOnClickListener(loginListener);
		
		// show the proper view 
		if (currentUser != null) {
			// setup everything and hide login form			
			loginContainer.setVisibility(View.GONE); 	
			userName.setText(currentUser.getUsername());
		} else {
			// hide user's info container			
			userContainer.setVisibility(View.GONE);
		}
		
		return root;
	}
	
	View.OnClickListener loginListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View view) {
			final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", getString(R.string.cargando), true);
			
			ParseFacebookUtils.logIn(getActivity(), new LogInCallback() {
				
				@Override
				public void done(ParseUser user, ParseException err) {				
					dialog.dismiss();

				    if (user == null) {
				      // Uh oh. The user cancelled the Facebook login.
				    } else {
				    	// either user signed in or logged in, proceed
				    	loginContainer.setVisibility(View.GONE);
						userContainer.setVisibility(View.VISIBLE);
						userName.setText(user.getUsername());
				    }
				}
			});
		}
	};
		
	public void logout() {
		ParseUser.logOut();
		loginContainer.setVisibility(View.VISIBLE);
		userContainer.setVisibility(View.GONE);
		userName.setText(getText(R.string.anonimo));
	}

}
