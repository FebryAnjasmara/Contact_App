package com.example.contactapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.contactapp.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "digital_talent.db";
    public static final String TABLE_NAME = "contacts";
    public static final String COLUMN_ID =  "id";
    public static final String COLUMN_NAME =  "name";
    public static final String COLUMN_NUMBER =  "number";
    public static final String COLUMN_ADDRESS =  "address";
    public static final String CREATE_CONTACTS =  "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + "" +
            " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT NOT NULL," + COLUMN_NUMBER +
            " TEXT NOT NULL," + COLUMN_ADDRESS + " TEXT NOT NULL)";
    public static final String TAG = DatabaseHelper.class.getName();

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_CONTACTS);
        Log.d(TAG, "onCreate: " + CREATE_CONTACTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public List<Contact> getAll(){
        List<Contact> contacts = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()){
            Contact contact = new Contact(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            contacts.add(contact);
        }
        cursor.close();
        return contacts;
    }

    public long insert(Contact contact){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contact.getName());
        values.put(COLUMN_NUMBER, contact.getNumber());
        values.put(COLUMN_ADDRESS, contact.getAddress());
        return getWritableDatabase().insert(TABLE_NAME, null, values);
    }

    public boolean delete(String id){
        return getWritableDatabase().delete(TABLE_NAME, COLUMN_ID + "=" + id, null) > 0 ;
    }

    public boolean update(Contact contact){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contact.getName());
        values.put(COLUMN_NUMBER, contact.getNumber());
        values.put(COLUMN_ADDRESS, contact.getAddress());
        return getWritableDatabase().update(TABLE_NAME, values, COLUMN_ID + "=" + contact.getId(), null) > 0;
    }
}
