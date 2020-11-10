package kz.sherua.nadoprodat.model

import kz.sherua.nadoprodat.model.dbentity.Product
import kz.sherua.nadoprodat.model.dbentity.Property

data class ProductToSave(
    val product: Product,
    val props: HashMap<Property, String>,
    val isToCreate: Boolean
)