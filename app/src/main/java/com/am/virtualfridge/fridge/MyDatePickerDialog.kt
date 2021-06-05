package com.am.virtualfridge.fridge

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.widget.DatePicker
import androidx.core.content.ContextCompat.getSystemService
import com.am.virtualfridge.fridge.AddProductDialog.Companion.selectedDay
import com.am.virtualfridge.fridge.AddProductDialog.Companion.selectedMonth
import com.am.virtualfridge.fridge.AddProductDialog.Companion.selectedYear
import java.util.*

/**
 * nadpisuje DatePickerDialog, jedynie co zmieniam, ze przypisuje date do companion object w AddProductDialog
 */

class MyDatePickerDialog(context: Context, private var year: Int, private var month: Int, private var dayOfMonth: Int ) : DatePickerDialog(context, DateListener(), year, month, dayOfMonth) {

    override fun onDateChanged(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        super.onDateChanged(view, year, month, dayOfMonth)
        this.year = year
        this.month = month
        this.dayOfMonth = dayOfMonth
    }

    override fun onClick(dialog: DialogInterface, which: Int) {
        super.onClick(dialog, which)
        selectedDay = dayOfMonth
        selectedMonth = month + 1
        selectedYear = year
    }

    override fun updateDate(year: Int, month: Int, dayOfMonth: Int) {
        super.updateDate(year - 1, month, dayOfMonth)
    }

     class DateListener : OnDateSetListener {
        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        }
    }

}