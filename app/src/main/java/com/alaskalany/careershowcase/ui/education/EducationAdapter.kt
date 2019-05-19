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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alaskalany.careershowcase.GlideApp
import com.alaskalany.careershowcase.R
import com.alaskalany.careershowcase.databinding.FragmentEducationBinding
import com.alaskalany.careershowcase.entity.EducationEntity

/**
 * [RecyclerView.Adapter] that can display a [EducationEntity]
 *
 *
 * Adapters provide a binding from an app-specific data set to views that are displayed
 * * within a [RecyclerView].
 * TODO: Replace the implementation with code for your data type.
 *
 * @param educationOnClickCallback Listener to clicks on Education items
 */
class EducationAdapter(private val educationOnClickCallback: EducationOnClickCallback) :
        RecyclerView.Adapter<EducationAdapter.ViewHolder>() {
    
    
    private var educationEntities: List<EducationEntity>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<FragmentEducationBinding>(LayoutInflater.from(parent.context),
                                                                        R.layout.fragment_education, parent, false)
        binding.educationOnClickCallback = educationOnClickCallback
        return ViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.education = educationEntities!![position]
        
        holder.binding.educationOnClickCallback = educationOnClickCallback
        val rootView = holder.binding.root
        GlideApp.with(rootView).load(educationEntities!![position].logoUrl).into(holder.binding.imageViewEducationLogo)
        holder.binding.executePendingBindings()
    }
    
    override fun getItemCount(): Int {
        return if (educationEntities == null) 0 else educationEntities!!.size
    }
    
    fun setEducationList(educationList: List<EducationEntity>) {
        if (educationEntities == null) {
            educationEntities = educationList
            notifyItemRangeInserted(0, educationList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return educationEntities!!.size
                }
                
                override fun getNewListSize(): Int {
                    return educationList.size
                }
                
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return educationEntities!![oldItemPosition].id == educationList[newItemPosition].id
                }
                
                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newEducation = educationList[newItemPosition]
                    val oldEducation = educationEntities!![oldItemPosition]
                    return newEducation.id == oldEducation.id && newEducation.description == oldEducation.description && newEducation.title == oldEducation.title
                }
            })
            educationEntities = educationList
            result.dispatchUpdatesTo(this)
        }
    }
    
    class ViewHolder
    internal constructor(val binding: FragmentEducationBinding) : RecyclerView.ViewHolder(binding.root)
}
