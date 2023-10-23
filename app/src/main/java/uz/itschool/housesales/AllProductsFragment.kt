package uz.itschool.housesales

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.itschool.housesales.adapters.CategoryAdapter
import uz.itschool.housesales.adapters.SmallProductAdapter
import uz.itschool.housesales.databinding.FragmentAllProductsBinding
import uz.itschool.housesales.databinding.FragmentMenuBinding
import uz.itschool.housesales.model.Product
import uz.itschool.housesales.model.ProductData
import uz.itschool.housesales.model.ProductType
import uz.itschool.housesales.retrofit.ProductApi
import uz.itschool.housesales.retrofit.ProductClient
import java.util.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AllProductsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AllProductsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val random = Random()
    private val productApi by lazy { ProductClient.getInstance().create(ProductApi::class.java)}
    private var products:ArrayList<Product> = ArrayList<Product>()
    private val smallAdapter by lazy { SmallProductAdapter(products,null,ProductType.SIMPLE_PRODUCT,object :OnClick{
        override fun click(product: Product) {

        }
    }) }
    private var categories:ArrayList<String> = ArrayList<String>()
    private val categoryAdapter by lazy { CategoryAdapter(categories,object : CategoryAdapter.CategoryClick{
        override fun click(category: String) {
            filter(category)
        }
    }) }
    private var _binding: FragmentAllProductsBinding?? =null
    private val binding get() =_binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding= FragmentAllProductsBinding.inflate(inflater,container,false)
        binding.categoryRv.adapter=categoryAdapter
        binding.products.adapter=smallAdapter


        productApi.getProducts().enqueue(object : Callback<ProductData> {
            override fun onResponse(call: Call<ProductData>, response: Response<ProductData>) {
                var body = response.body()
                if (response.isSuccessful && body!=null) {
                    products.addAll(body.products)
                }
            }
            override fun onFailure(call: Call<ProductData>, t: Throwable) {
                Log.d("Failure", "onFailure: $t")
            }
        })
        productApi.getCategories().enqueue(object :Callback<List<String>>{
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                var body = response.body()
                if (response.isSuccessful && body!=null) {
                    categories.addAll(body)
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.d("Failure", "onFailure: $t")
            }

        })
        object: CountDownTimer(1400,100){
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                smallAdapter.notifyDataSetChanged()
                categoryAdapter.notifyDataSetChanged()
            }
        }.start()


        binding.text.addTextChangedListener {
        if (!binding.text.text.isNullOrEmpty()){
            productApi.getProductsBySearch(binding.text.text.toString()).enqueue(object :Callback<ProductData>{
                override fun onResponse(call: Call<ProductData>, response: Response<ProductData>) {
                    var body = response.body()
                    if (response.isSuccessful && body!=null) {
                        products.clear()
                        products.addAll(body.products)
                        smallAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<ProductData>, t: Throwable) {
                }
            })
        }
    }
        return binding.root

    }
    fun filter(category:String){
        productApi.getOneCategoryProduct(category).enqueue(object :Callback<ProductData>{
            override fun onResponse(call: Call<ProductData>, response: Response<ProductData>) {
                var body = response.body()
                if (response.isSuccessful && body!=null) {
                    products.clear()
                    products.addAll(body.products)
                    smallAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ProductData>, t: Throwable) {
            }
        })

    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AllProductsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}