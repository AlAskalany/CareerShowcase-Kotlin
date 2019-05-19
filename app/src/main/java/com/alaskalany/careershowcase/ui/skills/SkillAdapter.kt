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

package com.alaskalany.careershowcase.ui.skills

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alaskalany.careershowcase.GlideApp
import com.alaskalany.careershowcase.R
import com.alaskalany.careershowcase.databinding.FragmentSkillBinding
import com.alaskalany.careershowcase.entity.SkillEntity

/**
 * [RecyclerView.Adapter] that can display a [SkillEntity]
 *
 *
 * Adapters provide a binding from an app-specific data set to views that are displayed
 * * within a [RecyclerView].
 * TODO: Replace the implementation with code for your data type.
 */
class SkillAdapter
/**
 * @param skillOnClickCallback Listener to clicks on Skill items
 */
    (private val skillOnClickCallback: SkillOnClickCallback) : RecyclerView.Adapter<SkillAdapter.ViewHolder>() {
    
    
    private var skillEntities: List<SkillEntity>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DataBindingUtil.inflate<FragmentSkillBinding>(LayoutInflater.from(parent.context), R.layout.fragment_skill,
                                                          parent, false)
        binding.skillOnClickCallback = skillOnClickCallback
        return ViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.skill = skillEntities!![position]
        
        holder.binding.skillOnClickCallback = skillOnClickCallback
        val rootView = holder.binding.root
        GlideApp.with(rootView).load(skillEntities!![position].logoUrl).into(holder.binding.imageViewSkillLogo)
        holder.binding.executePendingBindings()
    }
    
    override fun getItemCount(): Int {
        return if (skillEntities == null) 0 else skillEntities!!.size
    }
    
    fun setSkillList(skillList: List<SkillEntity>) {
        if (skillEntities == null) {
            skillEntities = skillList
            notifyItemRangeInserted(0, skillList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return skillEntities!!.size
                }
                
                override fun getNewListSize(): Int {
                    return skillList.size
                }
                
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return skillEntities!![oldItemPosition].id == skillList[newItemPosition].id
                }
                
                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newSkill = skillList[newItemPosition]
                    val oldSkill = skillEntities!![oldItemPosition]
                    return newSkill.id == oldSkill.id && newSkill.title == oldSkill.title && newSkill.level == oldSkill.level
                }
            })
            skillEntities = skillList
            result.dispatchUpdatesTo(this)
        }
    }
    
    class ViewHolder
    internal constructor(val binding: FragmentSkillBinding) : RecyclerView.ViewHolder(binding.root)
}
