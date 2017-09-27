package com.example.theappexperts.flowerapi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URLEncoder;
import java.util.List;

import retrofit2.http.Url;

/**
 * Created by TheAppExperts on 27/09/2017.
 */

class FlowerAdapter extends RecyclerView.Adapter <FlowerAdapter.FlowerViewHolder>{

    List<FlowerListModel> flowerListModels;
    int row;
    Context applicationContext;
    String url="http://services.hanselandpetal.com/photos/";

    public FlowerAdapter(List<FlowerListModel> flowerListModels, int row, Context applicationContext) {
        this.flowerListModels = flowerListModels;
        this.row=row;
        this.applicationContext=applicationContext;
    }

    @Override
    public FlowerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(row, null);
        return new FlowerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlowerViewHolder holder, int position) {
        holder.tvName.setText(flowerListModels.get(position).getName());
        holder.tvPrice.setText(flowerListModels.get(position).getPrice().toString());


        Log.i("Image : ", flowerListModels.get(position).getPhoto());
        Picasso.with(applicationContext)
                .load(url+flowerListModels.get(position).getPhoto())
                .resize(500, 500)
                .centerCrop()
                .into(holder.imgFlower);

    }

    @Override
    public int getItemCount() {
        return flowerListModels.size();
    }

    public class FlowerViewHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvPrice;
        ImageView imgFlower;

        public FlowerViewHolder(View itemView) {
            super(itemView);

            tvName= (TextView) itemView.findViewById(R.id.textView);
            tvPrice= (TextView) itemView.findViewById(R.id.textView2);
            imgFlower = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
