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

package com.alaskalany.careershowcase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.alaskalany.careershowcase.model.Contact

/**
 * A contact item representing a piece of description.
 */
@Entity(tableName = "contacts_table")
class ContactEntity : Contact {
    
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "contact_id")
    override var id: Int = 0
    
    @ColumnInfo(name = "contact_title")
    override var title: String? = null
    
    @ColumnInfo(name = "contact_description")
    override var description: String? = null
    
    @ColumnInfo(name = "contact_logo_url")
    override var logoUrl: String? = null
    
    constructor()
    
    @Ignore
    constructor(id: Int, title: String, description: String) {
        
        this.id = id
        this.title = title
        this.description = description
    }
}
