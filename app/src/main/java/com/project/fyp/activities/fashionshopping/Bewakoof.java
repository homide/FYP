package com.project.fyp.activities.fashionshopping;

import android.os.AsyncTask;

import com.project.fyp.interfaces.arraySave;
import com.project.fyp.models.Product;
import com.project.fyp.threads.CallingFashion;

import java.util.ArrayList;

public class Bewakoof extends AsyncTask<String, Void, ArrayList<Product>> implements arraySave {
    //ArrayList for corresponding objects
    ArrayList<Product> products = new ArrayList<>();
    String link;

    @Override
    protected void onPostExecute(ArrayList<Product> s) {
        super.onPostExecute(s);
        for (int i = 0; i < 50; i++){
            arraySave.products.add(s.get(i));
        }
    }



    @Override
    protected ArrayList<Product> doInBackground(String... strings) {
        try{
            System.out.println("Running Bewakoof on thread");
            //initialising calling.java and referencing it
            CallingFashion calling = new CallingFashion();
            link = strings[0];
            calling.call(13,strings[0],"col-sm-4 col-xs-6", "a", "h3","discountedPriceText",
                    "actualPriceText","img", "loyaltyPriceBox");
            //initialising and referencing Calling method variables
            this.products = calling.products;
            System.out.println("Bewakoof ended");
            System.out.println(products.size());
            return products;
        }catch (Exception e){
            //fail-safe :)
            System.out.println("Bewakoof not working" + e);
            return null;
        }
    }
}
