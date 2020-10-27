package kz.sherua.nadoprodat.state

import kz.sherua.nadoprodat.model.dbentity.PropertyValuesWithProps

sealed class SingleProductState {

    data class PropertyReceived(val property: List<PropertyValuesWithProps>) : SingleProductState()

}