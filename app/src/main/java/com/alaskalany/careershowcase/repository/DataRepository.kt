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

package com.alaskalany.careershowcase.repository

import androidx.lifecycle.MediatorLiveData
import com.alaskalany.careershowcase.database.AppDatabase
import com.alaskalany.careershowcase.entity.ContactEntity
import com.alaskalany.careershowcase.entity.EducationEntity
import com.alaskalany.careershowcase.entity.SkillEntity
import com.alaskalany.careershowcase.entity.WorkEntity

class DataRepository private constructor(val database: AppDatabase) {
    val contactRepository = ContactRepository(this)
    val workRepository = WorkRepository(this)
    val skillRepository = SkillRepository(this)
    val educationRepository = EducationRepository(this)
    
    private val isDatabaseCreated: Boolean?
        get() = database.databaseCreated.value
    
    init {
        workRepository.works = MediatorLiveData<List<WorkEntity>>()
        workRepository.works!!.addSource<List<WorkEntity>>(workRepository.loadAll()) { workEntities ->
            if (isDatabaseCreated != null) {
                workRepository.works!!.postValue(workEntities)
            }
        }
        educationRepository.observableEducations = MediatorLiveData<List<EducationEntity>>()
        educationRepository.observableEducations!!.addSource<List<EducationEntity>>(educationRepository.loadAll()) { educationEntities ->
            if (isDatabaseCreated != null) {
                educationRepository.observableEducations!!.postValue(educationEntities)
            }
        }
        skillRepository.observableSkills = MediatorLiveData<List<SkillEntity>>()
        skillRepository.observableSkills!!.addSource<List<SkillEntity>>(skillRepository.loadAll()) { skillEntities ->
            if (isDatabaseCreated != null) {
                skillRepository.observableSkills!!.postValue(skillEntities)
            }
        }
        contactRepository.observableContacts = MediatorLiveData<List<ContactEntity>>()
        contactRepository.observableContacts!!.addSource<List<ContactEntity>>(contactRepository.loadAll()) { contactEntities ->
            if (isDatabaseCreated != null) {
                contactRepository.observableContacts!!.postValue(contactEntities)
            }
        }
    }
    
    companion object {
        
        private var INSTANCE: DataRepository? = null
        
        fun getInstance(database: AppDatabase): DataRepository? {
            
            if (INSTANCE == null) {
                synchronized(DataRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = DataRepository(database)
                    }
                }
            }
            return INSTANCE
        }
    }
}
