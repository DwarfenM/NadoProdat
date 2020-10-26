package kz.sherua.nadoprodat.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_single_product.*
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.model.dbentity.ProductWithProps
import java.text.SimpleDateFormat
import java.util.*

class SingleProductFragment : Fragment() {

    private val args: SingleProductFragmentArgs by navArgs()
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!args.isNew) {
            val product = Gson().fromJson(args.productData, ProductWithProps::class.java)
            etProductName.setText(product.product.name)
            tvProductCount.text = product.product.count.toString()
            etSellPrice.setText(product.product.salesPrice.toString())
            etActualPrice.setText(product.product.costPrice.toString())
            tvAddedDate.text = dateFormat.format(Date(product.product.crDate!!))
        }
    }

}