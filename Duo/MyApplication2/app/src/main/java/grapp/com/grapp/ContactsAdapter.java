package grapp.com.grapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Erwin on 15-4-2016.
 */

@SuppressLint("InflateParams")
public class ContactsAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> contacts;

    public ContactsAdapter(Context context, List<String> contacts) {
        super(context, R.layout.contact_list_item, contacts);

        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String requestedName = contacts.get(position);

        View nameView = convertView;

        if(nameView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            nameView  = inflater.inflate(R.layout.contact_list_item, null);
        }

        TextView name = (TextView) nameView.findViewById(R.id.contact_name);
        name.setText(requestedName);

        return nameView;
    }
}
