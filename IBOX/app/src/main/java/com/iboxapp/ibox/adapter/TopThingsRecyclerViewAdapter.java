package com.iboxapp.ibox.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iboxapp.ibox.R;

/**
 * Created by gongchen on 2016/4/20.
 */
public class TopThingsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Context context;
    private String[] titles;

    public enum ITEM_TYPE {
        ITEM1
    }

    public TopThingsRecyclerViewAdapter(Context context, String[] titles) {
        this.titles = titles;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Item1ViewHolder(mLayoutInflater.inflate(R.layout.top_things_cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Item1ViewHolder) holder).mTextView.setText(titles[position]);
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE.ITEM1.ordinal();
    }

    @Override
    public int getItemCount() {
        return titles == null ? 0 : titles.length;
    }

    public static class Item1ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        public Item1ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.text_card_price);
        }
    }
}
