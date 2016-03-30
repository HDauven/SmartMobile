package csi.hdauven.com.csi_week_1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

@SuppressLint("InflateParams")  // See: https://code.google.com/p/android-developer-preview/issues/detail?id=1203
public class CrimeListAdapter extends ArrayAdapter<Crime> {

	private Context context;
	private List<Crime> crimes;

	public CrimeListAdapter(Context context, List<Crime> crimes) {
		super(context, R.layout.content_criminalslist, crimes);
		
		this.context = context;
		this.crimes = crimes;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Crime requestedCrime = crimes.get(position);
		View crimeView = convertView;

		//TOOD: replace this simple view by the layout as defined in crimelistitem.xml"
		if (crimeView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			crimeView = inflater.inflate(R.layout.crimelistitem, null);
		}

		TextView nameCrime = (TextView) crimeView.findViewById(R.id.textViewCrimeName);
		nameCrime.setText(requestedCrime.name);

		TextView bountyCrime = (TextView) crimeView.findViewById(R.id.textViewCrimeBounty);
		bountyCrime.setText(String.format("$ %d,-", requestedCrime.bountyInDollars));

		TextView descriptionCrime = (TextView) crimeView.findViewById(R.id.textViewCrimeDescription);
		descriptionCrime.setText(requestedCrime.description);
		
		return crimeView;
	}

}
