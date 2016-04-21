package grapp.com.grapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import grapp.com.grapp.model.GrappGroup;

/**
 * Created by Hein on 4/15/2016.
 */
public class GroupsAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<GrappGroup> groupsList;
    private Context context;

    public GroupsAdapter(Context context, List<GrappGroup> groupsList) {
        super();
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupsList = groupsList;
    }

    @Override
    public int getCount() {
        return groupsList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.groups_group_layout, parent, false);
            viewHolder.textInViewHolder = (TextView) convertView.findViewById(R.id.groups_group_textView);
            viewHolder.imageInViewholder = (ImageView) convertView.findViewById(R.id.groups_group_imageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textInViewHolder.setText(String.valueOf(groupsList.get(position).getGroupName()));

        int imageResourceId = this.context.getResources().getIdentifier(
                groupsList.get(position).getImageResource(), "drawable", this.context.getPackageName());
        Bitmap src = BitmapFactory.decodeResource(this.context.getResources(), imageResourceId);
        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(this.context.getResources(), src);
        drawable.setCircular(true);

        viewHolder.imageInViewholder.setImageDrawable(drawable);

        return convertView;
    }

    static class ViewHolder {
        TextView textInViewHolder;
        ImageView imageInViewholder;
    }
}
