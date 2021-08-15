package jie.wen.sonder.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import jie.wen.sonder.model.dao.AirlineDataDAO
import jie.wen.sonder.model.entity.AirlineDataEntity

// TODO: will use if need to store in local DB
@Database(entities = arrayOf(AirlineDataEntity::class), version = 1)
abstract class AirlineDataDatabase : RoomDatabase() {
    abstract fun airlineDataDao(): AirlineDataDAO
}