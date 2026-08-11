package com.axiel7.moelist.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.axiel7.moelist.data.local.searchhistory.SearchHistoryDao
import com.axiel7.moelist.data.local.searchhistory.SearchHistoryEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [
        SearchHistoryEntity::class,
    ],
    version = 1,
)
@TypeConverters(DatabaseConverters::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class MoeListDatabase : RoomDatabase() {
    abstract fun searchHistoryDao() : SearchHistoryDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<MoeListDatabase> {
    override fun initialize(): MoeListDatabase
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<MoeListDatabase>,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
): MoeListDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(dispatcher)
        .build()
}