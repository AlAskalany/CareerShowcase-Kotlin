/*
 * MIT License
 *
 * Copyright (c) 2018 Ahmed AlAskalany
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.alaskalany.careershowcase.ui

import android.view.MenuItem
import android.widget.Toast
import androidx.collection.SparseArrayCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.alaskalany.careershowcase.MainActivity
import com.alaskalany.careershowcase.R
import com.alaskalany.careershowcase.ui.contact.ContactListFragment
import com.alaskalany.careershowcase.ui.education.EducationListFragment
import com.alaskalany.careershowcase.ui.overview.OverviewFragment
import com.alaskalany.careershowcase.ui.skills.SkillListFragment
import com.alaskalany.careershowcase.ui.work.WorkListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

/**
 * Bottom Navigation Manager that handles [BottomNavigationView]
 */
class BottomNavigationManager
/**
 * @param fragmentActivity [MainActivity]
 */
    (
    /**
     * [MainActivity]
     */
    private val activity: FragmentActivity,
    /**
     * [BottomNavigationView]
     */
    private var navigation: BottomNavigationView?
    ) : BottomNavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemReselectedListener,
        FragmentManager.OnBackStackChangedListener, OnBackPressed {
    
    /**
     * Collection of fragments used with [BottomNavigationView]
     */
    private val fragments: SparseArrayCompat<ScrollToTop>
    
    init {
        fragments = SparseArrayCompat()
        createFragments(fragments)
        
        // Set the bottom navigation view
        setBottomNavigationView(navigation)
        
        // listen to item selection and reselection from the bottom navigation view
        navigation!!.setOnNavigationItemSelectedListener(this)
        navigation!!.setOnNavigationItemReselectedListener(this)
    }
    
    /**
     * @param navigation [BottomNavigationView]
     */
    private fun setBottomNavigationView(navigation: BottomNavigationView?) {
        
        this.navigation = navigation
    }
    
    /**
     * @param menuItem Bottom navigation menu item
     *
     * @return True if a menu item was selected
     */
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        // Replace current fragment with requested fragment
        when (menuItem.itemId) {
            R.id.navigation_overview  -> {
                replaceFragment(BottomNavigationManager.OVERVIEW)
                return true
            }
            R.id.navigation_education -> {
                replaceFragment(BottomNavigationManager.EDUCATION)
                return true
            }
            R.id.navigation_work      -> {
                replaceFragment(BottomNavigationManager.WORK)
                return true
            }
            R.id.navigation_skills    -> {
                replaceFragment(BottomNavigationManager.SKILLS)
                return true
            }
            R.id.navigation_contact   -> {
                replaceFragment(BottomNavigationManager.CONTACT)
                return true
            }
        }
        return false
    }
    
    /**
     * @param navFragment Navigation fragment
     *
     * @throws RuntimeException If the navigation fragment is null
     */
    @Throws(RuntimeException::class)
    private fun replaceFragment(navFragment: Int) {
        
        val fragment = fragments.get(navFragment)
        if (fragment != null) {
            val transaction = activity.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container_navigation, (fragment as Fragment?)!!)
            if (fragment !is OverviewFragment) {
                transaction.addToBackStack(null)
            }
            transaction.commit()
        } else {
            throw RuntimeException("Navigation fragment shouldn't be null")
        }
    }
    
    /**
     * Handles reselection of a [BottomNavigationView]'s menu item, by scrolling to the top of the screen
     *
     * @param menuItem Bottom navigation menu item
     */
    override fun onNavigationItemReselected(menuItem: MenuItem) {
        
        when (menuItem.itemId) {
            R.id.navigation_overview  ->
                // Overview menu item reselected, scroll to the top of the overview fragment
                scrollToFragmentTop(getFragment(OVERVIEW))
            R.id.navigation_education ->
                // Education menu item reselected, scroll to the top of the education fragment
                scrollToFragmentTop(getFragment(EDUCATION))
            R.id.navigation_work      ->
                // Work menu item reselected, scroll to the top of the work fragment
                scrollToFragmentTop(getFragment(WORK))
            R.id.navigation_skills    ->
                // Skills menu item reselected, scroll to the top of the skills fragment
                scrollToFragmentTop(getFragment(SKILLS))
            R.id.navigation_contact   ->
                // Contact menu item reselected, scroll to the top of the contact fragment
                scrollToFragmentTop(getFragment(CONTACT))
        }
    }
    
    /**
     * Scroll to the top of the fragment implemented [ScrollToTop]
     *
     * @param fragment Bottom navigation fragment
     */
    private fun scrollToFragmentTop(fragment: ScrollToTop) {
        
        fragment.top()
    }
    
    /**
     * @param fragmentId Fragment id
     *
     * @return Bottom navigation fragment
     */
    private fun getFragment(fragmentId: Int): ScrollToTop {
        
        return Objects.requireNonNull<ScrollToTop>(fragments.get(fragmentId))
    }
    
    /**
     * For app start, attach the [OverviewFragment]
     *
     * @param freshStart Is this a fresh activity start
     */
    fun init(freshStart: Boolean) {
        
        if (freshStart) {
            val fragment = fragments.get(OVERVIEW)
            if (fragment != null) {
                val supportFragmentManager = activity.supportFragmentManager
                val transaction = supportFragmentManager.beginTransaction()
                transaction.add(R.id.container_navigation, (fragment as Fragment?)!!)
                //transaction.addToBackStack(null);
                transaction.commit()
            }
        }
    }
    
    /**
     * Called whenever the contents of the back stack change.
     */
    override fun onBackStackChanged() {
        // TODO
        Toast.makeText(activity.applicationContext, "Backstack changed", Toast.LENGTH_SHORT).show()
    }
    
    /**
     * Handling back presses by replacing any fragment with the [OverviewFragment]
     *
     * @return true if back was pressed with a fragment other than [OverviewFragment] attached
     */
    override fun onBackPressed(): Boolean {
        // If back was pressed with a fragment other than the overview fragment attached
        if (navigation!!.selectedItemId != R.id.navigation_overview) {
            
            // Set the overview menu item in the bottom navigation view as selected
            navigation!!.menu.getItem(0).isChecked = true
            
            // Attach the overview fragment
            replaceFragment(OVERVIEW)
            return true
        }
        // If back was pressed with the overview fragment attached
        return false
    }
    
    companion object {
        
        /**
         * [OverviewFragment]
         */
        private val OVERVIEW = 0
        
        /**
         * [EducationListFragment]
         */
        private val EDUCATION = 1
        
        /**
         * [WorkListFragment]
         */
        private val WORK = 2
        
        /**
         * [SkillListFragment]
         */
        private val SKILLS = 3
        
        /**
         * [ContactListFragment]
         */
        private val CONTACT = 4
        
        private fun createFragments(mFragments: SparseArrayCompat<ScrollToTop>) {
            
            mFragments.put(OVERVIEW, OverviewFragment())
            mFragments.put(EDUCATION, EducationListFragment())
            mFragments.put(WORK, WorkListFragment())
            mFragments.put(SKILLS, SkillListFragment())
            mFragments.put(CONTACT, ContactListFragment())
        }
    }
}
