package kz.sherua.nadoprodat.model

import kz.sherua.nadoprodat.model.dbentity.SalesDetailsWithProducts
import java.util.*

data class SingleSaleItem (
    val date: Date,
    val listOfItems: List<SalesDetailsWithProducts>
)