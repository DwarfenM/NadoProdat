package kz.sherua.nadoprodat.model.dbentity

import androidx.room.PrimaryKey

data class Sales (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val salesId: Int,
    val salesPrice: Double,
    val currencyId: Int,
    val crDate: String,
    val upDate: String
)