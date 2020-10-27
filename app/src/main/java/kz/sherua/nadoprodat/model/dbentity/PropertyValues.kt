package kz.sherua.nadoprodat.model.dbentity

import androidx.room.Entity

@Entity(primaryKeys = ["productId", "propertyId"])
data class PropertyValues(
    var productId: Long,
    var propertyId: Long,
    var value: String
)