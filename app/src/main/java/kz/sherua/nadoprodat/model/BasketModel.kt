package kz.sherua.nadoprodat.model

data class BasketModel (
    val itemName: String,
    val itemCount: Long,
    val itemPrice: Long,
    val itemUrl: String?
)