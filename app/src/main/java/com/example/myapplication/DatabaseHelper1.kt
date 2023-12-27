package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper1(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ProductDatabase"
        private const val TABLE_NAME = "products"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_PRICE = "price"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_IMAGE_URI = "image_uri"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $KEY_ID INTEGER PRIMARY KEY,
                $KEY_NAME TEXT,
                $KEY_PRICE TEXT,
                $KEY_DESCRIPTION TEXT,
                $KEY_IMAGE_URI TEXT
            )
        """.trimIndent()

        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertProduct(name: String, price: String, description: String, imageUri: String): Long {
        val values = ContentValues().apply {
            put(KEY_NAME, name)
            put(KEY_PRICE, price)
            put(KEY_DESCRIPTION, description)
            put(KEY_IMAGE_URI, imageUri)
        }

        return writableDatabase.insert(TABLE_NAME, null, values)
    }

    // Update function
    fun updateProduct(id: Long, name: String, price: String, description: String, imageUri: String): Int {
        val values = ContentValues().apply {
            put(KEY_NAME, name)
            put(KEY_PRICE, price)
            put(KEY_DESCRIPTION, description)
            put(KEY_IMAGE_URI, imageUri)
        }

        // Make sure to check if the row with the given id exists before updating
        return writableDatabase.update(TABLE_NAME, values, "$KEY_ID=?", arrayOf(id.toString()))
    }

    // Delete function
    fun deleteProduct(id: Long): Int {
        // Make sure to check if the row with the given id exists before deleting
        return writableDatabase.delete(TABLE_NAME, "$KEY_ID=?", arrayOf(id.toString()))
    }

    fun getAllProducts(): List<Product> {
        val productList = mutableListOf<Product>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor: Cursor = readableDatabase.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndex(KEY_ID))
                val name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                val price = cursor.getString(cursor.getColumnIndex(KEY_PRICE))
                val description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))
                val imageUri = cursor.getString(cursor.getColumnIndex(KEY_IMAGE_URI))

                val product = Product(id, name, price, description, imageUri)
                productList.add(product)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return productList
    }
}