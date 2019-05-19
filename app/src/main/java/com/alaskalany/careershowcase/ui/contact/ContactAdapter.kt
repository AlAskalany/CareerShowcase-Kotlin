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
import android.view.View
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
    (
    /**
     *
     */
    private val contactOnClickCallback: ContactOnClickCallback
    ) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    
    /**
     *
     */
    private var contactEntities: List<ContactEntity>? = null
    
    /**
     * Called when RecyclerView needs a new [RecyclerView.ViewHolder] of the given type to represent
     * an item.
     *
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     *
     *
     * The new ViewHolder will be used to display items of the adapter using
     * [.onBindViewHolder]. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary [View.findViewById] calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     *
     * @see .getItemViewType
     * @see .onBindViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        
        val binding = DataBindingUtil.inflate<FragmentContactBinding>(
            LayoutInflater.from(parent.context), R.layout.fragment_contact, parent, false
                                                                     )
        binding.contactOnClickCallback = contactOnClickCallback
        return ViewHolder(binding)
    }
    
    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [RecyclerView.ViewHolder.itemView] to reflect the item at the given
     * position.
     *
     *
     * Note that unlike [android.widget.ListView], RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the `position` parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use [RecyclerView.ViewHolder.getAdapterPosition] which will
     * have the updated adapter position.
     * Override [.onBindViewHolder] instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        
        holder.binding.contact = contactEntities!![position]
        
        when (contactEntities!![position].title) {
            "E-mail" -> holder.binding.textViewContactDescription.autoLinkMask = Linkify.EMAIL_ADDRESSES
            "Mobile" -> holder.binding.textViewContactDescription.autoLinkMask = Linkify.PHONE_NUMBERS
            "Skype"  -> holder.binding.textViewContactDescription.setTextIsSelectable(true)
            else     -> {
            }
        }
        
        holder.binding.contactOnClickCallback = contactOnClickCallback
        val rootView = holder.binding.root
        GlideApp.with(rootView).load(
            contactEntities!![position].logoUrl
                                    ).into(holder.binding.imageViewContactLogo)
        holder.binding.executePendingBindings()
    }
    
    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        
        return if (contactEntities == null) 0 else contactEntities!!.size
    }
    
    fun setContactList(contactList: List<ContactEntity>) {
        
        if (contactEntities == null) {
            contactEntities = contactList
            notifyItemRangeInserted(0, contactList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                
                /**
                 * Returns the size of the old list.
                 *
                 * @return The size of the old list.
                 */
                override fun getOldListSize(): Int {
                    
                    return contactEntities!!.size
                }
                
                /**
                 * Returns the size of the new list.
                 *
                 * @return The size of the new list.
                 */
                override fun getNewListSize(): Int {
                    
                    return contactList.size
                }
                
                /**
                 * Called by the DiffUtil to decide whether two object represent the same Item.
                 *
                 *
                 * For example, if your items have unique ids, this method should check their id equality.
                 *
                 * @param oldItemPosition The position of the item in the old list
                 * @param newItemPosition The position of the item in the new list
                 * @return True if the two items represent the same object or false if they are different.
                 */
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    
                    return contactEntities!![oldItemPosition].id == contactList[newItemPosition].id
                }
                
                /**
                 * Called by the DiffUtil when it wants to check whether two items have the same data.
                 * DiffUtil uses this information to detect if the contents of an item has changed.
                 *
                 *
                 * DiffUtil uses this method to check equality instead of [Object.equals]
                 * so that you can change its behavior depending on your UI.
                 * For example, if you are using DiffUtil with a
                 * [RecyclerView.Adapter], you should
                 * return whether the items' visual representations are the same.
                 *
                 *
                 * This method is called only if [.areItemsTheSame] returns
                 * `true` for these items.
                 *
                 * @param oldItemPosition The position of the item in the old list
                 * @param newItemPosition The position of the item in the new list which replaces the
                 * oldItem
                 * @return True if the contents of the items are the same or false if they are different.
                 */
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
    
    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     *
     *
     * [RecyclerView.Adapter] implementations should subclass ViewHolder and add fields for caching
     * potentially expensive [View.findViewById] results.
     *
     *
     * While [RecyclerView.LayoutParams] belong to the [RecyclerView.LayoutManager],
     * [ViewHolders][RecyclerView.ViewHolder] belong to the adapter. Adapters should feel free to use
     * their own custom ViewHolder implementations to store data that makes binding view contents
     * easier. Implementations should assume that individual item views will hold strong references
     * to `ViewHolder` objects and that `RecyclerView` instances may hold
     * strong references to extra off-screen item views for caching purposes
     */
    class ViewHolder
    /**
     * @param binding binding for the FragmentContact layout
     */
    internal constructor(
        /**
         *
         */
        val binding: FragmentContactBinding
                        ) : RecyclerView.ViewHolder(binding.root)
}
