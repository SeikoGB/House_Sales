package uz.itschool.housesales.model

data class Cart (
    val discountedTotal: Int,
    val id: Int,
    val products: List<CartProduct>,
    val total: Int,
    val totalProducts: Int,
    val totalQuantity: Int,
    val userId: Int
)