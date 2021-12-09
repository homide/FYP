package com.project.fyp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.fyp.R;
import com.project.fyp.activities.webview.WebViewActivity;
import com.project.fyp.database.DatabaseHelper;
import com.project.fyp.models.Product;
import com.project.fyp.models.Websites;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapterWebView extends RecyclerView.Adapter<MyAdapterWebView.ViewHolder> {
    Context context;
    ArrayList<Websites> websites;

    public MyAdapterWebView(Context context, ArrayList<Websites> websites){
        this.context = context;
        this.websites = websites;
    }

    @NonNull
    @Override
    public MyAdapterWebView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.website_row, parent, false);
        return new MyAdapterWebView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterWebView.ViewHolder holder, int position) {
        Picasso.with(context).load(websites.get(position).getImageLink()).into(holder.websiteImage);
        holder.websiteImage.setOnClickListener(v->{
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra("url",websites.get(position).getUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return websites.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView websiteImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            websiteImage = itemView.findViewById(R.id.websiteImage);
        }
    }
}
