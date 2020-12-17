package kz.sherua.nadoprodat.model

import kz.sherua.nadoprodat.model.dbentity.ProductWithProps

data class StorageSortModel(val sortType: String, val products: List<ProductWithProps>)