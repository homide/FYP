package com.project.fyp.activities.wishlist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.fyp.R;
import com.project.fyp.database.DatabaseHelper;
import com.project.fyp.models.Product;

import java.util.ArrayList;

public class GeneralWishes extends Fragment {

    View view;
    RecyclerView recyclerView;

    Context context;

    TextView noSaveData;
    ArrayList<Product> mainList;
    MyAdapterSavedGeneral adapter;

    //DatabaseHelper
    DatabaseHelper databaseHelper;

    public GeneralWishes(Context context){
        this.context = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.wishlist_common_fragment, container, false);

        databaseHelper = new DatabaseHelper(context);

        noSaveData = view.findViewById(R.id.noWishesText);
        recyclerView = view.findViewById(R.id.recycler_view);
        noSaveData.setVisibility(View.INVISIBLE);
        mainList = new ArrayList<>();

        mainList = databaseHelper.getGeneralItemsWishlist();

        if (mainList.size() <= 0){
            noSaveData.setVisibility(View.VISIBLE);
        }

        adapter = new MyAdapterSavedGeneral(context, mainList);
        LinearLayoutManager llm = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        return view;
    }

}