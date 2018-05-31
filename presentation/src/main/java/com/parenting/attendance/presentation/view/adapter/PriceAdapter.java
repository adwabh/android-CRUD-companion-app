/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.parenting.attendance.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parenting.attendance.R;
import com.parenting.attendance.presentation.model.PriceModel;
import com.parenting.attendance.presentation.model.UserModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adaptar that manages a collection of {@link UserModel}.
 */
public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.CryptoViewHolder> {

  public interface OnItemClickListener {
    void onCryptoItemClicked(PriceModel priceModelModel);
  }

  private List<PriceModel> usersCollection;
  private final LayoutInflater layoutInflater;

  private OnItemClickListener onItemClickListener;

  @Inject
  PriceAdapter(Context context) {
    this.layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.usersCollection = Collections.emptyList();
  }

  @Override public int getItemCount() {
    return (this.usersCollection != null) ? this.usersCollection.size() : 0;
  }

  @Override public CryptoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = this.layoutInflater.inflate(R.layout.list_item_index, parent, false);
    return new CryptoViewHolder(view);
  }

  @Override public void onBindViewHolder(CryptoViewHolder holder, final int position) {
    final PriceModel priceModel = this.usersCollection.get(position);
    holder.textViewTickerName.setText("Etherium");
    StringBuilder builder = new StringBuilder();
    builder.append("INR ")
           .append(priceModel.getINR());
    holder.textViewTickerValue.setText(builder.toString());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (PriceAdapter.this.onItemClickListener != null) {
          PriceAdapter.this.onItemClickListener.onCryptoItemClicked(priceModel);
        }
      }
    });
  }

  @Override public long getItemId(int position) {
    return position;
  }

  public void setPriceCollection(Collection<PriceModel> priceCollection) {
    this.validatePriceCollection(priceCollection);
    this.usersCollection = (List<PriceModel>) priceCollection;
    this.notifyDataSetChanged();
  }

  public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  private void validatePriceCollection(Collection<PriceModel> usersCollection) {
    if (usersCollection == null) {
      throw new IllegalArgumentException("The list cannot be null");
    }
  }

  static class CryptoViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.ticker_name_textView) TextView textViewTickerName;
    @Bind(R.id.ticker_value_textView) TextView textViewTickerValue;

    CryptoViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
