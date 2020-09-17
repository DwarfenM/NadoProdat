package kz.sherua.nadoprodat.state

sealed class AddItemState {

    data class AddCount(val count: Int): AddItemState()

    data class RemoveCount(val count: Int): AddItemState()

    object AddItem: AddItemState()

}