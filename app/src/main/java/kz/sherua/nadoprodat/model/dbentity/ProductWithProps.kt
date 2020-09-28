package kz.sherua.nadoprodat.model.dbentity

import androidx.room.Embedded
import androidx.room.Relation

data class ProductWithProps (
    @Embedded
    var product: Product,
    @Relation(
        entity = PropertyValues::class,
        entityColumn = "productId",
        parentColumn = "id"
    )
    var propertyValues: List<PropertyValuesWithProps>
    )