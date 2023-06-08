package com.example.personelkayit.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.example.personelkayit.R
import com.example.personelkayit.adapter.ListPagerAdapter
import com.example.personelkayit.databinding.ActivityListBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ListActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var binding: ActivityListBinding

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()

        // View pager ve Tablayout için

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        val viewadapter = ListPagerAdapter(this)
        viewPager.adapter = viewadapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val tabText = when(position) {
                0 -> "Personnel List"
                1 -> "Search"
                else -> "Personnel List"
            }
            tab.text = tabText
        }.attach()

        // View pager ve Tablayout için



        binding.postImage.setOnClickListener {

            val intent = Intent(applicationContext, PersonnelAddActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}