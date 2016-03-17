package org.csiweek1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

@SuppressLint("InflateParams")  // See: https://code.google.com/p/android-developer-preview/issues/detail?id=1203
public class CriminalListAdapter extends ArrayAdapter<Criminal> {

	private Context context;
	private List<Criminal> criminals;

	public CriminalListAdapter(Context context, List<Criminal> criminals) {
		super(context, R.layout.criminallistitem, criminals);
		
		this.context = context;
		this.criminals = criminals;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Criminal requestedCriminal = criminals.get(position);

		View criminalView = convertView;

		if(criminalView == null) {
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			criminalView  = inflater.inflate(R.layout.criminallistitem, null);
		}

		TextView nameView = (TextView) criminalView.findViewById(R.id.name_criminal);
		nameView.setText(requestedCriminal.name);

		TextView priceView = (TextView) criminalView.findViewById(R.id.total_bounty);
		priceView.setText(String.format("$ %d,-",requestedCriminal.getBountyInDollars()));

		ImageView imageView = (ImageView) criminalView.findViewById(R.id.mugshot);
		imageView.setImageResource(R.drawable.mugshot1);

		return criminalView;
	}

}
