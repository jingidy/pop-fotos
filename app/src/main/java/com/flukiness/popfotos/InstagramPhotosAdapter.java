package com.flukiness.popfotos;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Jing Jin on 9/11/14.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    private static DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
    private static DateFormat tf = DateFormat.getTimeInstance(DateFormat.SHORT);
    private static long dayInMs = 24 * 60 * 60 * 1000;

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
        if (photo.caption != null) {
            tvCaption.setText(formmattedComment(photo.user, photo.caption));
        } else {
            tvCaption.setVisibility(View.GONE);
        }

        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        tvUsername.setText(photo.user.username);

        TextView tvPhotoTime = (TextView) convertView.findViewById(R.id.tvPhotoTime);
        tvPhotoTime.setText(formattedTime(photo.time));

        TextView tvLikesCount = (TextView) convertView.findViewById(R.id.tvLikesCount);
        if (photo.numLikes > 0) {
            tvLikesCount.setText(NumberFormat.getInstance().format(photo.numLikes));
        } else {
            View vLikesContainer = convertView.findViewById((R.id.vLikesContainer));
            vLikesContainer.setVisibility(View.GONE);
        }

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

    private String formattedTime(Date time) {
        long now = new Date().getTime();
        long timeLong = time.getTime();

        if (timeLong + dayInMs > now) {
            // Display time if it was posted today
            return tf.format(time);
        } else if (timeLong + 2 * dayInMs > now) {
            return getContext().getString(R.string.yesterday);
        } else {
            return df.format(time);

        }
    }

    private CharSequence formmattedComment(User user, String comment) {
        StringBuilder sb = new StringBuilder();
        sb.append("<b>").append(user.username).append("</b>").append(" ").append(comment);
        return Html.fromHtml(sb.toString());
    }
}
