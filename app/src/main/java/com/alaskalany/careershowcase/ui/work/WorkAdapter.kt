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

package com.alaskalany.careershowcase.ui.work

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alaskalany.careershowcase.GlideApp
import com.alaskalany.careershowcase.R
import com.alaskalany.careershowcase.databinding.FragmentWorkBinding
import com.alaskalany.careershowcase.entity.WorkEntity

/**
 * [RecyclerView.Adapter] that can display a [WorkEntity]
 *
 *
 * Adapters provide a binding from an app-specific data set to views that are displayed
 * * within a [RecyclerView].
 * TODO: Replace the implementation with code for your data type.
 */
class WorkAdapter(private val workOnClickCallback: WorkOnClickCallback) :
        RecyclerView.Adapter<WorkAdapter.ViewHolder>() {
    
    private var workEntities: List<WorkEntity>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DataBindingUtil.inflate<FragmentWorkBinding>(LayoutInflater.from(parent.context), R.layout.fragment_work,
                                                         parent, false)
        binding.workOnClickCallback = workOnClickCallback
        return ViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.work = workEntities!![position]
        holder.binding.workOnClickCallback = workOnClickCallback
        val rootView = holder.binding.root
        GlideApp.with(rootView).load(workEntities!![position].logoUrl).into(holder.binding.imageViewWorkLogo)
        holder.binding.executePendingBindings()
    }
    
    override fun getItemCount(): Int {
        return if (workEntities == null) 0 else workEntities!!.size
    }
    
    fun setWorkList(workList: List<WorkEntity>) {
        if (workEntities == null) {
            workEntities = workList
            notifyItemRangeInserted(0, workList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return workEntities!!.size
                }
                
                override fun getNewListSize(): Int {
                    return workList.size
                }
                
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return workEntities!![oldItemPosition].id == workList[newItemPosition].id
                }
                
                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newWork = workList[newItemPosition]
                    val oldProduct = workEntities!![oldItemPosition]
                    return newWork.id == oldProduct.id && newWork.description == oldProduct.description && newWork.title == oldProduct.title && newWork.description == oldProduct.description
                }
            })
            workEntities = workList
            result.dispatchUpdatesTo(this)
        }
    }
    
    class ViewHolder
    internal constructor(val binding: FragmentWorkBinding) : RecyclerView.ViewHolder(binding.root)
}
