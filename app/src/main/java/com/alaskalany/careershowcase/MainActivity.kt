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
    
    /**
     * Activity layout bindings
     */
    internal lateinit var binding: ActivityMainBinding
    
    /**
     * Bottom navigation manager [BottomNavigationManager]
     */
    private var bottomNavigationManager: BottomNavigationManager? = null
    
    /**
     * Called when the activity is starting.  This is where most initialization
     * should go: calling [.setContentView] to inflate the
     * activity's UI, using [.findViewById] to programmatically interact
     * with widgets in the UI, calling
     * [.managedQuery] to retrieve
     * cursors for data being displayed, etc.
     *
     * You can call [.finish] from within this function, in
     * which case onDestroy() will be immediately called after {onCreate} without any of the
     * rest of the activity lifecycle ([.onStart], [.onResume], [.onPause], etc)
     * executing.
     *
     * *Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.*
     *
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in [.onSaveInstanceState].  ***Note: Otherwise it is null.***
     *
     * @see .onStart
     *
     * @see .onSaveInstanceState
     *
     * @see .onRestoreInstanceState
     *
     * @see .onPostCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        
        super.onCreate(savedInstanceState)
        
        // Bind to layout
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        
        // Instantiate the bottom navigation manager
        bottomNavigationManager = BottomNavigationManager(this, binding.navigation)
        
        // Set bottom navigation to first fragment
        bottomNavigationManager!!.init(savedInstanceState == null)
        
        // Handle connectivity
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
    
    /**
     * Called when the activity has detected the user's press of the back
     * key.  The default implementation simply finishes the current activity,
     * but you can override this to do whatever you want.
     */
    override fun onBackPressed() {
        
        if (!bottomNavigationManager!!.onBackPressed()) {
            // TODO make sure this is the write way to handle back-press
            //getSupportFragmentManager().popBackStack();
            finish()
        }
    }
    
    companion object {
        
        /**
         * Debugging tag
         */
        val TAG = "MainActivity"
    }
}
