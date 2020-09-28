package kz.sherua.nadoprodat.state

import kz.sherua.nadoprodat.model.dbentity.Product
import kz.sherua.nadoprodat.model.dbentity.ProductWithProps
import kz.sherua.nadoprodat.model.dbentity.PropertyValuesWithProps

sealed class BasketState {

    object OpenSearch: BasketState()

    object CloseSearch: BasketState()

    object ItemsSelled: BasketState()

    data class SortedState(val basketList: List<Product>): BasketState()

    data class BasketDeletedState(val basketList: List<Product>): BasketState()

    data class ItemAdded(val basketList: List<Product>): BasketState()

    data class FoundItem(val foundItems: List<ProductWithProps>): BasketState()
}