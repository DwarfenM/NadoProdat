package kz.sherua.nadoprodat.model.dbentity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Property (
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    var name: String,
    val crDate: Long,
    val upDate: Long
)