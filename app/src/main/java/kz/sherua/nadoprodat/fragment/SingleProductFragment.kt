package kz.sherua.nadoprodat.fragment

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_single_product.*
import kz.sherua.nadoprodat.R
import kz.sherua.nadoprodat.adapter.PropertiesAdapter
import kz.sherua.nadoprodat.dialog.PropertiesBottomDialogFragment
import kz.sherua.nadoprodat.model.ProductToSave
import kz.sherua.nadoprodat.model.dbentity.Product
import kz.sherua.nadoprodat.model.dbentity.ProductWithProps
import kz.sherua.nadoprodat.model.dbentity.Property
import kz.sherua.nadoprodat.presenter.SingleProductPresenter
import kz.sherua.nadoprodat.state.SingleProductState
import kz.sherua.nadoprodat.utils.ImageUtils
import kz.sherua.nadoprodat.view.SingleProductView
import java.text.SimpleDateFormat
import java.util.*


class SingleProductFragment : MviFragment<SingleProductView, SingleProductPresenter>(),
    SingleProductView {

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private lateinit var itemsAdapter: PropertiesAdapter
    private lateinit var itemAdded: BehaviorSubject<List<Property>>
    private lateinit var getImagePathIntent: BehaviorSubject<Uri>
    private lateinit var product: ProductWithProps
    private val args: SingleProductFragmentArgs by navArgs()
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
    private var imageString: String? = null

    private fun verifyStoragePermissions(activity: Activity?) {
        val permission = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        } else {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemAdded = BehaviorSubject.create()
        getImagePathIntent = BehaviorSubject.create()
        activity?.appBarMain?.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_single_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lvAddProp.setOnClickListener {
//            val navController =
//                Navigation.findNavController((context as FragmentActivity).findViewById(R.id.nav_host_fragment))
//            navController.navigate(R.id.propertiesBottomDialogFragment)
            val dialog = PropertiesBottomDialogFragment(itemAdded)
            dialog.show(activity?.supportFragmentManager!!, PropertiesBottomDialogFragment.TAG)
            activity?.supportFragmentManager!!.executePendingTransactions()
        }
        ivImage.setOnClickListener {
            verifyStoragePermissions(activity)
        }
        itemsAdapter = PropertiesAdapter(context!!)
        rvProps.adapter = itemsAdapter
        rvProps.layoutManager = GridLayoutManager(context!!, 1)
        if (!args.isNew) {
            product = Gson().fromJson(args.productData, ProductWithProps::class.java)
            if (product.product.image != null) {
                val bitmap = ImageUtils.getBitmapFromPath(product.product.image!!)
                if (bitmap != null) {
                    ivImage.setImageBitmap(bitmap)
                }
            }
            imageString = product.product.image
            etProductName.setText(product.product.name)
            tvProductCount.text = product.product.count.toString()
            etSellPrice.setText(product.product.salesPrice.toString())
            etActualPrice.setText(product.product.costPrice.toString())
            tvAddedDate.text = dateFormat.format(Date(product.product.crDate!!))
            if (product.propertyValues.isNotEmpty()) {
                itemsAdapter.reassignItems(product.propertyValues.toMutableList())
            }
        } else {

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (hasAllPermissionsGranted(grantResults)) {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
        } else {
            Log.d("Denied", "denied")
        }
    }

    private fun hasAllPermissionsGranted(grantResults: IntArray): Boolean {
        for (grantResult in grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("res", "result_code is: " + resultCode + "request code is: " + requestCode)
        if (resultCode == RESULT_OK && requestCode == 100) {
            getImagePathIntent.onNext(data?.data!!)
        }
    }

    override fun createPresenter() = SingleProductPresenter(context!!)

    override fun receivePropertiesIntent(): Observable<List<Property>> {
        return itemAdded
    }

    override fun saveProductIntent(): Observable<ProductToSave> {
        return RxView.clicks(btnAddItem).map {
            ProductToSave(
                Product(
                    id = if (this::product.isInitialized) product?.product.id else null,
                    name = etProductName.text.toString(),
                    count = tvProductCount.text.toString().toInt(),
                    salesPrice = etSellPrice.text.toString().toDouble(),
                    costPrice = etActualPrice.text.toString().toDouble(),
                    crDate = if (this::product.isInitialized) product?.product.crDate else System.currentTimeMillis(),
                    image = imageString
                ), itemsAdapter.getItems(), args.isNew
            )
        }
    }

    override fun getImagePathIntent(): Observable<Uri> {
        return getImagePathIntent
    }

    override fun render(state: SingleProductState) {
        when (state) {
            is SingleProductState.PropertyReceived -> {
                Log.d("tag", state.property.toString())
                itemsAdapter.addItems(state.property)
            }
            is SingleProductState.ProductSaved -> {
                activity?.onBackPressed()
            }
            is SingleProductState.ImagePicked -> {
                Log.d("here", "Triggered")
                imageString = state.image
                val bitmap = ImageUtils.getBitmapFromPath(state.image)
                if (bitmap != null) {
                    Log.d("here", "Triggered bot by")
                    ivImage.setImageBitmap(bitmap)
                }
            }
        }
    }

}