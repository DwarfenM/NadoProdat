package kz.sherua.nadoprodat.model

import kz.sherua.nadoprodat.model.dbentity.Property

data class ProductToSave(
    val productId: Long? = null,
    val productName: String,
    val productCount: Int,
    val productSalesPrice: Double,
    val productCostPrice: Double,
    val props: HashMap<Property, String>,
    val isToCreate: Boolean
)