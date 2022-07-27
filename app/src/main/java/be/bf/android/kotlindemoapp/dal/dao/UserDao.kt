package be.bf.android.kotlindemoapp.dal.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import be.bf.android.kotlindemoapp.dal.DbHelper
import be.bf.android.kotlindemoapp.dal.entities.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.sql.Connection

class UserDao(private val context: Context) {
    object Entry: BaseColumns {
        const val TABLE_NAME = "users"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"

        const val SQL_CREATE_USER = """CREATE TABLE $TABLE_NAME(
            ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_USERNAME VARCHAR(255) NOT NULL,
            $COLUMN_PASSWORD VARCHAR(255) NOT NULL)"""
        const val SQL_UPGRADE_USER = """DROP TABLE $TABLE_NAME"""
    }
    private val helper: DbHelper = DbHelper(context)

    fun openReadable(): SQLiteDatabase = helper.readableDatabase
    fun openWritable(): SQLiteDatabase = helper.writableDatabase

    fun insert(user: User): Long {
        return openReadable().use {
            val cv = ContentValues().apply {
                put(Entry.COLUMN_USERNAME, user.username)
                put(Entry.COLUMN_PASSWORD, user.password)
            }
            it.insert(Entry.TABLE_NAME, null, cv)
        }
    }
    suspend fun findAll(): Flow<List<User>> = flow {
        openWritable().use {
            val users = mutableListOf<User>()
            val cursor = it.query(Entry.TABLE_NAME, null, null, null, null, null, null)
            with(cursor) {
                while (moveToNext()) {
                    users.add(User(
                        getString(getColumnIndex(Entry.COLUMN_USERNAME)),
                        getString(getColumnIndex(Entry.COLUMN_PASSWORD)),
                        getInt(getColumnIndex(BaseColumns._ID))
                    ))
                }
            }
            emit(users)
        }
    }
}