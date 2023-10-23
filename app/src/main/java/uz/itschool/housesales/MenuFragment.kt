package uz.itschool.housesales

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.itschool.housesales.adapters.BigProductAdapter
import uz.itschool.housesales.adapters.CategoryAdapter
import uz.itschool.housesales.adapters.SmallProductAdapter
import uz.itschool.housesales.databinding.FragmentMenuBinding
import uz.itschool.housesales.model.Product
import uz.itschool.housesales.model.ProductData
import uz.itschool.housesales.model.ProductType
import uz.itschool.housesales.model.User
import uz.itschool.housesales.preferences.Settings
import uz.itschool.housesales.retrofit.ProductApi
import uz.itschool.housesales.retrofit.ProductClient
import java.util.Random
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
class MenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: User? = null
    private val productApi by lazy { ProductClient.getInstance().create(ProductApi::class.java)}
    private val settings by lazy { Settings.getData(this.requireContext()) }
    private var _binding: FragmentMenuBinding?? =null
    private val binding get() =_binding!!
    private var products:ArrayList<Product> = ArrayList<Product>()
    val random = Random()
    private var recommended:ArrayList<Product> = ArrayList<Product>()
    private val bigAdapter = BigProductAdapter(products,object:OnClick{
        override fun click(product: Product) {
            parentFragmentManager.beginTransaction().setReorderingAllowed(true).replace(R.id.main,ProductInfoFragment.newInstance(product)).addToBackStack("MenuFragment").commit()
        }
    })
    private var smallAdapter=SmallProductAdapter(recommended,null,ProductType.SIMPLE_PRODUCT,object :OnClick{
        override fun click(product: Product) {
            parentFragmentManager.beginTransaction().setReorderingAllowed(true).replace(R.id.main,ProductInfoFragment.newInstance(product)).addToBackStack("MenuFragment").commit()
        }
    })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as User
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding= FragmentMenuBinding.inflate(inflater,container,false)
        binding.profile.text="  "+settings.getUser()!!.firstName
        setAdapters()
        fetchProducts()

        fetchRecomended()
        object: CountDownTimer(2000,100){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                bigAdapter.notifyDataSetChanged()
                smallAdapter.notifyDataSetChanged()
            }
        }.start()
        binding.searching.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main,AllProductsFragment()).setReorderingAllowed(true).addToBackStack("MenuFragment").commit()
        }
        binding.seeAll.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.main,AllProductsFragment()).setReorderingAllowed(true).addToBackStack("MenuFragment").commit()
        }
        binding.cart.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main,CartFragment()).setReorderingAllowed(true).addToBackStack("MenuFragment").commit()
        }


        var dialog = Dialog(requireContext())
        var dialogView=layoutInflater.inflate(R.layout.logout_dialog,null)
        var btnyes=dialogView.findViewById<MaterialButton>(R.id.btn_yes)
        var btnno=dialogView.findViewById<MaterialButton>(R.id.btn_no)
        binding.logout.setOnClickListener {
            dialog.setContentView(dialogView)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.GRAY))
            btnno.setOnClickListener {
                dialog.dismiss()
            }
            btnyes.setOnClickListener {
                settings.logout()
                dialog.dismiss()
                parentFragmentManager.beginTransaction().replace(R.id.main,LoginFragment()).setReorderingAllowed(true).commit()

            }
            dialog.show()
        }
        return binding.root
    }
    fun setAdapters(){
        binding.bigProductRv.adapter=bigAdapter
        binding.miniProductRv.adapter=smallAdapter
    }
    fun fetchProducts(){
        productApi.getProducts().enqueue(object :Callback<ProductData>{
            override fun onResponse(call: Call<ProductData>, response: Response<ProductData>) {
                var body = response.body()
                if (response.isSuccessful && body!=null) {
                    products.clear()
                    products.addAll(body.products)
                }
            }
            override fun onFailure(call: Call<ProductData>, t: Throwable) {

            }
        })
    }
    fun rand(from: Int, to: Int) : Int {
        return random.nextInt(to - from) + from
    }
    fun fetchRecomended(){
        productApi.getRecomended(10,rand(0,90)).enqueue(object :Callback<ProductData>{
            override fun onResponse(call: Call<ProductData>, response: Response<ProductData>) {
                var body = response.body()
                if (response.isSuccessful && body!=null) {
                    recommended.clear()
                    recommended.addAll(body.products)
                }
            }

            override fun onFailure(call: Call<ProductData>, t: Throwable) {

            }
        })
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: User) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}