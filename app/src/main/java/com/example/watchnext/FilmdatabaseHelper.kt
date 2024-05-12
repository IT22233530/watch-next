package com.example.watchnext

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.ContactsContract.CommonDataKinds.Note
import java.sql.SQLClientInfoException

class FilmdatabaseHelper (context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION){

    companion object{

        private const val DATABASE_NAME = "filmapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "filmlist"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "fname"
        private const val COLUMN_CATEGORY = "category"
        private const val COLUMN_RATING = "rating"
        private const val COLUMN_DISCRIPTION = "discription"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_NAME TEXT,$COLUMN_CATEGORY TEXT,$COLUMN_RATING TEXT,$COLUMN_DISCRIPTION TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTbaleQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTbaleQuery)
        onCreate(db)
    }

    fun insertFilm(film:Film){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME,film.fName)
            put(COLUMN_CATEGORY,film.fCategory)
            put(COLUMN_RATING,film.rating)
            put(COLUMN_DISCRIPTION,film.description)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun getAllfilms(): List<Film> {

        val filmList = mutableListOf<Film>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val fname = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
            val rating = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_RATING))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DISCRIPTION))

            val film = Film(id,fname,category,rating,description)
            filmList.add(film)

        }
        cursor.close()
        db.close()
        return filmList
    }

    fun updateFilm(film: Film){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, film.fName)
            put(COLUMN_CATEGORY, film.fCategory)
            put(COLUMN_RATING, film.rating)
            put(COLUMN_DISCRIPTION, film.description)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(film.id.toString())
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
    }

    fun getFilmByID(filmId :Int):Film{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $filmId"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val fname = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
        val catagory = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
        val rating = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_RATING))
        val discrition = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DISCRIPTION))

        cursor.close()
        db.close()
        return Film(id,fname,catagory,rating,discrition)

    }

    fun deleteFilm(filmId :Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(filmId.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()
    }


}