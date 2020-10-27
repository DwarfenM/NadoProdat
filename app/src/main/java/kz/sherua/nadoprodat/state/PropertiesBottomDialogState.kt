package kz.sherua.nadoprodat.state

import kz.sherua.nadoprodat.model.dbentity.Property

sealed class PropertiesBottomDialogState {

    data class GetAllPropNames(val properties: List<Property>) : PropertiesBottomDialogState()

}