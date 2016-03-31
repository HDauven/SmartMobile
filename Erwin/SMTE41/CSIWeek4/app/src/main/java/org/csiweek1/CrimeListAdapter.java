package org.csiweek1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Erwin on 24-3-2016.
 */
public class CrimeListAdapter extends ArrayAdapter<Crime>
{
    private Context context;
    private List<Crime> crimes;

    public CrimeListAdapter(Context context, List<Crime> crimes)
    {
        super(context, R.layout.crime_list_item, crimes);

        this.context = context;
        this.crimes = crimes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Crime requestedCrime = crimes.get(position);

        View crimeView = convertView;

        if(crimeView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            crimeView  = inflater.inflate(R.layout.crime_list_item, null);
        }

        TextView crimeNameView = (TextView) crimeView.findViewById(R.id.name_crime);
        crimeNameView.setText(requestedCrime.name);

        TextView crimeBounty = (TextView) crimeView.findViewById(R.id.bountyDollars);
        crimeBounty.setText(String.format("$ %d,-", requestedCrime.bountyInDollars));

        TextView crimeDescription = (TextView) crimeView.findViewById(R.id.crimeDescription);
        crimeDescription.setText(requestedCrime.description);

        return crimeView;
    }
}
