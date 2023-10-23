package uz.itschool.housesales.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.itschool.housesales.OnClick
import uz.itschool.housesales.R
import uz.itschool.housesales.databinding.ProductMiniItemBinding
import uz.itschool.housesales.model.CartProduct
import uz.itschool.housesales.model.Product
import uz.itschool.housesales.model.ProductType
import kotlin.collections.ArrayList

class SmallProductAdapter(var products:ArrayList<Product>?,var cartProducts:ArrayList<CartProduct>?,var vartype:ProductType,var click: OnClick):RecyclerView.Adapter<SmallProductAdapter.MyHolder>() {
    class MyHolder(view: View):RecyclerView.ViewHolder(view) {
        var name=view.findViewById<TextView>(R.id.mini_product_name)
        var brand =view.findViewById<TextView>(R.id.mini_brand_name)
        var rating=view.findViewById<TextView>(R.id.mini_product_rating)
        var image=view.findViewById<ImageView>(R.id.mini_product_image)
        var card=view.findViewById<CardView>(R.id.mini_product_card)
        var price= view.findViewById<TextView>(R.id.mini_product_price)
        var discount= view.findViewById<TextView>(R.id.discount)

        companion object {
            fun create(viewGroup: ViewGroup): MyHolder {
                return MyHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.product_mini_item, viewGroup, false))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return when(vartype){
            ProductType.CART_PRODUCT->cartProducts!!.size
            ProductType.SIMPLE_PRODUCT->products!!.size
        }
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        if (vartype==ProductType.SIMPLE_PRODUCT){
        var product=products!![position]
        holder.name.text=product.title
        holder.price.text=product.price.toString()+"$"
        holder.brand.text=product.brand
        holder.discount.visibility=View.GONE
        holder.rating.text=product.rating.toString()
        holder.image.load(product.images[0])
            holder.card.setOnClickListener {
                click.click(product)
            }
        }
        else{
            var product=cartProducts!![position]
            holder.name.text=product.title
            holder.price.text=product.price.toString()+"$"
            holder.brand.text="Items left:"+product.quantity
            holder.rating.visibility=View.GONE
            holder.image.visibility=View.GONE
        }

    }

}