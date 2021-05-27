package ua.kpi.comsys.iv8106.tools.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class Databaser {

    DBHelper dbh;
    SQLiteDatabase db;

    public Databaser (@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        this.dbh = new DBHelper(context, name, factory, version);
        this.db = this.dbh.getWritableDatabase();
    }

    public String queryTable (String tableName, String query, String queryColumn){
        Cursor curr  = this.db.query(tableName,
                        null,
                        String.format("%s = '%s'", queryColumn,  query),
                        null,
                        null,
                        null,
                        null);

        if (curr.getCount() > 0) {
            curr.moveToFirst();
            return curr.getString(curr.getColumnIndex("json"));
        }
        return null;
    }

    public Boolean apppendToTable (String tableName, String newQuery, String queryColumn, ContentValues values){
        Cursor curr  = this.db.query(tableName,
                null,
                String.format("%s = '%s'", queryColumn,  newQuery),
                null,
                null,
                null,
                null);

        if (curr.getCount() <= 0) {
            this.db.insert(tableName, null, values);
            return true;
        }
        return false;
    }
}

