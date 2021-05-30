package com.am.virtualfridge.db

import android.util.Log
import com.am.virtualfridge.receipts.Receipt
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class FirebaseReceipts {
    /**
     * metody odpowiedzialne za prawidlowa obsluge przepisow w firebasie
     */
    companion object {
        private val firebase: FirebaseDatabase = FirebaseDatabase.getInstance("https://virtualfridge-47aca-default-rtdb.europe-west1.firebasedatabase.app/")
        private val user = FirebaseAuth.getInstance().currentUser!!.uid
        private var myRef = firebase.getReference("receipts").child(user)

        fun addReceipt(receipt: Receipt) {
            /**
             * dodaje dana recepte do firebasu
             */
            val query = myRef.orderByChild("name").equalTo(receipt.name)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    myRef.child("${Date().time}").setValue(receipt)
                }

                override fun onCancelled(p0: DatabaseError) {
                    Log.e("Firebase", "Small error")
                }
            })
        }

        /**
         * mozliwosc zmiany nazwy przepisu
         */
        fun editProduct(newReceipt: Receipt, oldReceipt: Receipt) {
            val query = myRef.orderByChild("name")
                .equalTo(oldReceipt.name)

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (i in dataSnapshot.children) {
                        val item = i.getValue(Receipt::class.java)
                        if (item != null) {
                            if (item.name == oldReceipt.name) {
                                i.key?.let { myRef.child(it).setValue(Receipt(newReceipt.name, newReceipt.link)) }
                                return
                            }
                        }
                    }
                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("Firebase", "Small error")
                }
            })

        }


        /**
         * usuwam recepte o podanej nazwie z firebasu,
         */

        fun deleteReceipt(receipt: Receipt) {
            val query = myRef.orderByChild("name")
                .equalTo(receipt.name)

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (i in dataSnapshot.children) {
                        val item = i.getValue(Receipt::class.java)
                        if (item != null && receipt.link == item.link) {
                            i.key?.let { myRef.child(it).removeValue() }
                            return
                        }
                    }
                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("Firebase", "Small error")
                }
            })
        }

    }
}