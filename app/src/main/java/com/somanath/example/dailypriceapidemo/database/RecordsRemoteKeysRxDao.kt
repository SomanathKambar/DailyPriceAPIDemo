package com.somanath.example.dailypriceapidemo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.somanath.example.dailypriceapidemo.data.RecordRemoteKeys

@Dao
interface RecordsRemoteKeysRxDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<RecordRemoteKeys>)

    @Query("SELECT * FROM record_remote_keys WHERE recordId = :recordID")
    fun remoteKeysByRecordId(recordID: Long): RecordRemoteKeys

    @Query("DELETE FROM record_remote_keys")
    fun clearRemoteKeys()

}