package com.am.virtualfridge.fridge

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog
import com.am.virtualfridge.databinding.DialogEditProductBinding
import com.am.virtualfridge.db.Firebase
import com.am.virtualfridge.fridge.AddProductDialog.Companion.selectedDay
import com.am.virtualfridge.fridge.AddProductDialog.Companion.selectedMonth
import com.am.virtualfridge.fridge.AddProductDialog.Companion.selectedYear
import java.util.*

/**
 * klasa umozliwia zmiane ilosci produktu, badz daty jego dodania
 */
class EditProductDialog(context: Context, private val oldProduct: Product) : AppCompatDialog(context) {


    private lateinit var binding: DialogEditProductBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)


        selectedDay = oldProduct.dayOfMonth
        selectedMonth = oldProduct.month
        selectedYear = oldProduct.year

        binding.tvName.text = oldProduct.name.toUpperCase(Locale.ROOT)
        binding.etAmount.setText(oldProduct.amount.toString())


        binding.ivPlus.setOnClickListener {
            binding.etAmount.setText(
                (binding.etAmount.text.toString().toInt() + 1).toString()
            )
        }

        binding.ivMinus.setOnClickListener {
            if (binding.etAmount.text.toString().toInt() > 1) {
                binding.etAmount.setText(
                    (binding.etAmount.text.toString().toInt() - 1).toString()
                )
            }
        }

        binding.ivDelete.setOnClickListener {
            val item = Product(binding.tvName.text.toString().toLowerCase(Locale.ROOT),
                binding.etAmount.text.toString().toInt())
            Firebase.deleteProduct(item)
            dismiss()
        }

        binding.tvEdit.setOnClickListener {
            val item = Product(oldProduct.name.toLowerCase(Locale.ROOT),
                binding.etAmount.text.toString().toInt(),
                selectedDay,
                selectedMonth,
                selectedYear
            )
            Firebase.editProduct(item, oldProduct)
            dismiss()
        }

        binding.ivDate.setOnClickListener {
            if (oldProduct.dayOfMonth == 0 || oldProduct.month == 0 || oldProduct.year == 0) {
                val calendar = Calendar.getInstance()
                MyDatePickerDialog(context, calendar.get(1), calendar.get(2), calendar.get(5)).show()
            } else {
                MyDatePickerDialog(context, oldProduct.year, oldProduct.month - 1, oldProduct.dayOfMonth).show()
            }
        }

    }
}