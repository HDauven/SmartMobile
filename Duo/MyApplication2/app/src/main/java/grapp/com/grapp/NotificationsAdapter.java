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

/**
 * Created by Hein on 4/15/2016.
 */
public class NotificationsAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<GrappNotification> notificationList;
    private Context context;

    public NotificationsAdapter(Context context, List<GrappNotification> notificationList) {
        super();
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.notificationList = notificationList;
    }

    @Override
    public int getCount() {
        return notificationList.size();
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
            convertView = layoutInflater.inflate(R.layout.notifications_notification_layout, parent, false);
            viewHolder.messageInViewHolder = (TextView) convertView.findViewById(R.id.notifications_notification_tvMessage);
            viewHolder.dateInViewHolder = (TextView) convertView.findViewById(R.id.notifications_notification_tvDate);
            viewHolder.imageInViewHolder = (ImageView) convertView.findViewById(R.id.notifications_notification_ivImage);
            viewHolder.statusInViewHolder = (ImageView) convertView.findViewById(R.id.notifications_notification_ivStatus);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.messageInViewHolder.setText(String.valueOf(notificationList.get(position).getMessage()));
        viewHolder.dateInViewHolder.setText(String.valueOf(notificationList.get(position).getDate()));

        int imageResourceId = this.context.getResources().getIdentifier(
                notificationList.get(position).getImageResource(), "drawable", this.context.getPackageName());
        Bitmap src = BitmapFactory.decodeResource(this.context.getResources(), imageResourceId);
        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(this.context.getResources(), src);
        drawable.setCircular(true);
        viewHolder.imageInViewHolder.setImageDrawable(drawable);

        if (notificationList.get(position).isSeen()) {
            viewHolder.statusInViewHolder.setImageResource(R.drawable.ic_lens_black_24dp);
        } else {
            viewHolder.statusInViewHolder.setImageResource(R.drawable.ic_panorama_fish_eye_black_24dp);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView messageInViewHolder;
        TextView dateInViewHolder;
        ImageView imageInViewHolder;
        ImageView statusInViewHolder;
    }
}
