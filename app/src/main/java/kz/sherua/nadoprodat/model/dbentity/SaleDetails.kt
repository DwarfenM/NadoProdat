package kz.sherua.nadoprodat.model.dbentity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class SaleDetails(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val salesId: Long,
    val productId: Long,
    val productSalesPrice: Double,
    val productCount: Int,
    val currencyId: Long? = null,
    val crDate: Long,
    val upDate: Long? = null
)