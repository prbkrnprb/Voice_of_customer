package com.cognizant.a348984.voc.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.cognizant.a348984.voc.data.VOCContract.*;

/**
 * Created by 348984 on 1/12/2017.
 */

public class VOCDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "voc_details.db";

    public VOCDbHelper(Context context) {
        super(context, DATABASE_NAME, null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LOGIN_TABLE = "CREATE TABLE " + LoginEntry.TABLE_NAME + " ("
                + LoginEntry.COLUMN_ID + " INTEGER PRIMARY KEY, "
                + LoginEntry.COLUMN_EMAIL + " TEXT NOT NULL, "
                + LoginEntry.COLUMN_TYPE + " TEXT NOT NULL, "
                + LoginEntry.COLUMN_LAST_LOGIN + " DATE NOT NULL, "
                + LoginEntry.COLUMN_IS_LOGGED_IN + " BOOLEAN"
                + ");";

        final String SQL_CREATE_CATEGORY_TABLE = "CREATE TABLE " + CategoryEntry.TABLE_NAME + " ("
                + CategoryEntry.COLUMN_ID + " INTEGER PRIMARY KEY, "
                + CategoryEntry.COLUMN_VALUE + " TEXT NOT NULL, "
                + CategoryEntry.COLUMN_CREATED_BY + " TEXT, "
                + CategoryEntry.COLUMN_CREATED_ON + " DATE NOT NULL, "
                + CategoryEntry.COLUMN_LAST_UPDATED + " DATE NOT NULL"
                + ");";


        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " + QuestionsEntry.TABLE_NAME + " ("
                + QuestionsEntry.COLUMN_ID + " INTEGER PRIMARY KEY, "
                + QuestionsEntry.COLUMN_VALUE + " TEXT NOT NULL, "
                + QuestionsEntry.COLUMN_CATEGORY_ID + " INTEGER NOT NULL, "
                + QuestionsEntry.COLUMN_CREATED_BY + " TEXT, "
                + QuestionsEntry.COLUMN_CREATED_ON + " DATE NOT NULL, "
                + QuestionsEntry.COLUMN_LAST_UPDATED + " DATE NOT NULL"

                + "FOREIGN KEY (" + QuestionsEntry.COLUMN_CATEGORY_ID + ") REFERENCES "
                + CategoryEntry.TABLE_NAME + " ("
                + CategoryEntry.COLUMN_ID + "));";

        final String SQL_CREATE_PROGRESS_TABLE = "CREATE TABLE " + ProgressEntry.TABLE_NAME + " ("
                + ProgressEntry.COLUMN_KEY + " INTEGER PRIMARY KEY,"
                + ProgressEntry.COLUMN_DETAIL_ID + " INTEGER, "
                + ProgressEntry.COLUMN_CATEGORY_NAME + " TEXT NOT NULL, "
                + ProgressEntry.COLUMN_QUESTION_NAME + " TEXT NOT NULL, "
                + ProgressEntry.COLUMN_ANSWER + " TEXT NOT NULL, "
                + ProgressEntry.COLUMN_IS_SAVED + " BOOLEAN"
                + ");";

        db.execSQL(SQL_CREATE_LOGIN_TABLE);
        db.execSQL(SQL_CREATE_CATEGORY_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        db.execSQL(SQL_CREATE_PROGRESS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LoginEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CategoryEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ProgressEntry.TABLE_NAME);

        onCreate(db);
    }
}
