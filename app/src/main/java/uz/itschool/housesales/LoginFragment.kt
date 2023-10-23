package uz.itschool.housesales

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.itschool.housesales.databinding.FragmentLoginBinding
import uz.itschool.housesales.model.Login
import uz.itschool.housesales.model.ProductData
import uz.itschool.housesales.model.User
import uz.itschool.housesales.preferences.Settings
import uz.itschool.housesales.retrofit.ProductApi
import uz.itschool.housesales.retrofit.ProductClient

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val productApi by lazy { ProductClient.getInstance().create(ProductApi::class.java) }
    private val settings by lazy { Settings.getData(this.requireContext()) }
    private var _binding: FragmentLoginBinding?? =null
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
        _binding= FragmentLoginBinding.inflate(inflater,container,false)

        binding.signIn.setOnClickListener{
            var login=binding.login.text.toString()
            var password=binding.signInPassword.text.toString()

            if (login== "" || password == "") {
                Toast.makeText(requireContext(), "Complete the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{
                var login=Login(login,password)
                productApi.login(login).enqueue(object :Callback<User>{
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        val body = response.body()
                        if (response.isSuccessful && body!=null){
                        settings.setUser(body)
                        parentFragmentManager.beginTransaction().setReorderingAllowed(true).replace(R.id.main,MenuFragment.newInstance(body)).commit()
                        }
                    }
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        binding.login.error
                        binding.signInPassword.error
                    }

                })
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}