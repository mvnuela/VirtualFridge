package com.am.virtualfridge

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.am.virtualfridge.db.FirebaseFridge
import com.am.virtualfridge.fridge.MyFridgeFragment
import com.am.virtualfridge.receipts.ReceiptsFragment
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    private lateinit var username : TextView
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var reference: DatabaseReference
    private lateinit var viewpagerAdapter: ViewPagerAdapter
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseFridge.giveUsername()
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        username = findViewById(R.id.username)
        firebaseUser= FirebaseAuth.getInstance().currentUser!!
        reference = FirebaseDatabase.getInstance("https://virtualfridge-47aca-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users").child(firebaseUser.uid)

        val tableLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        viewpagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewpagerAdapter.addFragment(MyFridgeFragment(),"MyFridge")
        viewpagerAdapter.addFragment(WebPageFragment(),"Search recepits")
        viewpagerAdapter.addFragment(ReceiptsFragment(),"Fav recepits")
        viewPager.adapter=viewpagerAdapter
        tableLayout.setupWithViewPager(viewPager)

        /**
         * zmienilem na liveData
         */
        val nameObserver = Observer<String> {
            username.text = it
        }

        FirebaseFridge.username.observe(this, nameObserver)

    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.logout ->{
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this,StartActivity::class.java))
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }


    class ViewPagerAdapter (manager: FragmentManager) : FragmentPagerAdapter(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        var fragments : ArrayList<Fragment> = ArrayList()
        var titles : ArrayList<String> = ArrayList()

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getItem(position: Int): Fragment {
            return fragments.get(position)
        }

        fun addFragment(fragment: Fragment, title : String){
            fragments.add(fragment)
            titles.add(title)

        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles.get(position)
        }

    }
 }



