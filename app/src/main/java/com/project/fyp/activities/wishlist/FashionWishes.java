package com.project.fyp.activities.wishlist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.fyp.R;
import com.project.fyp.database.DatabaseHelper;
import com.project.fyp.models.Product;

import java.util.ArrayList;

public class FashionWishes extends Fragment {

    View view;
    RecyclerView recyclerView;

    Context context;

    TextView noSaveData;
    ArrayList<Product> mainList;
    MyAdapterSavedFashion adapter;

    DatabaseHelper databaseHelper;

    public FashionWishes(Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.wishlist_common_fragment, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);

        databaseHelper = new DatabaseHelper(context);

        noSaveData = view.findViewById(R.id.noWishesText);
        noSaveData.setVisibility(View.INVISIBLE);
        mainList = new ArrayList<>();
        mainList = databaseHelper.getFashionItemsWishlist();

        if (mainList.size() <= 0){
            noSaveData.setVisibility(View.VISIBLE);
        }

        adapter = new MyAdapterSavedFashion(context, mainList);
        GridLayoutManager glm = new GridLayoutManager(context,2,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(glm);
        recyclerView.setAdapter(adapter);
        return view;
    }

}
