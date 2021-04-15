package com.duha.blume.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.duha.blume.MVP.OrderVariants;
import com.duha.blume.R;

import java.util.List;

import butterknife.ButterKnife;

public class DetailOrderedProductsListViewHolder extends RecyclerView.ViewHolder {

    ImageView image1;
    TextView productName1,txtExtras,qty,price;

    public DetailOrderedProductsListViewHolder(final Context context, View itemView, List<OrderVariants> newsListResponse) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        image1 = (ImageView) itemView.findViewById(R.id.productImage1);
        productName1 = (TextView) itemView.findViewById(R.id.productName1);
        txtExtras = (TextView) itemView.findViewById(R.id.txtExtras);
        qty = (TextView) itemView.findViewById(R.id.quantity);
        price = (TextView) itemView.findViewById(R.id.price);



    }
}
