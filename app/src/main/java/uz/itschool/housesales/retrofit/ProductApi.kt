package uz.itschool.housesales.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import uz.itschool.housesales.model.CartData
import uz.itschool.housesales.model.Login
import uz.itschool.housesales.model.Product
import uz.itschool.housesales.model.ProductData
import uz.itschool.housesales.model.User
import retrofit2.http.Path as Path

interface ProductApi {
    @GET("/products")
    fun getProducts():Call<ProductData>

    @GET("/products/categories")
    fun getCategories():Call<List<String>>

    @GET("/products")
    fun getRecomended(@Query("limit")limit:Int,@Query("skip")skip:Int):Call<ProductData>

    @GET("/products/category/{category}")
    fun getOneCategoryProduct(@Path("category") category:String ):Call<ProductData>

    @GET("/products/search")
    fun getProductsBySearch(@Query("q") query : String): Call<ProductData>

    @POST("/auth/login")
    fun login(@Body login: Login):Call<User>

    @GET("/carts/user/{id}")
    fun getCartOfUser(@Path("id") id:Int):Call<CartData>
}