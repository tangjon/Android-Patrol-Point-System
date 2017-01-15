package com.example.android.mypointsystem.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mypointsystem.MemberInfoActivity;
import com.example.android.mypointsystem.R;
import com.example.android.mypointsystem.model.MemberProfile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class MemberListDataAdapter extends RecyclerView.Adapter<MemberListDataAdapter.ViewHolder> {

    public static final String ITEM_ID_KEY = "item_id_key";
    public static final String ITEM_KEY = "item_key";
    private List<MemberProfile> mItems;
    private Context mContext;

    public MemberListDataAdapter(Context context, List<MemberProfile> items) {
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public MemberListDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

//    Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//    setSupportActionBar(toolbar);

    @Override
    // SUPPLY DATA WHAT YOU WANT TO DISPLAY TO USER & EVENT HANDLERS
    public void onBindViewHolder(MemberListDataAdapter.ViewHolder holder, int position) {
        final MemberProfile item = mItems.get(position);

        try {
            holder.tvName.setText(item.getFull_name());
            holder.tvPoints.setText(Integer.toString(item.getPoints()) + " points");
            // CHANGE THIS
            String imageFile = "patrolImg_" + item.getCurrent_patrol() + ".png";
            InputStream inputStream = mContext.getAssets().open(imageFile);
            Drawable d = Drawable.createFromStream(inputStream, null);
            holder.imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ONE CLICK LISTENER
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "You selected " + item.getFull_name(), Toast.LENGTH_SHORT).show();

                // DEFINE THE INTENT
                Intent intent = new Intent(mContext, MemberInfoActivity.class);

                // SEND DATA TO DEFINED ACTIVITY ABOVE
                intent.putExtra(ITEM_KEY, item);

                // START ACTIVITY and EXPECT AND RESULT
                ((Activity) mContext).startActivityForResult(intent, 1);


            }
        });

        // LONG CLICK LISTENER
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(mContext, "You long clicked " + item.getFull_name(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });


    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public ImageView imageView;
        public TextView tvPoints;
        public View mView;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.itemNameText);
            imageView = (ImageView) itemView.findViewById(R.id.itemListImage);
            tvPoints = (TextView) itemView.findViewById(R.id.tvPoints);
            mView = itemView;
        }
    }
}