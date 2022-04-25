package com.startupai.healthcare

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class DbClass(context: Context, factory: SQLiteDatabase.CursorFactory?): SQLiteOpenHelper(context,Constants.DB_NAME,factory,Constants.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE IF NOT EXISTS " + Constants.TABLE_NAME + "("
                + Constants.COL_U_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constants.COL_U_F_NAME + " TEXT," +
                Constants.COL_U_L_NAME + " TEXT," +
                Constants.COL_U_EMAIL + " TEXT," +
                Constants.COL_U_PASSWORD + " TEXT" + ");")
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME)
        onCreate(db)
    }

    fun addUser(fName : String, lName : String,Email:String,Password:String ){

        try {
            val values = ContentValues()

            values.put(Constants.COL_U_F_NAME, fName)
            values.put(Constants.COL_U_L_NAME, lName)
            values.put(Constants.COL_U_EMAIL, Email)
            values.put(Constants.COL_U_PASSWORD, Password)

            val db = this.writableDatabase

            db.insert(Constants.TABLE_NAME, null, values)

            db.close()

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun getUser(email:String,password: String): Cursor? {
        val db = this.readableDatabase
        return  db.rawQuery("select * from " + Constants.TABLE_NAME + " where " + Constants.COL_U_EMAIL + "='"+ email + "'" +" and "+Constants.COL_U_PASSWORD + "='"+ password + "'" , null)
    }
}