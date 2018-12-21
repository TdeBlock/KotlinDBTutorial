package com.example.tom.kotlindbtutorial

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder

class DatabaseHandler: SQLiteOpenHelper {

    companion object {
        val Tag = "DatabaseHandler"
        val DBName = "ContactDB"
        val DBVersion = 1

        val tableName = "phoneTable"
        val ConID = "id"
        val FirstName = "fname"
        val LastName = "lname"
        val Number = "number"
        val Email = "email"
    }

    var context: Context? = null
    var sqlObj: SQLiteDatabase

    constructor(context: Context): super(context, DBName, null, DBVersion){
        this.context = context
        sqlObj = this.writableDatabase
    }
    override fun onCreate(p0: SQLiteDatabase?) {
        //SQL for creating table
        var sql1: String = "CREATE TABLE IF NOT EXISTS " + tableName + " " +
                "(" + ConID + "INTEGER PRIMARY KEY," +
                FirstName + " TEXT, " + LastName + " TEXT, " + Email +
                " TEXT, " + Number + " TEXT );"

        p0!!.execSQL(sql1)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP table IF EXISTS " + tableName)
        onCreate(p0)
    }

    fun addContact(values: ContentValues): String{
        var Msg: String = "Error"
        val ID = sqlObj!!.insert(tableName, "" , values)

        if(ID > 0){
            Msg = "Ok"
        }
        return Msg
    }

    fun fetchContacts(keyword: String): ArrayList<ContactData>{
        var arraylist = ArrayList<ContactData>()

        val sqb = SQLiteQueryBuilder()
        sqb.tables = tableName
        val cols = arrayOf("id", "fname", "lname", "email", "number")
        val rowSelArgs = arrayOf(keyword)

        val cur = sqb.query(sqlObj, cols, "fname like ?", rowSelArgs, null, null, "fname")

        if(cur.moveToFirst()){
            do{
                val id = cur.getInt(cur.getColumnIndex("id"))
                val fname = cur.getString(cur.getColumnIndex("fname"))
                val lname = cur.getString(cur.getColumnIndex("lname"))
                val email = cur.getString(cur.getColumnIndex("email"))
                val number = cur.getString(cur.getColumnIndex("number"))

                arraylist.add(ContactData(id, fname, lname, email, number))
            } while (cur.moveToNext())
        }

        var count: Int = arraylist.size
        return arraylist
    }

    fun updateContact(values: ContentValues, id: Int):String{
        var selectionArgs = arrayOf(id.toString())

        val i = sqlObj!!.update(tableName, values, "id=?", selectionArgs)
        if(i > 0){
            return "Ok"
        } else {
            return "Error"
        }
    }

    fun RemoveContact(id: Int): String{
        var selectionArgs = arrayOf(id.toString())

        val i = sqlObj!!.delete(tableName, "id=?", selectionArgs)
        if (i > 0){
            return "Ok"
        } else {
            return "Error"
        }
    }











}