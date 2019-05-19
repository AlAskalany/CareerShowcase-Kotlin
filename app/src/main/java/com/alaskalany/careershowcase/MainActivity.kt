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

package com.alaskalany.careershowcase

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alaskalany.careershowcase.databinding.ActivityMainBinding
import com.alaskalany.careershowcase.ui.BottomNavigationManager
import com.alaskalany.careershowcase.ui.overview.OverviewFragment

/**
 * Main activity
 */
class MainActivity : AppCompatActivity(), OverviewFragment.OnOverviewFragmentInteractionListener {
    
    internal lateinit var binding: ActivityMainBinding
    private var bottomNavigationManager: BottomNavigationManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        bottomNavigationManager = BottomNavigationManager(this, binding.navigation)
        bottomNavigationManager!!.init(savedInstanceState == null)
        val networkHandler = NetworkHandler(applicationContext)
        networkHandler.run()
    }
    
    /**
     * Interface [OverviewFragment.OnOverviewFragmentInteractionListener]
     * for interacting with [OverviewFragment]
     *
     * @param uri uri
     */
    override fun onOverviewFragmentInteraction(uri: Uri) {
        // TODO handle interaction with the OverviewFragment from MainActivity
    }
    
    override fun onBackPressed() {
        if (!bottomNavigationManager!!.onBackPressed()) {
            // TODO make sure this is the write way to handle back-press
            //getSupportFragmentManager().popBackStack();
            finish()
        }
    }
}
