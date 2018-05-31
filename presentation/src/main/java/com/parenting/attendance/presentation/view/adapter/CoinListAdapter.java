package com.parenting.attendance.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parenting.attendance.R;
import com.parenting.attendance.presentation.model.LTC;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by adwait on 15/01/18.
 */

public class CoinListAdapter extends RecyclerView.Adapter<CoinListAdapter.CoinHolder> {

    private ArrayList<LTC> list;
    private Context mContext;

    @Inject
    public CoinListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public CoinHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_index, parent, false);
        CoinHolder holder = new CoinHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CoinHolder holder, int position) {
        LTC item;
        try {
            item = list.get(position);
            holder.ticker_trade_static_textView.setVisibility(View.INVISIBLE);
            holder.ticker_trade_volume_textView.setVisibility(View.INVISIBLE);
            Picasso.with(mContext).load(item.getImageUrl()).error(R.drawable.ic_bitcoin).into(holder.ticker_logo_imageView);
            holder.ticker_name_textView.setText(item.getCoinName());
            holder.ticker_value_textView.setText(item.getAlgorithm());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public ArrayList<LTC> getList() {
        return list;
    }

    public void setList(List<LTC> list) {
        this.list = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public class CoinHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView ticker_trade_static_textView;
        public AppCompatTextView ticker_trade_volume_textView;
        public AppCompatImageView ticker_logo_imageView;
        public AppCompatTextView ticker_value_textView;
        public AppCompatTextView ticker_name_textView;

        public CoinHolder(View itemView) {
            super(itemView);
            ticker_name_textView = (AppCompatTextView) itemView.findViewById(R.id.ticker_name_textView);
            ticker_value_textView = (AppCompatTextView) itemView.findViewById(R.id.ticker_value_textView);
            ticker_trade_volume_textView = (AppCompatTextView) itemView.findViewById(R.id.ticker_trade_volume_textView);
            ticker_trade_static_textView = (AppCompatTextView) itemView.findViewById(R.id.ticker_trade_static_textView);
            ticker_logo_imageView = (AppCompatImageView) itemView.findViewById(R.id.ticker_logo_imageView);

        }
    }
}
