package kz.sherua.nadoprodat.model.dbentity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class SubGroup (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val groupId: Long,
    val crDate: Long,
    val upDate: Long
)