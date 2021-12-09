package com.project.fyp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.fyp.models.Product;
import com.project.fyp.models.SearchList;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    Context context;
    public static final String DATABASE_NAME = "allData";
    public static final String LOGIN_DATABASE = "loginDetails";

    public static final String GENERAL_ITEMS = "generalItems";
    public static final String LAST_CLICKED_GENERAL_ITEMS = "lastClickedGeneralItems";

    public static final String SEARCH_RESULTS = "searchResults";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS " + LOGIN_DATABASE + "(email TEXT NOT NULL, password TEXT NOT NULL, loggedIn TEXT NOT NULL)");

        db.execSQL("create table IF NOT EXISTS " + GENERAL_ITEMS + "(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, priceBefore TEXT, discountedPrice TEXT, discount TEXT, imageLink TEXT, productLink TEXT NOT NULL, tag TEXT NOT NULL, rating FLOAT, ratingCount TEXT)");
        db.execSQL("create table IF NOT EXISTS " + LAST_CLICKED_GENERAL_ITEMS + "(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, priceBefore TEXT, discountedPrice TEXT, discount TEXT, imageLink TEXT, productLink TEXT NOT NULL, tag TEXT NOT NULL, rating FLOAT, ratingCount TEXT)");

        db.execSQL("create table IF NOT EXISTS " + SEARCH_RESULTS + "(id INTEGER PRIMARY KEY AUTOINCREMENT, searchedItem TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GENERAL_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + LOGIN_DATABASE);
        db.execSQL("DROP TABLE IF EXISTS " + SEARCH_RESULTS);
        db.execSQL("DROP TABLE IF EXISTS " + LAST_CLICKED_GENERAL_ITEMS);
        db.execSQL("create table IF NOT EXISTS " + LOGIN_DATABASE + "(email TEXT NOT NULL, password TEXT NOT NULL, loggedIn TEXT NOT NULL)");
        db.execSQL("create table IF NOT EXISTS " + GENERAL_ITEMS + "(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, priceBefore TEXT, discountedPrice TEXT, discount TEXT, imageLink TEXT, productLink TEXT NOT NULL, tag TEXT NOT NULL, rating FLOAT, ratingCount TEXT)");
        db.execSQL("create table IF NOT EXISTS " + LAST_CLICKED_GENERAL_ITEMS + "(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, priceBefore TEXT, discountedPrice TEXT, discount TEXT, imageLink TEXT, productLink TEXT NOT NULL, tag TEXT NOT NULL, rating FLOAT, ratingCount TEXT)");
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

    public String getEmail(){
        try{
            SQLiteDatabase db = getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT email FROM " + LOGIN_DATABASE, null);
            while (cur.moveToNext()){
                return cur.getString(0);
            }
            return "";
        }catch (Exception e){
            System.out.println(e);
            return "";
        }
    }

    public ArrayList<SearchList> getSearchedItems(){
        try{
            ArrayList<SearchList> arrayList = new ArrayList<>();
            SQLiteDatabase db = getReadableDatabase();
            Cursor res = db.rawQuery("SELECT * FROM " + SEARCH_RESULTS,null);
            while (res.moveToNext()){
                SearchList searchList = new SearchList();
                searchList.setId(res.getInt(0));
                searchList.setSearchedItem(res.getString(1));
            }
            return arrayList;
        }catch (Exception e){
            System.out.println(e);
            return null;
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

    public ArrayList<Product> getGeneralItemsWishlist(){
        try{
            ArrayList<Product> arrayList = new ArrayList<>();
            SQLiteDatabase db = getReadableDatabase();
            Cursor res = db.rawQuery("SELECT * FROM " + GENERAL_ITEMS,null);
            while (res.moveToNext()){
                Product product = new Product();
                product.setTitle(res.getString(1));
                product.setPriceBefore(res.getString(2));
                product.setDiscountedPrice(res.getString(3));
                product.setDiscount(res.getString(4));
                product.setImageLink(res.getString(5));
                product.setProductLink(res.getString(6));
                product.setTag(res.getString(7));
                product.setRating(res.getFloat(8));
                product.setRatingCount(res.getString(9));
                arrayList.add(product);
            }
            return arrayList;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public void insertGeneralItemsWishlist(Product product){
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

    public boolean deleteGeneralItemsWishlist(String productLink){
        try{
            SQLiteDatabase db = getWritableDatabase();
            return db.delete(GENERAL_ITEMS,  "productLink = '" + productLink + "'", null) > 0;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public boolean checkGeneralItem(String productLink){
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + GENERAL_ITEMS + " WHERE productLink = '" + productLink + "'",null);
        if (res.getCount() > 0 ){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkGeneralItemList(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + GENERAL_ITEMS,null);
        if (res.getCount() > 0 ){
            return true;
        }else{
            return false;
        }
    }



    public void insertGeneralItemsLastClicked(Product product){
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
            db.insert(LAST_CLICKED_GENERAL_ITEMS,null,contentValues);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public boolean deleteGeneralItemsLastClicked(String productLink){
        try{
            SQLiteDatabase db = getWritableDatabase();
            return db.delete(LAST_CLICKED_GENERAL_ITEMS,  "productLink = '" + productLink + "'", null) > 0;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public boolean checkGeneralItemListLastClicked(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + LAST_CLICKED_GENERAL_ITEMS,null);
        if (res.getCount() > 0 ){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<Product> getGeneralItemsLastClicked(){
        try{
            ArrayList<Product> arrayList = new ArrayList<>();
            SQLiteDatabase db = getReadableDatabase();
            Cursor res = db.rawQuery("SELECT * FROM " + LAST_CLICKED_GENERAL_ITEMS,null);
            while (res.moveToNext()){
                Product product = new Product();
                product.setTitle(res.getString(1));
                product.setPriceBefore(res.getString(2));
                product.setDiscountedPrice(res.getString(3));
                product.setDiscount(res.getString(4));
                product.setImageLink(res.getString(5));
                product.setProductLink(res.getString(6));
                product.setTag(res.getString(7));
                product.setRating(res.getFloat(8));
                product.setRatingCount(res.getString(9));
                arrayList.add(product);
            }
            return arrayList;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}
