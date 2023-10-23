package uz.itschool.housesales.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.itschool.housesales.R

class ImageAdapter(var images:ArrayList<String>): RecyclerView.Adapter<ImageAdapter.MyHolder>(){
    class MyHolder(view: View):RecyclerView.ViewHolder(view){
        var image=view.findViewById<ImageView>(R.id.product_imagee)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.image_item,parent,false))
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.image.load(images[position])
    }
}