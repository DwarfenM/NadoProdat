package kz.sherua.nadoprodat.state

import kz.sherua.nadoprodat.model.dbentity.Product

sealed class BasketState {

    object OpenSearch: BasketState()

    object CloseSearch: BasketState()

    object ItemsSelled: BasketState()

    data class ItemAdded(val basketList: List<Product>): BasketState()
}