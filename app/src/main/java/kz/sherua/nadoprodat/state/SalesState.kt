package kz.sherua.nadoprodat.state

import kz.sherua.nadoprodat.model.dbentity.Sales

sealed class SalesState {

    object SalesEmpty : SalesState()

    data class GetAllSales(val sellsList: List<Sales>) : SalesState()
}