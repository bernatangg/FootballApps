package com.result.isoftscore.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class BasePageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    private val fragmentList = mutableListOf<Fragment>()
    private val pageTitleList = mutableListOf<String>()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return pageTitleList[position]
    }

    fun addFragment(fragment: Fragment, pageTitle: String) {
        fragmentList.add(fragment)
        pageTitleList.add(pageTitle)
    }
}

