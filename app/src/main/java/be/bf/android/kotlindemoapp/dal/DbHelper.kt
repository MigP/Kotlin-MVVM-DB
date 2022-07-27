package be.bf.android.kotlindemoapp.dal

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import be.bf.android.kotlindemoapp.dal.dao.UserDao

class DbHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        const val DB_NAME = "demo_db"
        var DB_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(UserDao.Entry.SQL_CREATE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(UserDao.Entry.SQL_UPGRADE_USER)
    }
}