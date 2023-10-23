package uz.itschool.housesales.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.itschool.housesales.OnClick
import uz.itschool.housesales.R
import uz.itschool.housesales.databinding.FragmentLoginBinding
import uz.itschool.housesales.databinding.ProductBigItemBinding
import uz.itschool.housesales.model.Product

class BigProductAdapter(var products:ArrayList<Product>,var click: OnClick):RecyclerView.Adapter<BigProductAdapter.MyHolder>() {
    class MyHolder(binding:ProductBigItemBinding):RecyclerView.ViewHolder(binding.root) {
        var name=binding.productName
        var brand = binding.brandd
        var price=binding.price
        var rating=binding.rating
        var card=binding.bigProductCard
        var background = binding.background
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(ProductBigItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        var product = products[position]
        holder.name.text=product.title
        holder.rating.text=product.rating.toString()
        holder.brand.text=product.brand
        holder.price.text=product.price.toString()+"$"
        holder.background.load(product.images[0])
        holder.card.setOnClickListener {
            click.click(product)
        }
    }
}