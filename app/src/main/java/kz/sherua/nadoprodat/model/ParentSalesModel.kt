package kz.sherua.nadoprodat.model

import kz.sherua.nadoprodat.model.dbentity.SalesWithDetailsAndProducts
import java.util.*

data class ParentSalesModel(
    var date: Date,
    var sum: Double,
    var childs: MutableList<SalesWithDetailsAndProducts>
)