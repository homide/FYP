package com.project.fyp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.fyp.R;
import com.project.fyp.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapterFashion extends RecyclerView.Adapter<MyAdapterFashion.ViewHolder> {
    Context context;
    ArrayList<Product> products;

    public MyAdapterFashion(Context context, ArrayList<Product> products){
        this.context = context;
        this.products = products;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fashion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        try {
            holder.mainTitle.setText(product.title);
            holder.priceBefore.setText(product.priceBefore);
            holder.discPrice.setText(product.discountedPrice);
            holder.discount.setText(product.discount);
            holder.ratingBar.setRating(product.rating);
            holder.ratingCount.setText(product.ratingCount);
            String tag = product.tag;
            switch (tag){
                case "Paytm":
                    Picasso.with(context).load("https://store-images.s-microsoft.com/image/apps.30876.14627692376548906.9a3f6f3d-f2dd-491a-82aa-74db97c80cdd.2c236b26-6975-47ba-8423-4ad4fef81746").into(holder.tag);
                    break;
                case "Shopclues":
                    Picasso.with(context).load("https://bharatstories.in/wp-content/uploads/2019/12/ShopClues-1200x858.png").into(holder.tag);
                    break;
                case "Snapdeal":
                    Picasso.with(context).load("https://pbs.twimg.com/profile_images/774270055555137538/MHkmX_fU_400x400.jpg").into(holder.tag);
                case "Koovs":
                    Picasso.with(context).load("https://res-4.cloudinary.com/crunchbase-production/image/upload/c_lpad,h_256,w_256,f_auto,q_auto:eco/v1397190836/593d89ec956537ccb4212dd87539d092.jpg").into(holder.tag);
                    break;
                case "Bewakoof":
                    Picasso.with(context).load("https://images.bewakoof.com/original/bewakoof-b--app-logo-bewakoof-com-new-logo-1483957527.jpg").into(holder.tag);
                    break;
            }
        }catch (Exception m){
            System.out.println(m);
        }
        try {
            if (product.imageLink.length() <= 0) {
                Picasso.with(context).load("https://1m19tt3pztls474q6z46fnk9-wpengine.netdna-ssl.com/wp-content/themes/unbound/images/No-Image-Found-400x264.png").into(holder.productImage);
            } else {
                Picasso.with(context).load(product.imageLink).into(holder.productImage);
            }
        } catch (Exception m) {
            System.out.println(m);
        }


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mainTitle, priceBefore, discPrice, discount, ratingCount;
        public ImageView productImage,tag;
        public RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mainTitle = itemView.findViewById(R.id.mainTitle);
            priceBefore = itemView.findViewById(R.id.priceBefore);
            discPrice = itemView.findViewById(R.id.discPrice);
            discount = itemView.findViewById(R.id.discount);
            productImage = itemView.findViewById(R.id.productImage);
            tag = itemView.findViewById(R.id.tag);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            ratingCount = itemView.findViewById(R.id.ratingCount);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Product url = products.get(position);
            String link = url.productLink;
            Intent intent = new Intent((Intent.ACTION_VIEW));
            intent.setData(Uri.parse(link));
            context.startActivity(intent);
        }
    }

}