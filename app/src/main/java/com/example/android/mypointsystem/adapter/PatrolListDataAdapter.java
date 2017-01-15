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

import com.example.android.mypointsystem.PatrolInfoActivity;
import com.example.android.mypointsystem.R;
import com.example.android.mypointsystem.model.PatrolProfile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class PatrolListDataAdapter extends RecyclerView.Adapter<PatrolListDataAdapter.ViewHolder> {

    public static final String ITEM_ID_KEY = "item_id_key";
    public static final String ITEM_KEY = "item_key";
    private List<PatrolProfile> mItems;
    private Context mContext;

    public PatrolListDataAdapter(Context context, List<PatrolProfile> items) {
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public PatrolListDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    // SUPPLY DATA WHAT YOU WANT TO DISPLAY TO USER & EVENT HANDLERS
    public void onBindViewHolder(PatrolListDataAdapter.ViewHolder holder, int position) {
        final PatrolProfile item = mItems.get(position);

        // SET IMAGE & SET NAME & SET POINTS

        try {
            holder.tvName.setText(item.getPATROL_NAME());
            holder.tvPoints.setText(Integer.toString(item.getPointSummary()) + " points");
            String imageFile = "patrolImg_" + item.getPATROL_NAME() + ".png";
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
//                Toast.makeText(mContext, "You selected " + item.getPATROL_NAME(), Toast.LENGTH_SHORT).show();

                // DEFINE THE INTENT
                Intent intent = new Intent(mContext, PatrolInfoActivity.class);

                // SEND PARCEABLE DATA TO DEFINED ACTIVITY ABOVE
                intent.putExtra(ITEM_KEY, item);

                // START ACTIVITY
                ((Activity) mContext).startActivityForResult(intent, 1);
            }
        });

        // LONG CLICK LISTENER
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(mContext, "You long clicked " + item.getPATROL_NAME(), Toast.LENGTH_SHORT).show();
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

