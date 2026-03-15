package com.axiel7.moelist.data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

fun getDatabaseBuilder(): RoomDatabase.Builder<MoeListDatabase> {
    // TODO: change to use a non temp dir
    val dbFile = File(System.getProperty("java.io.tmpdir"), "moelist-database.db")
    return Room.databaseBuilder<MoeListDatabase>(
        name = dbFile.absolutePath,
    )
}