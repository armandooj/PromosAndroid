package com.armandooj.promos.activities;

import java.util.ArrayList;

import com.armandooj.promos.R;
import com.armandooj.promos.adapters.MenuAdapter;
import com.armandooj.promos.models.MenuItem;
import com.armandooj.promos.utils.Utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MenuFragment extends Fragment implements OnItemClickListener {

	ListView listView;
	ArrayList<MenuItem> lstMenuItems;
	View vw_layout;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

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
		vw_layout = inflater.inflate(R.layout.fragment_menu, container, false);

		this.initialiseMenuItems(savedInstanceState);
		
		Utils.setFontAllView((ViewGroup) vw_layout);
		return vw_layout;
	}

	/**
	 * Step 2: Setup Menu Items
	 */
	private void initialiseMenuItems(Bundle args) {
		// get list view
		listView = (ListView) this.vw_layout.findViewById(R.id.lst_menu);

		lstMenuItems = new ArrayList<MenuItem>();

		addMenuItem(R.string.perfil, R.drawable.ico_person, ProfileActivity.class, args);
		addMenuItem(R.string.promociones, R.drawable.ico_star, PromotionsActivity.class, args);
		addMenuItem(R.string.actividad_reciente, R.drawable.ico_comment, SwipeActivity.class, args);
		addMenuItem(R.string.ayuda, R.drawable.ic_action_about, Test.class, args);

		// Assign the items to the list
		listView.setAdapter(new MenuAdapter(getActivity(), lstMenuItems));
		// Register item click listener
		listView.setOnItemClickListener(this);
	}

	@SuppressWarnings("rawtypes")
	private void addMenuItem(int labelID, int drawableId, Class cl, Bundle args) {
		MenuItem mnu = new MenuItem(labelID, drawableId, cl, args);
		lstMenuItems.add(mnu);
	}

	public void onItemClick(AdapterView<?> adp, View listview, int position, long id) {

		if (adp != null && adp.getAdapter() instanceof MenuAdapter) {
			MenuAdapter menuAdp = (MenuAdapter) adp.getAdapter();
			MenuItem itm = menuAdp.getItem(position);
			Fragment newContent = itm.get_fragment();

			if (newContent == null) {

				newContent = Fragment.instantiate(getActivity(), itm.get_class().getName(), itm.get_args());
				itm.set_fragment(newContent);
			}

			switchFragment(newContent);
		}
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof MainActivity) {
			MainActivity mActivity = (MainActivity) getActivity();
			mActivity.switchContent(fragment);
		}
	}

}
