package kz.sherua.nadoprodat.model.dbentity

import androidx.room.PrimaryKey
import java.util.*

data class Type(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val groupId: Int,
    val crDate: Date,
    val upDate: Date
)