package com.project.fyp.activities.generalshopping;

import android.os.AsyncTask;

import com.project.fyp.interfaces.arraySave;
import com.project.fyp.models.Product;
import com.project.fyp.threads.CallingGeneral;

import java.util.ArrayList;

public class Pricee extends AsyncTask<String, Void, ArrayList<Product>> implements arraySave {

    //ArrayList for corresponding objects
    ArrayList<Product> products = new ArrayList<>();
    String link;

    @Override
    protected void onPostExecute(ArrayList<Product> s) {
        super.onPostExecute(s);
        arraySave.products.addAll(s);
    }

    @Override
    protected ArrayList<Product> doInBackground(String... strings) {
        try{
            System.out.println("Running pricee on thread");
            CallingGeneral calling = new CallingGeneral();
            link = strings[0];
            calling.call(6,strings[0],"ng-scope","a","ng-binding",
                    "rupee","No Use","","pd-off ng-binding ng-scope",
                    "ng-binding ng-scope","active_rating");
            products = calling.products;
            System.out.println("Ended pricee");
            return products;
        }catch (Exception e){
            System.out.println("Pricee not working" + e);
            return null;
        }
    }
}
