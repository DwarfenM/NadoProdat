package kz.sherua.nadoprodat.model.dbentity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Product(@PrimaryKey(autoGenerate = true)
                   val id: Long? = null,
                   val name: String,
                   val count: Int,
                   val salesPrice: Double,
                   val salesCurrencyId: Long,
                   val costPrice: Double,
                   val costCurrencyId: Long,
                   val groupId: Long,
                   val subgroupId: Long,
                   val typeId: Long,
                   val isFlex: Boolean,
                   val image: String,
                   val crDate: Long,
                   val upDate: Long
                   )