package uz.itschool.housesales

import uz.itschool.housesales.model.Product

interface OnClick {
    fun click(product: Product)
}