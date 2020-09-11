package kz.sherua.nadoprodat.model.dbentity

import androidx.room.PrimaryKey
import java.util.*

data class SaleDetails(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val salesId: Int,
    val productId: Int,
    val productSalesPrice: Double,
    val currencyId: Int,
    val crDate: Date,
    val upDate: Date
)