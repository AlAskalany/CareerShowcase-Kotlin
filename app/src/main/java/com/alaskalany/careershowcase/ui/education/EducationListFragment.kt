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

package com.alaskalany.careershowcase.ui.education

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alaskalany.careershowcase.R
import com.alaskalany.careershowcase.databinding.FragmentEducationListBinding
import com.alaskalany.careershowcase.model.Education
import com.alaskalany.careershowcase.ui.ScrollToTop
import com.alaskalany.careershowcase.viewmodel.EducationListViewModel

/**
 * A fragment representing a list of Items.
 *
 *
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class EducationListFragment : Fragment(), ScrollToTop, EducationOnClickCallback {
    
    override fun onClick(item: Education) {
        Toast.makeText(this@EducationListFragment.context, "Clicked on EducationEntity Item", Toast.LENGTH_SHORT).show()
    }
    
    
    private var binding: FragmentEducationListBinding? = null
    
    
    /**
     * @return
     */
    /**
     * @param adapter
     */
    protected var adapter: EducationAdapter? = null
    
    
    /**
     * @return
     */
    /**
     * @param mColumnCount
     */
    protected var columnCount = 1
    
    /**
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            columnCount = arguments!!.getInt(ARG_COLUMN_COUNT)
        }
    }
    
    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     *
     * @return
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_education_list, container, false)
        adapter = EducationAdapter(this)
        val context = binding!!.root.context
        if (columnCount <= 1) {
            binding!!.listEducation.layoutManager = LinearLayoutManager(context)
        } else {
            binding!!.listEducation.layoutManager = GridLayoutManager(context, columnCount)
        }
        binding!!.listEducation.adapter = adapter
        return binding!!.root
    }
    
    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * [.setRetainInstance] to retain their instance,
     * as this skillOnClickCallback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after [.onCreateView]
     * and before [.onViewStateRestored].
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        
        super.onActivityCreated(savedInstanceState)
        val _model = ViewModelProviders.of(this).get(EducationListViewModel::class.java)
        binding!!.educationListViewModel = _model
        _model.educations.observe(this, Observer { pWorkEntities ->
            if (pWorkEntities != null) {
                adapter!!.setEducationList(pWorkEntities)
            } else {
            }
            binding!!.executePendingBindings()
        })
    }
    
    override fun top() {
        
        binding!!.listEducation.smoothScrollToPosition(0)
    }
    
    companion object {
        
        
        protected val ARG_COLUMN_COUNT = "column-count"
        
        /**
         * @param columnCount
         *
         * @return
         */
        fun newInstance(columnCount: Int): EducationListFragment {
            
            val fragment = EducationListFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}
