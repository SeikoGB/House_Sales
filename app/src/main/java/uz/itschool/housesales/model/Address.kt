package uz.itschool.housesales.model

data class Address(
    val address:String,
    val city:String,
    val coordinaties: Coordinaties,
    val postalCode:String,
    val state:String
)