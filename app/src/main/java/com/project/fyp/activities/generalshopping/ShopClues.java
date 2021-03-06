//Refer to MainActivity, Main2Activity and Calling for commenting references

package com.project.fyp.activities.generalshopping;

import android.os.AsyncTask;

import com.project.fyp.interfaces.arraySave;
import com.project.fyp.models.Product;
import com.project.fyp.threads.CallingGeneral;

import java.util.ArrayList;

public class ShopClues extends AsyncTask<String, Void, ArrayList<Product>> implements arraySave {

    //ArrayList for corresponding objects
    ArrayList<Product> products = new ArrayList<>();
    String link;

    @Override
    protected void onPostExecute(ArrayList<Product> s) {

        super.onPostExecute(s);
        arraySave.products.addAll(products);

    }

    @Override
    protected ArrayList<Product> doInBackground(String... strings) {
        try{

            //initialising calling.java and referencing it
            System.out.println("Running shopclues on thread");
            CallingGeneral calling = new CallingGeneral();
            link = strings[0];
            calling.call(2,strings[0],"column col3 search_blocks", "a", "h2", "p_price", "old_prices",
                    "img", "prd_discount","xyz","style");
            //initialising and referencing Calling method variables
            products = calling.products;
            System.out.println("Ended shopclues on thread");
            return products;
        }catch (Exception e){
            //fail-safe :)
            System.out.println("ShopClues not working" + e);
            return null;
        }
    }
}