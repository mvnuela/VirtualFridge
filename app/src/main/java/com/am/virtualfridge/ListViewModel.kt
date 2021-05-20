package com.am.virtualfridge

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListViewModel : ViewModel() {
   // val links = MutableLiveData<Set<String>>()
    private var myLink : String = ""
    fun addLink (link : String) {  myLink = link }
    fun getLink (): String { return myLink}


}