package kz.sherua.nadoprodat.state

import kz.sherua.nadoprodat.model.dbentity.ProductWithProps

sealed class StorageState {

    object NoProducts : StorageState()

    data class GetAllProducts(val productList: List<ProductWithProps>) : StorageState()

}