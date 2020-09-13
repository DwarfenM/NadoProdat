package kz.sherua.nadoprodat.model.dbentity

import androidx.room.Entity

@Entity(primaryKeys = ["productId", "propertyId"])
data class PropertyValues(
    val productId: Long,
    val propertyId: Long,
    val value: String
)