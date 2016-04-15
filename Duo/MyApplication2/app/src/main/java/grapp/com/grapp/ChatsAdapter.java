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
public class ChatsAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<GrappChat> chatsList;
    private Context context;

    public ChatsAdapter(Context context, List<GrappChat> chatsList) {
        super();
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.chatsList = chatsList;
    }

    @Override
    public int getCount() {
        return chatsList.size();
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
            convertView = layoutInflater.inflate(R.layout.chats_chat_layout, parent, false);
            viewHolder.profileNameInViewHolder = (TextView) convertView.findViewById(R.id.chats_chat_tvProfile);
            viewHolder.lastMessageInViewHolder = (TextView) convertView.findViewById(R.id.chats_chat_tvLastMessage);
            viewHolder.profileImageInViewHolder = (ImageView) convertView.findViewById(R.id.chats_chat_ivProfile);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.profileNameInViewHolder.setText(String.valueOf(chatsList.get(position).getChatName()));
        viewHolder.lastMessageInViewHolder.setText(String.valueOf(chatsList.get(position).getLastMessage()));

        int imageResourceId = this.context.getResources().getIdentifier(
                chatsList.get(position).getImageResource(), "drawable", this.context.getPackageName());
        Bitmap src = BitmapFactory.decodeResource(this.context.getResources(), imageResourceId);
        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(this.context.getResources(), src);
        drawable.setCircular(true);

        viewHolder.profileImageInViewHolder.setImageDrawable(drawable);

        return convertView;
    }

    static class ViewHolder {
        TextView profileNameInViewHolder;
        TextView lastMessageInViewHolder;
        ImageView profileImageInViewHolder;
    }
}
