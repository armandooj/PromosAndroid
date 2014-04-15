package com.armandooj.promos.activities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.armandooj.promos.R;
import com.armandooj.promos.adapters.PromotionsAdapter;
import com.armandooj.promos.models.Promo;
import com.armandooj.promos.utils.Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PromotionsActivity extends Fragment implements OnItemClickListener, OnClickListener {

	ListView listView;
	ArrayList<Promo> lstVideos;
	View vw_master;
	View vw_detail;

	// detail view
	TextView tvTitle, tvPrice, tvDesc;
	ImageView img;
	ImageButton btnBack, btnLike, btnFavorite;

	// animation
	private Animation mSlideInLeft;
	private Animation mSlideOutRight;
	private Animation mSlideInRight;
	private Animation mSlideOutLeft;

	boolean _isBack = true;
	
	private Callbacks callbacks = promotionsCallbacks;
	private List<Promo> promos; 
	ProgressDialog progress;
	
	public interface Callbacks {
		public void gotPromos(List<Promo> promos);
	}

	// if the fragment is no attached to an activity
	private static Callbacks promotionsCallbacks = new Callbacks() {
		@Override
		public void gotPromos(List<Promo> promos) {};
	};	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState); // TODO test
		
		if (container == null) {
			// We have different layouts, and in one of them this
			// fragment's containing frame doesn't exist. The fragment
			// may still be created from its saved state, but there is
			// no reason to try to create its view hierarchy because it
			// won't be displayed. Note this is not needed -- we could
			// just run the code below, where we would create and return
			// the view hierarchy; it would just never be used.
			return null;
		}
		LinearLayout theLayout = (LinearLayout) inflater.inflate(R.layout.activity_master_detail, container, false);

		this.vw_master = (View) theLayout.findViewById(R.id.master);
		this.vw_detail = (View) theLayout.findViewById(R.id.detail);

		// get list view
		listView = (ListView) this.vw_master.findViewById(R.id.lst_videos);

		// get detail controls
		tvTitle = (TextView) this.vw_detail.findViewById(R.id.text_name);
		tvPrice = (TextView) this.vw_detail.findViewById(R.id.text_price);
		tvDesc = (TextView) this.vw_detail.findViewById(R.id.text_desc);
		img = (ImageView) this.vw_detail.findViewById(R.id.image);

		btnBack = (ImageButton) this.vw_detail.findViewById(R.id.btn_back);
		btnLike = (ImageButton) this.vw_detail.findViewById(R.id.btn_like);
		btnFavorite = (ImageButton) this.vw_detail.findViewById(R.id.btn_love);

		btnBack.setOnClickListener(this);
		btnLike.setOnClickListener(this);
		btnFavorite.setOnClickListener(btnFavoriteListener);

		this.vw_master.setVisibility(View.VISIBLE);
		this.vw_detail.setVisibility(View.GONE);
		
		// if we find a cached list, use it
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		if (preferences.contains("promos")) {
			String promosJson = preferences.getString("promos", null);
			Type listType = new TypeToken<List<Promo>>(){}.getType();
			promos = new Gson().fromJson(promosJson, listType);
			listView.setAdapter(new PromotionsAdapter(getActivity(), promos));
		}
		
		// anyway, request the promos again
		getPromos();
		
		listView.setOnItemClickListener(this);

		this.initAnimation();
		
		Utils.setFontAllView(theLayout);
		return theLayout;
	}
	
	public void getPromos() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Promo");
		// find the most used first!
		query.orderByDescending("uses");
		query.findInBackground(new FindCallback<ParseObject>() {
		    
			public void done(List<ParseObject> scoreList, ParseException e) {		    	
		        if (e == null) {
		        	promos = new ArrayList<Promo>();
		    		for (ParseObject parseObject : scoreList) {
		    			Promo promo = new Promo(
		    					parseObject.getString("objectId"), 
		    					parseObject.getString("title"), 
		    					parseObject.getString("description"), 
		    					parseObject.getString("price"), 
		    					parseObject.getString("image_url"), 
		    					parseObject.getInt("uses")
		    			);
			    		promos.add(promo);
		    		}		    		
		    		
		    		if (isAdded()) {		    				    	
			    		if (listView.getAdapter() == null) {
			    			listView.setAdapter(new PromotionsAdapter(getActivity(), promos));
			    		} else {
			    			((PromotionsAdapter) listView.getAdapter()).notifyDataSetChanged();
			    		}
			    		// Save the list in the Activity
			    		callbacks.gotPromos(promos);
			    		// cache the list
			    		savePromos();
		    		}
		        }
		    }
		});
	}
	
	public void savePromos() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        Type listType = new TypeToken<List<Promo>>(){}.getType();
        editor.putString("promos", new Gson().toJson(promos, listType));
        editor.commit();
	}

	public void setPromos(List<Promo> promos) {
		this.promos = promos;
	}

	private void initAnimation() {
		// animation
		mSlideInLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_in);
		mSlideOutRight = AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_out);
		mSlideInRight = AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_in);
		mSlideOutLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_out);
	}

	public void onItemClick(AdapterView<?> adp, View listview, int position, long id) {
		this._isBack = false;
		showView(this._isBack);

		if (adp != null && adp.getAdapter() instanceof PromotionsAdapter) {
			PromotionsAdapter adapter = (PromotionsAdapter) adp.getAdapter();
			Promo promo = adapter.getItem(position);

			tvTitle.setText(promo.title);
			tvPrice.setText(promo.price);
			tvDesc.setText(promo.description);

			UrlImageViewHelper.setUrlDrawable(img, promo.imageUrl);
		}
	}
	
	View.OnClickListener btnFavoriteListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// update the promo	
		}
	};

	private void showView(boolean isBack) {
		try {
			if (isBack) {
				this.vw_master.setVisibility(View.VISIBLE);
				this.vw_detail.setVisibility(View.GONE);
				this.vw_detail.startAnimation(mSlideOutRight);
				this.vw_master.startAnimation(mSlideInLeft);
			} else {
				this.vw_master.setVisibility(View.GONE);
				this.vw_detail.setVisibility(View.VISIBLE);
				this.vw_master.startAnimation(mSlideOutLeft);
				this.vw_detail.startAnimation(mSlideInRight);
			}
		} catch (Exception e) {
			// try again without animations
			this.vw_master.setVisibility(View.VISIBLE);
			this.vw_detail.setVisibility(View.GONE);
			this.vw_master.setVisibility(View.GONE);
			this.vw_detail.setVisibility(View.VISIBLE);
		}
	}

	public void onBackPressed() {
		if (!this._isBack) {
			this._isBack = !this._isBack;
			showView(this._isBack);
			return;
		}
	}

	@Override
	public void onClick(View v) {
		onBackPressed();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException("Activity must implement fragment's callbacks.");
		}

		callbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		
		// reset the active callbacks interface
		callbacks = promotionsCallbacks;
	}

}
