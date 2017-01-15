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
import com.example.android.mypointsystem.PatrolListActivity;
import com.example.android.mypointsystem.R;
import com.example.android.mypointsystem.model.Entry;
import com.example.android.mypointsystem.model.Entry;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class EntryListDataAdapter extends RecyclerView.Adapter<EntryListDataAdapter.ViewHolder> {

    public static final String ITEM_ID_KEY = "item_id_key";
    public static final String ITEM_KEY = "item_key";
    private List<Entry> mItems;
    private Context mContext;

    public EntryListDataAdapter(Context context, List<Entry> items) {
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public EntryListDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.list_item2, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }


    @Override
    // SUPPLY DATA WHAT YOU WANT TO DISPLAY TO USER & EVENT HANDLERS
    public void onBindViewHolder(EntryListDataAdapter.ViewHolder holder, int position) {
        final Entry item = mItems.get(position);

        holder.tvName.setText(item.getENTRY_NAME());
        holder.tvPoints.setText(item.getDATE());
        

        // ONE CLICK LISTENER
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // DEFINE THE INTENT
                Intent intent = new Intent(mContext, PatrolListActivity.class);

                // SEND DATA TO DEFINED ACTIVITY ABOVE
                intent.putExtra( ITEM_KEY, item);

                // START ACTIVITY and EXPECT AND RESULT
                ((Activity) mContext).startActivityForResult(intent, 1);


            }
        });

        // LONG CLICK LISTENER
//        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Toast.makeText(mContext, "You long clicked " + item.getFull_name(), Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });


    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvPoints;
        public View mView;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.itemNameText);
            tvPoints = (TextView) itemView.findViewById(R.id.tvPoints);
            mView = itemView;
        }
    }
}