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

package com.alaskalany.careershowcase.database

import com.alaskalany.careershowcase.entity.ContactEntity
import com.alaskalany.careershowcase.entity.EducationEntity
import com.alaskalany.careershowcase.entity.SkillEntity
import com.alaskalany.careershowcase.entity.WorkEntity
import org.jetbrains.annotations.Contract
import java.util.*

object DataGenerator {
    
    @JvmStatic
    @Contract(pure = true)
    fun generateContacts(): List<ContactEntity> {
        return ContactContent.ITEMS
    }
    
    @JvmStatic
    @Contract(pure = true)
    fun generateEducations(): List<EducationEntity> {
        return EducationContent.ITEMS
    }
    
    @JvmStatic
    @Contract(pure = true)
    fun generateSkills(): List<SkillEntity> {
        return SkillContent.ITEMS
    }
    
    @JvmStatic
    @Contract(pure = true)
    fun generateWorks(): List<WorkEntity> {
        return WorkContent.ITEMS
    }
    
    /**
     * Helper class for providing sample description for user interfaces created by
     * Android template wizards.
     *
     *
     * TODO: Replace all uses of this class before publishing your app.
     */
    object ContactContent {
        
        /**
         * An array of sample (contact) items.
         */
        val ITEMS: MutableList<ContactEntity> = ArrayList()
        /**
         * A map of sample (contact) items, by ID.
         */
        private val ITEM_MAP: MutableMap<Int, ContactEntity> = HashMap()
        private const val COUNT = 25
        
        init {
            // Add some sample items.
            for (i in 1..COUNT) {
                addItem(createContactItem(i))
            }
        }
        
        private fun addItem(item: ContactEntity) {
            ITEMS.add(item)
            ITEM_MAP[item.id] = item
        }
        
        private fun createContactItem(position: Int): ContactEntity {
            return ContactEntity(position, "Item $position", makeDetails(position))
        }
        
        private fun makeDetails(position: Int): String {
            val builder = StringBuilder()
            builder.append("Details about Item: ").append(position)
            for (i in 0 until position) {
                builder.append("\nMore contactDetails information here.")
            }
            return builder.toString()
        }
    }
    
    /**
     * Helper class for providing sample description for user interfaces created by
     * Android template wizards.
     *
     *
     * TODO: Replace all uses of this class before publishing your app.
     */
    object EducationContent {
        
        /**
         * An array of sample (education) items.
         */
        val ITEMS: MutableList<EducationEntity> = ArrayList()
        private const val COUNT = 25
        
        init {
            // Add some sample items.
            for (i in 1..COUNT) {
                addItem(createEducationItem(i))
            }
        }
        
        private fun addItem(item: EducationEntity) {
            ITEMS.add(item)
        }
        
        private fun createEducationItem(position: Int): EducationEntity {
            val educationEntity = EducationEntity()
            educationEntity.id = position
            educationEntity.title = "Education $position"
            educationEntity.description = makeDetails(position)
            educationEntity.institution = "KTH Royal Institute of Technology"
            educationEntity.location = "Stockholm, Sweden"
            educationEntity.duration = "2014-2019"
            return educationEntity
        }
        
        private fun makeDetails(position: Int): String {
            val builder = StringBuilder()
            builder.append("Details about Education: ").append(position)
            for (i in 0 until position) {
                builder.append("\nMore contactDetails information here.")
            }
            return builder.toString()
        }
    }
    
    /**
     * Helper class for providing sample description for user interfaces created by
     * Android template wizards.
     *
     *
     * TODO: Replace all uses of this class before publishing your app.
     */
    object SkillContent {
        
        /**
         * An array of sample (skill) items.
         */
        val ITEMS: MutableList<SkillEntity> = ArrayList()
        private const val COUNT = 25
        
        init {
            // Add some sample items.
            for (i in 1..COUNT) {
                addItem(createSkillItem(i))
            }
        }
        
        private fun addItem(item: SkillEntity) {
            ITEMS.add(item)
        }
        
        private fun createSkillItem(position: Int): SkillEntity {
            val skillEntity = SkillEntity()
            skillEntity.id = position
            skillEntity.title = "Skill $position"
            skillEntity.level = makeLevel()
            return skillEntity
        }
        
        private fun makeLevel(): Int {
            return Random().nextInt(5)
        }
    }
    
    /**
     * Helper class for providing sample description for user interfaces created by
     * Android template wizards.
     *
     *
     * TODO: Replace all uses of this class before publishing your app.
     */
    object WorkContent {
        
        /**
         * An array of sample (contact) items.
         */
        val ITEMS: MutableList<WorkEntity> = ArrayList()
        private const val COUNT = 25
        
        init {
            // Add some sample items.
            for (i in 1..COUNT) {
                addItem(createWorkItem(i))
            }
        }
        
        private fun addItem(item: WorkEntity) {
            ITEMS.add(item)
        }
        
        private fun createWorkItem(position: Int): WorkEntity {
            val workEntity = WorkEntity()
            workEntity.id = position
            workEntity.title = "Work $position"
            workEntity.description = makeDetails(position)
            return workEntity
        }
        
        private fun makeDetails(position: Int): String {
            val builder = StringBuilder()
            builder.append("Details about Work: ").append(position)
            for (i in 0 until position) {
                builder.append("\nMore contactDetails information here.")
            }
            return builder.toString()
        }
    }
}
