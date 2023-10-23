package uz.itschool.housesales.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.itschool.housesales.OnClick
import uz.itschool.housesales.databinding.CategoryItemBinding
import java.util.ArrayList

class CategoryAdapter(var categories:ArrayList<String>,var click:CategoryAdapter.CategoryClick):RecyclerView.Adapter<CategoryAdapter.MyHolder>() {
    class MyHolder (binding:CategoryItemBinding):RecyclerView.ViewHolder(binding.root){
        var name=binding.categoryName
        var card=binding.categoryItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.name.text=categories[position]
        holder.card.setOnClickListener {
            click.click(categories[position])
        }
    }
    interface CategoryClick{
        fun click(category:String)
    }
}