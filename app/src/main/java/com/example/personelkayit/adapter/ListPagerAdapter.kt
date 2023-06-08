package com.example.personelkayit.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.personelkayit.activity.ListActivity
import com.example.personelkayit.fragment.PersonnelListFragment
import com.example.personelkayit.fragment.SearchFragment

class ListPagerAdapter(fragmentActivity: ListActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> PersonnelListFragment()
            1 -> SearchFragment()
            else -> PersonnelListFragment()
        }
    }
}