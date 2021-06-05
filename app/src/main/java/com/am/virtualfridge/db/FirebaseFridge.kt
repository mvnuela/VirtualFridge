package com.am.virtualfridge.db

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.am.virtualfridge.fridge.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class FirebaseFridge {
    /**
     * wykorzysutje metode companion object, ktora dziala podobnie jak static w incie, zeby miec łatwy dostep do firebasu z innych klas
     * kazdy uzytkownik posiada inna lodowke
     */
    companion object {
        var username = MutableLiveData<String> ("")
        private val firebase: FirebaseDatabase = FirebaseDatabase.getInstance("https://virtualfridge-47aca-default-rtdb.europe-west1.firebasedatabase.app/")
        val user = FirebaseAuth.getInstance().currentUser!!.uid
        var myRef = firebase.getReference("productsInFridge").child(user)
        fun addUpdateProduct(product: Product) {
            /**
             * sprawdzam czy produkt o pdanej nazwie i dacie waznosci znajduje sie w fireabasie
             */
            val query = myRef.orderByChild("name").equalTo(product.name)
            /**
             * jezeli dany produkt sie pojawia, to zwiekszam jego illosc w bazie danych w product.amount + item.amount
             * a jezeli nie to dadaje go do bazy danych
             */
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (i in dataSnapshot.children) {
                        val item = i.getValue(Product::class.java)
                        if (item != null) {
                            //robie, zeby produkty o tym samych parametrach sie zsumowaly, niestety w zjeba... realtime firebasie byl z tym problem i nie mozna bylo uzyc w kwerendzie, firestore > realtime
                            if (item.dayOfMonth == product.dayOfMonth && item.month == product.month && item.year == product.year) {
                                i.key?.let { myRef.child(it).setValue(Product(product.name, product.amount + item.amount, product.dayOfMonth, product.month, product.year)) }
                            } else {
                                myRef.child("${Date().time}").setValue(product)
                            }
                            return
                        }
                    }
                    myRef.child("${Date().time}").setValue(product)
                }

                override fun onCancelled(p0: DatabaseError) {
                    Log.e("Firebase", "Small error")
                }
            })
        }

        /**
         * mozliwosc zmiany parametrow produkty, jak ilosci czy daty waznosci
         */
        fun editProduct(newProduct: Product, oldProduct: Product) {
            val query = myRef.orderByChild("name")
                .equalTo(oldProduct.name)

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (i in dataSnapshot.children) {
                        val item = i.getValue(Product::class.java)
                        if (item != null) {
                            if (item.dayOfMonth == oldProduct.dayOfMonth && item.month == oldProduct.month && item.year == oldProduct.year) {
                                i.key?.let { myRef.child(it).setValue(Product(newProduct.name, newProduct.amount, newProduct.dayOfMonth, newProduct.month, newProduct.year)) }
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

        fun deleteProduct(product: Product) {
            val query = myRef.orderByChild("name")
                .equalTo(product.name)

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (i in dataSnapshot.children) {
                        val item = i.getValue(Product::class.java)
                        if (item != null) {
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

        /**
         * zwracam nick biezacego uzytkownika
         */
        fun giveUsername() {
            val query = firebase.getReference("users").child(user)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    username.value = dataSnapshot.children.last().value.toString()
                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("Firebase", "Small error")
                }
            })
        }
    }
}