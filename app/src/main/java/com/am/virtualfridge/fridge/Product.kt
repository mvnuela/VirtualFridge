package com.am.virtualfridge.fridge

data class Product(val name: String = "", val amount: Int = 0, val dayOfMonth : Int = 0, val month : Int = 0, val year : Int = 0, var alarm: Boolean = false)
