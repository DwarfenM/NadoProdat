package kz.sherua.nadoprodat.model.dbentity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Product(@PrimaryKey(autoGenerate = true)
                   var id: Long? = null,
                   var name: String,
                   var count: Int,
                   var salesPrice: Double,
                   var salesCurrencyId: Long? = null,
                   var costPrice: Double? = null,
                   var costCurrencyId: Long? = null,
                   var groupId: Long? = null,
                   var subgroupId: Long? = null,
                   var typeId: Long? = null,
                   var isFlex: Boolean? = false,
                   var image: String? = null,
                   var crDate: Long? = null,
                   var upDate: Long? = null
)