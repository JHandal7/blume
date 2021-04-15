package com.duha.blume.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duha.blume.Fragments.MyOrderedProductsDetailPage;
import com.duha.blume.MVP.OrderVariants;
import com.duha.blume.R;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by AbhiAndroid
 */
public class DetailOrderProductListAdapter extends RecyclerView.Adapter<DetailOrderedProductsListViewHolder> {
    Context context;
    List<OrderVariants> newsListResponse;

    public DetailOrderProductListAdapter(Context context, List<OrderVariants> newsListResponse) {
        this.context = context;
        this.newsListResponse = newsListResponse;
    }

    @Override
    public DetailOrderedProductsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_ordered_products_list_items1, null);
        DetailOrderedProductsListViewHolder DetailOrderedProductsListViewHolder = new DetailOrderedProductsListViewHolder(context, view, newsListResponse);
        return DetailOrderedProductsListViewHolder;
    }

    @Override
    public void onBindViewHolder(final DetailOrderedProductsListViewHolder holder, final int position) {

        holder.productName1.setText(newsListResponse.get(position).getProductname() + " - " + newsListResponse.get(position).getVariantname());
        try {

            if (newsListResponse.get(position).getExtra().size() > 0) {
                Log.d("size", newsListResponse.get(position).getExtra().toString());
                holder.txtExtras.setText("Extra item: " + newsListResponse.get(position).getExtra().size());
                holder.txtExtras.setVisibility(View.VISIBLE);
            } else {
                holder.txtExtras.setVisibility(View.GONE);
            }
        } catch (Exception e) {

        }

        holder.qty.setText("Cantidad: " + newsListResponse.get(position).getVarquantity());
        holder.price.setText("Precio: " + MyOrderedProductsDetailPage.currency + " " + newsListResponse.get(position).getVarprice());
        try {
            Picasso.with(context)
                    .load(newsListResponse.get(position).getImage())
                    .placeholder(R.drawable.defaultimage)
                    .into(holder.image1);
        } catch (Exception e) {
        }

    }

    @Override
    public int getItemCount() {
        return newsListResponse.size();
    }

}
