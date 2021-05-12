package com.am.virtualfridge

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.am.virtualfridge.databinding.DialogAddProductBinding
import java.util.*


// tworze piekny dialog

class AddProductDialog(context: Context) : AppCompatDialog(context) {

    // dodaje rozne takie

    private lateinit var binding: DialogAddProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tvAdd.setOnClickListener {
            val name = binding.etName.text.toString()
            val amount = binding.etAmount.text.toString()

            if(name.isEmpty()) {
                Toast.makeText(context, "Please enter a name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(amount.isEmpty()) {
                Toast.makeText(context, "Please enter quantity", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //robie zeby wszystko bylo z malej litery
            val item = Product(name.toLowerCase(Locale.ROOT), amount.toInt())
            MyFridgeActivity.addUpdateProduct(item)
            dismiss()
        }

        binding.tvCancel.setOnClickListener {
            cancel()
        }
    }
}