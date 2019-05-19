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
import androidx.room.PrimaryKey
import com.alaskalany.careershowcase.model.Skill

/**
 * A skill item representing a piece of description.
 */
/**
 *
 */
@Entity(tableName = "skills_table")
class SkillEntity : Skill {
    
    /**
     *
     */
    /**
     * @return
     */
    /**
     * @param pSkillId
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "skill_id")
    override var id: Int = 0
    
    /**
     *
     */
    /**
     * @return
     */
    /**
     * @param title
     */
    @ColumnInfo(name = "skill_title")
    override var title: String? = null
    
    @ColumnInfo(name = "skill_level")
    override var level: Int = 0
    
    @ColumnInfo(name = "skill_log_url")
    override var logoUrl: String? = null
}
