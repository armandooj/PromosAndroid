package com.armandooj.promos.activities;

import com.armandooj.promos.R;
import com.armandooj.promos.utils.Utils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.parse.Parse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends FragmentActivity implements OnClickListener {
	
	private SlidingMenu menu;
	// private Fragment content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
        // Initialize parse
        Parse.initialize(this, "xOYGA3pD0VgpO47tbQ3no1r6IOAyuHF7D0UgDbz4", "s2Vw5tYZSDnKCtWoZV471cjthYCtusDKw9N00iUn");
		
		ViewGroup vg = (ViewGroup)findViewById(R.id.main_root);
		
		menu = new SlidingMenu(this);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setSlidingEnabled(false);
		menu.setMenu(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MenuFragment()).commit();
		
		ImageButton btnList = (ImageButton) findViewById(R.id.btn_list);
		ImageButton btnCompose = (ImageButton) findViewById(R.id.btn_compose);

		btnList.setOnClickListener(this);
		btnCompose.setOnClickListener(this);
				
		Utils.setFontAllView(vg);
	}
	
	@Override
	public void onBackPressed() {
		if (menu.isMenuShowing()) {
			menu.showContent();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_list) {
			if (menu.isMenuShowing()) {
				menu.showContent();
			} else {
				menu.showMenu();
			}
		}
		if (v.getId() == R.id.btn_compose) {
			//Intent i = new Intent(this.getApplicationContext(), SubActivity.class);
			//startActivity(i);
		}
	}
	
	public void switchContent(Fragment fragment) {
		// content = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		menu.showContent();
	}

}
