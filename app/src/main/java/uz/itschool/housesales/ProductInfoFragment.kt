package uz.itschool.housesales

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.itschool.housesales.adapters.ImageAdapter
import uz.itschool.housesales.databinding.FragmentMenuBinding
import uz.itschool.housesales.databinding.FragmentProdectInfoBinding
import uz.itschool.housesales.model.Product

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Product? = null
    lateinit var product:Product
    private var _binding: FragmentProdectInfoBinding?? =null
    private val binding get() =_binding!!
    var images:ArrayList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Product
            product=param1!!
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        images.addAll(product.images)
        var imageAdapter=ImageAdapter(images)
        _binding=FragmentProdectInfoBinding.inflate(inflater,container,false)
        binding.images.adapter = imageAdapter
        binding.title.text=product.title
        binding.brand.text=product.brand
        binding.price.text=product.price.toString()
        binding.discount.text="Special dicount for "+product.discountPercentage.toString()+"%"
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Product) =
            ProductInfoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}