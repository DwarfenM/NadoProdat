package kz.sherua.nadoprodat.model.dbentity

import androidx.room.PrimaryKey
import java.util.*

data class Group(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val crDate: Date,
    val upDate: Date
)