package com.project.fyp.activities.fashionshopping;

import android.os.AsyncTask;

import com.project.fyp.interfaces.arraySave;
import com.project.fyp.models.Product;
import com.project.fyp.threads.CallingFashion;

import java.util.ArrayList;

public class Koovs extends AsyncTask<String, Void, ArrayList<Product>> implements arraySave {
    ArrayList<Product> products = new ArrayList<>();
    String link;

    @Override
    protected void onPostExecute(ArrayList<Product> s) {
        super.onPostExecute(s);
        if (s != null){
            arraySave.products.addAll(s);
        }
    }

    @Override
    protected ArrayList<Product> doInBackground(String... strings) {
        try{
            System.out.println("Running koovs on thread");
            //initialising calling.java and referencing it
            CallingFashion calling = new CallingFashion();
            link = strings[0];
            calling.call(12, strings[0],"product-inner pr", "a", "cd chp",
                    "money", "money", "prodImg",
                    "cr fs__15");
            //initialising and referencing Calling method variables
            this.products = calling.products;
            System.out.println("Koovs ended");
            return products;
        }catch (Exception e){
            //fail-safe :)
            System.out.println("Koovs not working" + e);
            return null;
        }
    }
}
