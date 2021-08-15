package jie.wen.sonder.model.dao

import androidx.room.*

import jie.wen.sonder.model.entity.AirlineDataEntity

// TODO: will use if need to store in local DB
@Dao
interface AirlineDataDAO {
    @Insert
    fun insert(vararg airlineDataEntity: AirlineDataEntity)

    @Update
    fun update(vararg airlineDataEntity: AirlineDataEntity)

    // TODO: define later
    @Delete
    fun delete(airlineDataEntity: AirlineDataEntity)

    // TODO: define later
    @Query("select * from airlinedataentity")
    fun findAll(): List<AirlineDataEntity>
}