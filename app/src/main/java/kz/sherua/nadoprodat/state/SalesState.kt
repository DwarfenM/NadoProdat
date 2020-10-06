package kz.sherua.nadoprodat.state

import kz.sherua.nadoprodat.model.dbentity.Sales
import kz.sherua.nadoprodat.model.dbentity.SalesWithDetailsAndProducts

sealed class SalesState {

    object SalesEmpty : SalesState()

    data class GetAllSales(val sellsList: List<SalesWithDetailsAndProducts>) : SalesState()
}