package uz.itschool.housesales

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.itschool.housesales.adapters.SmallProductAdapter
import uz.itschool.housesales.databinding.FragmentCartBinding
import uz.itschool.housesales.model.CartData
import uz.itschool.housesales.model.CartProduct
import uz.itschool.housesales.model.Product
import uz.itschool.housesales.model.ProductType
import uz.itschool.housesales.preferences.Settings
import uz.itschool.housesales.retrofit.ProductApi
import uz.itschool.housesales.retrofit.ProductClient

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CartFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentCartBinding?? =null
    private val productApi by lazy { ProductClient.getInstance().create(ProductApi::class.java)}
    private val settings by lazy { Settings.getData(this.requireContext()) }
    private val binding get() =_binding!!
    private var carts:ArrayList<CartProduct> = ArrayList<CartProduct>()
    private var smallAdapter= SmallProductAdapter(null,carts,ProductType.CART_PRODUCT,object :OnClick{
        override fun click(product: Product) {
            parentFragmentManager.beginTransaction().setReorderingAllowed(true).replace(R.id.main,ProductInfoFragment.newInstance(product)).addToBackStack("MenuFragment").commit()
        }
    })

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
        _binding=FragmentCartBinding.inflate(inflater,container,false)
        fetchCarts()

        binding.carts.adapter=smallAdapter
        return binding.root
    }
    fun fetchCarts(){
        productApi.getCartOfUser(settings.getUser()!!.id).enqueue(object
            :Callback<CartData>{
            override fun onResponse(call: Call<CartData>, response: Response<CartData>) {
                var body=response.body()
                if (response.isSuccessful && body!=null){
                    carts.clear()
                    carts.addAll(body.carts[0].products)
                    object: CountDownTimer(2000,100){
                        override fun onTick(millisUntilFinished: Long) {
                        }
                        override fun onFinish() {
                            smallAdapter.notifyDataSetChanged()
                        }
                    }.start()
                }
            }

            override fun onFailure(call: Call<CartData>, t: Throwable) {
            }
        })
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}