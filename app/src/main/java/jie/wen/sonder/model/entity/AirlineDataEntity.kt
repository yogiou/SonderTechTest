package jie.wen.sonder.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// TODO: will use if need to store in local DB
@Entity
data class AirlineDataEntity (
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "country") val country: String?,
    @ColumnInfo(name = "logo") val logo: String?,
    @ColumnInfo(name = "slogan") val slogan: String?,
    @ColumnInfo(name = "head_quaters") val head_quaters: String?,
    @ColumnInfo(name = "established") val established: Int?
    )