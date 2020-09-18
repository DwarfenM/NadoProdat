package kz.sherua.nadoprodat.model

data class BasketModel (
    var itemName: String,
    var itemCount: Long,
    var itemPrice: Long,
    var itemUrl: String?
)