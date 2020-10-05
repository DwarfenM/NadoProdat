package kz.sherua.nadoprodat.model.dbentity

import androidx.room.Embedded
import androidx.room.Relation

data class SalesDetailsWithProducts (
    @Embedded var salesDetails : SaleDetails,
    @Relation(
        entity = Product::class,
        entityColumn = "id",
        parentColumn = "productId"
    )
    var product: ProductWithProps
)