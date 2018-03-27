package com.example.mygrocerieslist.mygrocerieslist.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mygrocerieslist.mygrocerieslist.Model.Grocery;
import com.example.mygrocerieslist.mygrocerieslist.Util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RaymondTsang on 12/26/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context context;

    public DatabaseHandler(Context context) {
        super(context, Util.DB_NAME, null, Util.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GROCERY_TABLE =
                "CREATE TABLE " + Util.GROCERIES_TABLE_NAME + "(" +
                        Util.KEY_ID + " INTEGER PRIMARY KEY," +
                        Util.KEY_GROCERY_ITEM + " TEXT," +
                        Util.KEY_GROCERY_QUANTITY + " TEXT);";
        db.execSQL(CREATE_GROCERY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Util.DB_NAME);
        onCreate(db);
    }

    public void addGrocery(Grocery grocery) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_GROCERY_ITEM, grocery.getItem());
        values.put(Util.KEY_GROCERY_QUANTITY, grocery.getQuantity());

        db.insert(Util.GROCERIES_TABLE_NAME, null, values);
    }

    public Grocery getGrocery(int id) {
        Grocery grocery = null;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                Util.GROCERIES_TABLE_NAME,
                new String[] {Util.KEY_ID, Util.KEY_GROCERY_ITEM, Util.KEY_GROCERY_QUANTITY},
                Util.KEY_ID + "=?",
                new String[] {String.valueOf(id)},
                null, null, null);

        if (cursor.moveToFirst()) {
            grocery = new Grocery();
            grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Util.KEY_ID))));
            grocery.setItem(cursor.getString(cursor.getColumnIndex(Util.KEY_GROCERY_ITEM)));
            grocery.setQuantity(cursor.getString(cursor.getColumnIndex(Util.KEY_GROCERY_QUANTITY)));
        }

        return grocery;
    }

    public List<Grocery> getAllGroceries() {
        List<Grocery> groceryList = new ArrayList<>();
        Grocery grocery = null;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                Util.GROCERIES_TABLE_NAME,
                new String[]{Util.KEY_ID, Util.KEY_GROCERY_ITEM, Util.KEY_GROCERY_QUANTITY},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                grocery = new Grocery();
                grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Util.KEY_ID))));
                grocery.setItem(cursor.getString(cursor.getColumnIndex(Util.KEY_GROCERY_ITEM)));
                grocery.setQuantity(cursor.getString(cursor.getColumnIndex(Util.KEY_GROCERY_QUANTITY)));

                groceryList.add(grocery);
            } while (cursor.moveToNext());
        }
        return groceryList;
    }

    public int updateGrocery(Grocery grocery) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_GROCERY_ITEM, grocery.getItem());
        values.put(Util.KEY_GROCERY_QUANTITY, grocery.getQuantity());

        int ret = db.update(
                Util.GROCERIES_TABLE_NAME,
                values,
                Util.KEY_ID + "=?",
                new String[] {String.valueOf(grocery.getId())}
                );


        return ret;
    }

    public void deleteGrocery(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(
                Util.GROCERIES_TABLE_NAME,
                Util.KEY_ID + "=?",
                new String[] {String.valueOf(id)}
                );
        db.close();
    }

}
