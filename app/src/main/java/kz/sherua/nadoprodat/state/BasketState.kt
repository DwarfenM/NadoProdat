package kz.sherua.nadoprodat.state

import kz.sherua.nadoprodat.model.BasketModel

sealed class BasketState {

    object OpenSearch: BasketState()

    object CloseSearch: BasketState()

    data class ItemAdded(val basketList: List<BasketModel>): BasketState()
}