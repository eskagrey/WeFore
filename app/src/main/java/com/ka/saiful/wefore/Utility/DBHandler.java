package com.ka.saiful.wefore.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ka.saiful.wefore.Utility.Model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 11/28/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABSE_NAME = "db_person";

    private static final String TABLE_PERSON = "person";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "pass";

    private static SQLiteDatabase sqLiteDatabase;
    private static DBHandler databaseHandler;
    private final String TAG = DBHandler.class.getSimpleName();

    public DBHandler(Context context) {
        super(context, DATABSE_NAME, null, DATABASE_VERSION);
    }

    public static void init(Context context) {
        databaseHandler = new DBHandler(context);
        sqLiteDatabase = databaseHandler.getWritableDatabase();
    }

    public static synchronized DBHandler getInstance() {
        return databaseHandler;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE_PERSON = "CREATE TABLE IF NOT EXISTS "
                + TABLE_PERSON + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_PASS + " TEXT" + ")";

        sqLiteDatabase.execSQL(CREATE_TABLE_PERSON);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON);
        onCreate(sqLiteDatabase);

    }

    public void addUser(Person person) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, person.getName());
        values.put(KEY_EMAIL, person.getEmail());
        values.put(KEY_PASS, person.getPass());

        sqLiteDatabase.insert(TABLE_PERSON, null, values);
        Log.d(TAG, "insert table user success !");
    }

    public List<Person> getAllUser() {
        List<Person> persons = new ArrayList<>();
        String selectAllUser = "SELECT * FROM " + TABLE_PERSON + " ORDER BY " + KEY_ID + " DESC";

        Cursor cursor = sqLiteDatabase.rawQuery(selectAllUser, null);
        if (cursor.moveToFirst()) {
            do {
                Person person = new Person();
                person.setId(cursor.getInt(0));
                person.setName(cursor.getString(1));
                person.setEmail(cursor.getString(2));
                person.setPass(cursor.getString(3));
                persons.add(person);
            } while (cursor.moveToNext());
        }
        return persons;
    }

    public boolean checkUser(String email, String pass) {
        String selectQuery = "SELECT * FROM " + TABLE_PERSON + " WHERE " + KEY_EMAIL + " = '" + email
                + "' AND " + KEY_PASS + " = '" + pass + "'";

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        return cursor.moveToFirst();
    }

}
