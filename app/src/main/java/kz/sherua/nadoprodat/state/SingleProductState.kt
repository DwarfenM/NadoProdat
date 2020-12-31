package kz.sherua.nadoprodat.state

import android.graphics.Bitmap
import kz.sherua.nadoprodat.model.dbentity.PropertyValuesWithProps

sealed class SingleProductState {

    data class PropertyReceived(val property: List<PropertyValuesWithProps>) : SingleProductState()

    data class ImagePicked(val image: String) : SingleProductState()

    object ProductSaved: SingleProductState()

}