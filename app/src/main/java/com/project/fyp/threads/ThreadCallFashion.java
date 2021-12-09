package com.project.fyp.threads;

import com.project.fyp.activities.fashionshopping.Bewakoof;
import com.project.fyp.activities.fashionshopping.Koovs;
import com.project.fyp.activities.generalshopping.Paytm;
import com.project.fyp.activities.generalshopping.ShopClues;
import com.project.fyp.activities.generalshopping.Snapdeal;

public class ThreadCallFashion implements Runnable {
    String url, methodCallingName;

    ThreadCallFashion(String url, String methodCallingName){
        this.url = url;
        this.methodCallingName = methodCallingName;
    }
    @Override
    public void run() {
        if(methodCallingName.equals("Snapdeal")){
            Snapdeal snap = new Snapdeal();
            snap.execute(url);
        }
        if(methodCallingName.equals("ShopClues")){
            ShopClues shop = new ShopClues();
            shop.execute(url);
        }
        if(methodCallingName.equals("Paytm")){
            Paytm pay = new Paytm();
            pay.execute(url);
        }
        if(methodCallingName.equals("Koovs")){
            Koovs koov = new Koovs();
            koov.execute(url);
        }
        if(methodCallingName.equals("Bewakoof")){
            Bewakoof bew = new Bewakoof();
            bew.execute(url);
        }
    }

    public void start(){
        System.out.println("Starting " + methodCallingName);
        Thread t = new Thread(this);
        t.start();
    }
}
