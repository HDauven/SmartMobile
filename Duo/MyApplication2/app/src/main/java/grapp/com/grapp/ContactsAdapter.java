package grapp.com.grapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import grapp.com.grapp.model.GrappContact;

/**
 * Created by Erwin on 15-4-2016.
 */

@SuppressLint("InflateParams")
public class ContactsAdapter extends ArrayAdapter<GrappContact> {

    private Context context;
    public List<GrappContact> contacts;

    public ContactsAdapter(Context context, List<GrappContact> contacts) {
        super(context, R.layout.contact_list_item, contacts);

        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GrappContact requestedContact = contacts.get(position);

        View nameView = convertView;

        if(nameView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            nameView  = inflater.inflate(R.layout.contact_list_item, null);
        }

        TextView name = (TextView) nameView.findViewById(R.id.contact_name);
        name.setText(requestedContact.Name);

        TextView distance = (TextView) nameView.findViewById(R.id.contact_distance);
        distance.setText(String.format("%.1f km", (requestedContact.Distance/1000)));

        return nameView;
    }
}
