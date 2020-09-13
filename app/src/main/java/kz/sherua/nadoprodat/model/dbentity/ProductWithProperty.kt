package kz.sherua.nadoprodat.model.dbentity

import androidx.room.Embedded
import androidx.room.Relation

data class ProductWithProperty(
    @Embedded var propertyValues: PropertyValues,
    @Relation(
        entity = Product::class,
        entityColumn = "id",
        parentColumn = "productId"
    )
    var product: Product,
    @Relation(
        entity = Property::class,
        entityColumn = "id",
        parentColumn = "propertyId"
    )
    var property: Property
)