package com.example.android.mypointsystem.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.mypointsystem.R;
import com.example.android.mypointsystem.model.PatrolProfile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class PatrolListDataAdapterListView extends ArrayAdapter<PatrolProfile> {

    List<PatrolProfile> mCourseItems;
    LayoutInflater mInflator;

    public PatrolListDataAdapterListView(Context context, List<PatrolProfile> objects) {
        super(context, R.layout.list_item, objects);

        mCourseItems = objects;
        mInflator = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflator.inflate(R.layout.list_item, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.itemNameText);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.itemListImage);

        PatrolProfile item = mCourseItems.get(position);

        tvName.setText(item.getPATROL_NAME());
//        imageView.setImageResource(R.mipmap.ic_launcher);
        InputStream inputStream = null;
        try {
            String imageFile = "patrolImg_" + item.getPATROL_NAME() + ".png";
            inputStream = getContext().getAssets().open(imageFile);
            Drawable d = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return convertView;
    }
}
