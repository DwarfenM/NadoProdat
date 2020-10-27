package kz.sherua.nadoprodat.model.dbentity

import androidx.room.Embedded
import androidx.room.Relation

data class PropertyValuesWithProps(
    @Embedded var propertyValues: PropertyValues?,
    @Relation(
        entity = Property::class,
        entityColumn = "id",
        parentColumn = "propertyId"
    )
    var property: Property
)