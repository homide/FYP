package com.project.fyp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.fyp.models.Product;

public class DatabaseHelper extends SQLiteOpenHelper {
    Context context;
    public static final String DATABASE_NAME = "allData";
    public static final String LOGIN_DATABASE = "loginDetails";
    public static final String GENERAL_ITEMS = "generalItems";
    public static final String SEARCH_RESULTS = "searchResults";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS " + LOGIN_DATABASE + "(email TEXT NOT NULL, password TEXT NOT NULL, loggedIn TEXT NOT NULL)");
        db.execSQL("create table IF NOT EXISTS " + GENERAL_ITEMS + "(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, priceBefore TEXT, discountedPrice TEXT, discount TEXT, imageLink TEXT, productLink TEXT NOT NULL, tag TEXT NOT NULL, rating FLOAT, ratingCount TEXT)");
        db.execSQL("create table IF NOT EXISTS " + SEARCH_RESULTS + "(id INTEGER PRIMARY KEY AUTOINCREMENT, searchedItem TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GENERAL_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + LOGIN_DATABASE);
        db.execSQL("DROP TABLE IF EXISTS " + SEARCH_RESULTS);
        db.execSQL("create table IF NOT EXISTS " + LOGIN_DATABASE + "(email TEXT NOT NULL, password TEXT NOT NULL, loggedIn TEXT NOT NULL)");
        db.execSQL("create table IF NOT EXISTS " + GENERAL_ITEMS + "(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, priceBefore TEXT, discountedPrice TEXT, discount TEXT, imageLink TEXT, productLink TEXT NOT NULL, tag TEXT NOT NULL, rating FLOAT, ratingCount TEXT)");
        db.execSQL("create table IF NOT EXISTS " + SEARCH_RESULTS + "(id INTEGER PRIMARY KEY AUTOINCREMENT, searchedItem TEXT NOT NULL)");
    }

    public Cursor getData(String tableName){
        try{
            SQLiteDatabase db = getReadableDatabase();
            Cursor res = db.rawQuery("SELECT * FROM " + tableName,null);
            return res;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public void insertLoginDetails(String email, String password, String loggedIn){
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("email",email);
            contentValues.put("password",password);
            contentValues.put("loggedIn",loggedIn);
            db.insert(LOGIN_DATABASE,null,contentValues);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void deleteLoginDetails(){
        try{
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DELETE FROM " + LOGIN_DATABASE);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public boolean checkLoginDetails(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("SELECT loggedIn FROM " + LOGIN_DATABASE,null);
        if (res.getCount() > 0){
            while (res.moveToNext()){
                String val = res.getString(0);
                if (val.equals("true")){
                    return true;
                }else{
                    return false;
                }
            }
        }else{
            return false;
        }

        return false;
    }

    public void updateLoginDetails(String email, String password, String loggedIn){
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("email",email);
            contentValues.put("password",password);
            contentValues.put("loggedIn",loggedIn);
            db.update(LOGIN_DATABASE,contentValues,"email = '" + email + "'", null);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void insertSearchItem(String itemName){
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("searchedItem",itemName);
            db.insert(SEARCH_RESULTS,null,contentValues);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void deleteSearchItem(int id){
        try{
            SQLiteDatabase db = getWritableDatabase();
            db.delete(SEARCH_RESULTS,"id = " + id, null);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void insertGeneralItems(Product product){
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("title",product.getTitle());
            contentValues.put("priceBefore",product.getPriceBefore());
            contentValues.put("discountedPrice",product.getDiscountedPrice());
            contentValues.put("discount",product.getDiscount());
            contentValues.put("imageLink",product.getImageLink());
            contentValues.put("productLink",product.getProductLink());
            contentValues.put("tag",product.getTag());
            contentValues.put("rating",product.getRating());
            contentValues.put("ratingCount",product.getRatingCount());
            db.insert(GENERAL_ITEMS,null,contentValues);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public boolean deleteGeneralItems(String productLink){
        try{
            SQLiteDatabase db = getWritableDatabase();
            return db.delete(GENERAL_ITEMS,  "productLink = '" + productLink + "'", null) > 0;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public boolean checkGeneral(String productLink){
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + GENERAL_ITEMS + " WHERE productLink = '" + productLink + "'",null);
        if (res.getCount() > 0 ){
            return true;
        }else{
            return false;
        }
    }

}
