package ua.kpi.comsys.iv8106.tools.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Pic list, movie list
        String createMain  = "CREATE TABLE  %s (" +
                "no INTEGER PRIMARY KEY AUTOINCREMENT," +
                "query TEXT," +
                "json TEXT" +
                ");";
        //Movie desc
        String createSecondary = "CREATE TABLE %s (" +
                "no INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id TEXT," +
                "json TEXT" +
                ");";

        db.execSQL(String.format(createMain, "imageList"));
        db.execSQL(String.format(createMain, "movieList"));
        db.execSQL(String.format(createSecondary, "movieDescList"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("Upgrade, IDK do something.");
    }
}
