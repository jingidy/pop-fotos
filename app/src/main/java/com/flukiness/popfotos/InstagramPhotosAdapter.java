package com.flukiness.popfotos;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Jing Jin on 9/11/14.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> photos) {
        super(context, android.R.layout.simple_list_item_1, photos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);

        boolean isNewView = convertView == null;
        if (isNewView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        tvCaption.setText(Html.fromHtml(photo.htmlString()));

        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        tvUsername.setText(photo.user.username);

        ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto);
        ImageView imgUser = (ImageView) convertView.findViewById(R.id.imgUser);
        if (isNewView) {
            imgPhoto.getLayoutParams().height = imgPhoto.getLayoutParams().width;
        } else {
            imgUser.setImageResource(0);
            //TODO reset to original loading image
            imgPhoto.setImageResource(0);
        }
        Picasso.with(getContext()).load(photo.imageURL).into(imgPhoto);
        Picasso.with(getContext()).load(photo.user.imageURL).into(imgUser);

        return convertView;
    }
}
