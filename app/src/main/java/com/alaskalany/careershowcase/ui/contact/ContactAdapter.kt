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

package com.alaskalany.careershowcase.ui.contact

import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alaskalany.careershowcase.GlideApp
import com.alaskalany.careershowcase.R
import com.alaskalany.careershowcase.databinding.FragmentContactBinding
import com.alaskalany.careershowcase.entity.ContactEntity

/**
 * [RecyclerView.Adapter] that can display a [ContactEntity]
 *
 *
 * Adapters provide a binding from an app-specific data set to views that are displayed
 * * within a [RecyclerView].
 * TODO: Replace the implementation with code for your data type.
 */
class ContactAdapter
/**
 * @param contactOnClickCallback Callback for click on Contact items
 */
    (private val contactOnClickCallback: ContactOnClickCallback) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    
    
    private var contactEntities: List<ContactEntity>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<FragmentContactBinding>(LayoutInflater.from(parent.context),
                                                                      R.layout.fragment_contact, parent, false)
        binding.contactOnClickCallback = contactOnClickCallback
        return ViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.contact = contactEntities!![position]
        
        when (contactEntities!![position].title) {
            "E-mail" -> holder.binding.textViewContactDescription.autoLinkMask = Linkify.EMAIL_ADDRESSES
            "Mobile" -> holder.binding.textViewContactDescription.autoLinkMask = Linkify.PHONE_NUMBERS
            "Skype" -> holder.binding.textViewContactDescription.setTextIsSelectable(true)
            else -> {
            }
        }
        
        holder.binding.contactOnClickCallback = contactOnClickCallback
        val rootView = holder.binding.root
        GlideApp.with(rootView).load(contactEntities!![position].logoUrl).into(holder.binding.imageViewContactLogo)
        holder.binding.executePendingBindings()
    }
    
    override fun getItemCount(): Int {
        return if (contactEntities == null) 0 else contactEntities!!.size
    }
    
    fun setContactList(contactList: List<ContactEntity>) {
        if (contactEntities == null) {
            contactEntities = contactList
            notifyItemRangeInserted(0, contactList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return contactEntities!!.size
                }
                
                override fun getNewListSize(): Int {
                    return contactList.size
                }
                
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return contactEntities!![oldItemPosition].id == contactList[newItemPosition].id
                }
                
                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newContact = contactList[newItemPosition]
                    val oldContact = contactEntities!![oldItemPosition]
                    val isIdEqual = newContact.id == oldContact.id
                    val isDescriptionEqual = newContact.description == oldContact.description
                    val isTitleEqual = newContact.title == oldContact.title
                    return isIdEqual && isDescriptionEqual && isTitleEqual
                }
            })
            contactEntities = contactList
            result.dispatchUpdatesTo(this)
        }
    }
    
    class ViewHolder
    internal constructor(val binding: FragmentContactBinding) : RecyclerView.ViewHolder(binding.root)
}
