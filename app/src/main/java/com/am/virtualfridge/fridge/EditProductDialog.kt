package com.am.virtualfridge.fridge

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog
import com.am.virtualfridge.databinding.DialogEditProductBinding
import java.util.*

class EditProductDialog(context: Context, val name: String, private val amount: Int) : AppCompatDialog(context) {


    private lateinit var binding: DialogEditProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvName.text = name.toUpperCase(Locale.ROOT)
        binding.etAmount.setText(amount.toString())


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
            MyFridgeActivity.deleteProduct(item)
            dismiss()
        }

        binding.tvEdit.setOnClickListener {
            val item = Product(binding.tvName.text.toString().toLowerCase(Locale.ROOT),
                binding.etAmount.text.toString().toInt())
            MyFridgeActivity.editProduct(item)
            dismiss()
        }
    }
}