package kz.sherua.nadoprodat.model.dbentity

import androidx.room.Embedded
import androidx.room.Relation

data class SalesWithDetailsAndProducts (
    @Embedded
    var sales: Sales,
    @Relation(
        entity = SaleDetails::class,
        entityColumn = "salesId",
        parentColumn = "id"
    )
    var salesDetails: List<SalesDetailsWithProducts>
)