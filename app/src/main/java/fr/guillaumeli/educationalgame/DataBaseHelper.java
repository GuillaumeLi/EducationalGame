package fr.guillaumeli.educationalgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String WORD_TABLE = "WORD_TABLE";
    private static final String COLUMN_WORD = "WORD";
    private static final String COLUMN_RIGHT_LETTER = "RIGHT_LETTER";
    private static final String COLUMN_FALSE_LETTER = "FALSE_LETTER";
    private static final String COLUMN_ID = "ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "wordDataBase.db", null, 1);
    }

    /**
     * This is called the first time a database is accessed
     * There should be code in here to create a new database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + WORD_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                 COLUMN_WORD + " TEXT, " + COLUMN_RIGHT_LETTER + " TEXT, " + COLUMN_FALSE_LETTER + " TEXT)";

        db.execSQL(createTableStatement);
    }

    /**
     * This is called if the database version number changes
     * It prevents previous users apps from breaking when you change the database design
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Add a data to the database
     * getWritableDatabase() method is to insert data
     * getReadableDatabase() method is to read/select data
     * ContentValue is like a hashmap --> associate two values
     *
     * insert is a successful variable. It checks if the db.insert worked or not
     *
     * @param data to add to the database
     * @return
     */
    public boolean addData(Data data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_WORD, data.getWord());
        contentValues.put(COLUMN_RIGHT_LETTER, data.getRightLetter());
        contentValues.put(COLUMN_FALSE_LETTER, data.getFalseLetter());

        long insert = db.insert(WORD_TABLE, null, contentValues);

        if(insert == -1){
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Cursor is the result set from a SQL statement
     * @return
     */

    public ArrayList<Data> getData() {
        ArrayList<Data> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + WORD_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            //Loop through the cursor (result set) and create new data object to put them in the return list
            do {
                int dataID = cursor.getInt(0);
                String word = cursor.getString(1);
                String rightLetter = cursor.getString(2);
                String falseLetter = cursor.getString(3);

                Data newData = new Data(dataID, word, rightLetter, falseLetter);
                returnList.add(newData);

            } while(cursor.moveToNext());
        }

        //We need to close the cursor and database when finished to let other users to use
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean updateData(String row_id, String word, String rightLetter, String falseLetter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_WORD, word);
        contentValues.put(COLUMN_RIGHT_LETTER, rightLetter);
        contentValues.put(COLUMN_FALSE_LETTER, falseLetter);

        long result = db.update(WORD_TABLE, contentValues, COLUMN_ID + "=?", new String[] {row_id});
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteOneData(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(WORD_TABLE, COLUMN_ID + "=?", new String[] {row_id});
        if(result == -1) {
            return true;
        } else {
            return false;
        }
    }
}
