package com.am.virtualfridge.fridge

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.am.virtualfridge.databinding.DialogAddProductBinding
import java.util.*


/**
 * tworze dialog, w ktorym podaje sie nazwe produktu, jego ilosc i ewentualna date waznosci
 * i daje mozliwosc dodania produktu do bazy danych
 */

class AddProductDialog(context: Context) : AppCompatDialog(context) {
    private lateinit var binding: DialogAddProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedYear = 0
        selectedMonth = 0
        selectedDay = 0

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
            val item = Product(name.toLowerCase(Locale.ROOT), amount.toInt(), selectedDay, selectedMonth, selectedYear)
            MyFridgeFragment.addUpdateProduct(item)
            dismiss()
        }

        binding.tvCancel.setOnClickListener {
            cancel()
        }
        /**
         * mozliwosc dodania dazy waznosci produktu, w przyapdku gdy uzytkownik nie kliknie na kalendarz
         * daja jest ustawiona na 0-0-0 i nie bedzie uwzgledniona przy powiadomieniach
         */
        var first = true
        binding.ivCalendar.setOnClickListener {
            if (first) {
                val calendar = Calendar.getInstance()
                selectedDay = calendar.get(5)
                selectedMonth = calendar.get(2) + 1
                selectedYear = calendar.get(1)
                Log.d("Data", "Halo")
                first = false
            }
            MyDatePickerDialog(context, selectedYear, selectedMonth - 1, selectedDay).show()
        }
    }

    companion object {
        var selectedDay = 0
        var selectedMonth = 0
        var selectedYear = 0
    }

}