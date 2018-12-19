package com.example.tom.kotlindbtutorial

import android.database.sqlite.SQLiteOpenHelper

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


}