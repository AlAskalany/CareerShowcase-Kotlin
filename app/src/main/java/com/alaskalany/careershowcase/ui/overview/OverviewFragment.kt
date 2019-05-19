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

package com.alaskalany.careershowcase.ui.overview

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.alaskalany.careershowcase.GlideApp
import com.alaskalany.careershowcase.R
import com.alaskalany.careershowcase.databinding.FragmentOverviewBinding
import com.alaskalany.careershowcase.ui.ScrollToTop
import org.jetbrains.annotations.Contract

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [OnOverviewFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [OverviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class OverviewFragment : Fragment(), ScrollToTop {
    
    
    private var param1: String? = null
    
    
    private var param2: String? = null
    
    
    private var onOverviewFragmentInteractionListener: OnOverviewFragmentInteractionListener? = null
    
    
    private var binding: FragmentOverviewBinding? = null
    
    /**
     * @param uri
     */
    fun onButtonPressed(uri: Uri) {
        
        if (onOverviewFragmentInteractionListener != null) {
            onOverviewFragmentInteractionListener!!.onOverviewFragmentInteraction(uri)
        }
    }
    
    /**
     * @param context
     */
    override fun onAttach(context: Context) {
        
        super.onAttach(context)
        registerListener(context)
    }
    
    /**
     * @param context
     */
    @Contract("null -> fail")
    private fun registerListener(context: Context?) {
        
        if (context is OnOverviewFragmentInteractionListener) {
            onOverviewFragmentInteractionListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnOverviewFragmentInteractionListener")
        }
    }
    
    /**
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            param1 = arguments!!.getString(ARG_PARAM1)
            param2 = arguments!!.getString(ARG_PARAM2)
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
        
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_overview, container, false)
        binding!!.name = "Ahmed AlAskalany"
        binding!!.headline = "Software Engineer"
        
        binding!!.imageButtonLinkedin.setOnClickListener { v ->
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/askalany/"))
            startActivity(browserIntent)
        }
        
        binding!!.imageButtonGithub.setOnClickListener { v ->
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/AlAskalany"))
            startActivity(browserIntent)
        }
        
        binding!!.imageButtonTwitter.setOnClickListener { v ->
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/Askalanism"))
            startActivity(browserIntent)
        }
        // Inflate the layout for this fragment
        GlideApp.with(this).load(getString(R.string.profile_picture_url)).into(binding!!.imageViewProfilePicture)
        return binding!!.root
    }
    
    /**
     * @param savedInstanceState
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        
        super.onActivityCreated(savedInstanceState)
        binding!!.executePendingBindings()
    }
    
    
    override fun onDetach() {
        
        super.onDetach()
        unregisterListener()
    }
    
    
    private fun unregisterListener() {
        
        onOverviewFragmentInteractionListener = null
    }
    
    override fun top() {
    }
    
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnOverviewFragmentInteractionListener {
        
        // TODO: Update argument type and name
        fun onOverviewFragmentInteraction(uri: Uri)
    }
    
    companion object {
        
        
        private val ARG_PARAM1 = "param1"
        
        
        private val ARG_PARAM2 = "param2"
        
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         *
         * @return A new instance of fragment OverviewFragment.
         */
        fun newInstance(param1: String, param2: String): OverviewFragment {
            
            val fragment = OverviewFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
} // Required empty public constructor
