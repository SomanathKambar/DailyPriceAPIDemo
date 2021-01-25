package com.somanath.example.dailypriceapidemo.database

import android.content.Context
import androidx.room.*
import com.somanath.example.dailypriceapidemo.data.Record
import com.somanath.example.dailypriceapidemo.data.RecordRemoteKeys
import com.somanath.example.dailypriceapidemo.data.RecordToDB
import javax.inject.Singleton

@Database(
    entities = [RecordToDB::class,RecordRemoteKeys::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
@Singleton
abstract class PriceDetailsDatabase : RoomDatabase() {

    abstract fun priceDetailsRxDao(): PriceDetailsRxDao
    abstract fun recordRemoteKeyDao(): RecordsRemoteKeysRxDao

    companion object {
        const val DATABASE_NAME = "priceDetails.db"

        @Volatile
        private var INSTANCE: PriceDetailsDatabase? = null

        fun getInstance(context: Context): PriceDetailsDatabase =
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: buildDatabase(
                            context
                        ).also {
                            INSTANCE = it
                        }
                }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PriceDetailsDatabase::class.java, DATABASE_NAME
            )
                .build()
    }
}