package com.am.virtualfridge.receipts

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.am.virtualfridge.databinding.DialogAddReceiptBinding
import com.am.virtualfridge.db.FirebaseReceipts
import java.util.*


/**
 * tworze dialog, w ktorym podaje sie nazwe przepisu
 * i umozliwiam dodanie do bazy danych
 */
class AddReceiptDialog(context: Context, private val link: String) : AppCompatDialog(context) {
    private lateinit var binding: DialogAddReceiptBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogAddReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvAdd.setOnClickListener {
            val name = binding.etName.text.toString()
            if(name.isEmpty()) {
                Toast.makeText(context, "Please enter a name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val item = Receipt(name.toLowerCase(Locale.ROOT), link)
            FirebaseReceipts.addReceipt(item)
            dismiss()

        }

        binding.tvCancel.setOnClickListener {
            cancel()
        }

    }
}