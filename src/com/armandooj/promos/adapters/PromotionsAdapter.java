package com.armandooj.promos.adapters;

import java.util.List;

import com.armandooj.promos.R;
import com.armandooj.promos.models.Promo;
import com.armandooj.promos.utils.Utils;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PromotionsAdapter extends ArrayAdapter<Promo> {

	private List<Promo> _list;
	private final Activity _context;
	private static LayoutInflater _inflater = null;

	public PromotionsAdapter(Activity context, List<Promo> lst) {
		super(context, R.layout.row_masterdetail, lst);
		this._context = context;
		this._list = lst;

		_inflater = this._context.getLayoutInflater();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (convertView == null) {
			view = _inflater.inflate(R.layout.row_masterdetail, null);
		}

		Utils.setFontAllView(parent);

		Promo promo = _list.get(position);

		TextView tvTitle = (TextView) view.findViewById(R.id.text_name);
		TextView tvPrice = (TextView) view.findViewById(R.id.text_price);
		TextView tvDesc = (TextView) view.findViewById(R.id.text_desc);
		ImageView iv = (ImageView) view.findViewById(R.id.image);

		tvTitle.setText(promo.title);
		tvPrice.setText(promo.price);
		tvDesc.setText(promo.description);

		UrlImageViewHelper.setUrlDrawable(iv, promo.imageUrl);
		
		ImageView mostUsedImage = (ImageView) view.findViewById(R.id.best_promo);
		// most used tag
		if (position == 0) {
			// set it visible
			mostUsedImage.setVisibility(View.VISIBLE);
		} else {
			mostUsedImage.setVisibility(View.GONE);
		}

		return view;
	}
}
