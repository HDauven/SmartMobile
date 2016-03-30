package csi.hdauven.com.csi_week_1;

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
		super(context, R.layout.content_criminalslist, criminals);
		
		this.context = context;
		this.criminals = criminals;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Criminal requestedCriminal = criminals.get(position);
		View criminalView = convertView;

		//TOOD: replace this simple view by the layout as defined in criminallistitem.xml"
		if (criminalView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			criminalView = inflater.inflate(R.layout.criminallistitem, null);
		}

		TextView nameCriminal = (TextView) criminalView.findViewById(R.id.textViewCriminalName);
		nameCriminal.setText(requestedCriminal.name);

		TextView bountyCriminal = (TextView) criminalView.findViewById(R.id.textViewCriminalBounty);
		bountyCriminal.setText(String.format("$ %d,-", requestedCriminal.getBountyInDollars()));

		ImageView mugshotCriminal = (ImageView) criminalView.findViewById(R.id.imageViewCriminal);
		mugshotCriminal.setImageDrawable(requestedCriminal.mugshot);
		
		return criminalView;
	}

}
