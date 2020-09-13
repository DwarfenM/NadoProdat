package kz.sherua.nadoprodat.model.dbentity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Sales (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val salesId: Long,
    val salesPrice: Double,
    val currencyId: Long,
    val crDate: Long,
    val upDate: Long
)