package com.startupai.healthcare

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class DbAppointmentClass(context: Context, factory: SQLiteDatabase.CursorFactory?): SQLiteOpenHelper(context,Constants.DB_NAME,factory,Constants.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE IF NOT EXISTS " + Constants.APP_TABLE_NAME + "("
                + Constants.COL_APP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constants.COL_APP_F_NAME + " TEXT," +
                Constants.COL_APP_L_NAME + " TEXT," +
                Constants.COL_APP_GENDER + " TEXT," +
                Constants.COL_APP_AGE + " TEXT," +
                Constants.COL_APP_PHONE + " TEXT," +
                Constants.COL_APP_EMAIL + " TEXT," +
                Constants.COL_APP_DATE + " TEXT," +
                Constants.COL_APP_TIME  + " TEXT" + ");")
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + Constants.APP_TABLE_NAME)
        onCreate(db)
    }

    fun addAppointment(fName : String, lName : String,gender:String,age:String,phone:String,Email:String,date:String,time:String){
        try {
            val values = ContentValues()

            values.put(Constants.COL_APP_F_NAME, fName)
            values.put(Constants.COL_APP_L_NAME, Email)
            values.put(Constants.COL_APP_GENDER, gender)
            values.put(Constants.COL_APP_AGE, age)
            values.put(Constants.COL_APP_PHONE, phone)
            values.put(Constants.COL_APP_EMAIL, Email)
            values.put(Constants.COL_APP_DATE, date)
            values.put(Constants.COL_APP_TIME, time)

            val db = this.writableDatabase

            db.insert(Constants.APP_TABLE_NAME, null, values)
            db.close()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

}