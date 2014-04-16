package com.armandooj.promos.activities;

import com.armandooj.promos.R;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.google.gson.Gson;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends Fragment {
	
	private ParseUser currentUser;
	
	RelativeLayout loginContainer;
	LinearLayout userContainer;
	TextView userName;
	ProfilePictureView userImage;
	
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
		userImage = (ProfilePictureView) root.findViewById(R.id.profile_image);
		
		currentUser = ParseUser.getCurrentUser();
		
		// login form
		((Button) root.findViewById(R.id.btn_login)).setOnClickListener(loginListener);
		
		// show the proper view 
		if (currentUser != null) {
			// setup everything and hide login form			
			loginContainer.setVisibility(View.GONE); 	
			
			// get the user's graph from cache or Internet
			Session session = ParseFacebookUtils.getSession();
			if (session != null && session.isOpened()) {
				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
				if (preferences.contains("user")) {
					FacebookUser fbUser = new Gson().fromJson(preferences.getString("user", ""), FacebookUser.class);
					fillUserUI(fbUser);
				} else {
					makeMeRequest();	
				}												
			}			
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
						
						makeMeRequest();
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
		userImage.setProfileId(null);
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		SharedPreferences.Editor editor = preferences.edit();
		editor.remove("user");
		editor.commit();
	}
	
	public void makeMeRequest() {
	    Request request = Request.newMeRequest(ParseFacebookUtils.getSession(),
            new Request.GraphUserCallback() {
                
	    		@Override
                public void onCompleted(GraphUser user, Response response) {
                    if (user != null) {
                    	FacebookUser fbUser = new FacebookUser();
                    	fbUser.facebookId = user.getId();
                    	fbUser.name = user.getName();
                    	// fbUser.location = (String) user.getLocation().getProperty("name");
                    	
                    	fillUserUI(fbUser);
                    	
                    	// save the user
                		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("user", new Gson().toJson(fbUser, FacebookUser.class));
                        editor.commit();
                    } else if (response.getError() != null) {
                        // handle error
                    	Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_LONG).show();
                    }                  
                }               
            });
	    request.executeAsync();	 
	}
	
	public void fillUserUI(FacebookUser user) {
		userName.setText(user.name);
		userImage.setProfileId(user.facebookId);
	}
	
	private class FacebookUser {
		
		public String facebookId;
		public String name;
		// public String location;
	}

}
