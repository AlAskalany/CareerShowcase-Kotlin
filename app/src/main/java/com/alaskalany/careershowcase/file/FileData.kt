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

package com.alaskalany.careershowcase.file

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.alaskalany.careershowcase.entity.ContactEntity
import com.alaskalany.careershowcase.entity.EducationEntity
import com.alaskalany.careershowcase.entity.SkillEntity
import com.alaskalany.careershowcase.entity.WorkEntity
import com.google.gson.Gson
import org.jetbrains.annotations.Contract

object FileData {
    
    @JvmStatic
    @Contract(pure = true)
    fun getInstance(): FileData {
        return FileData
    }
    
    private val educationLiveData = MediatorLiveData<List<EducationEntity>>()
    
    private val skillsLiveData = MediatorLiveData<List<SkillEntity>>()
    
    private val workLiveData = MediatorLiveData<List<WorkEntity>>()
    
    private val contactsLiveData = MediatorLiveData<List<ContactEntity>>()
    
    fun getEducationLiveData(application: Application): LiveData<List<EducationEntity>> {
        
        if (educationLiveData.value == null) {
            loadEducation(application)
        }
        return educationLiveData
    }
    
    private fun loadEducation(application: Application) {
        
        val gson = Gson()
        val dataJson =
            gson.fromJson(JsonFileReader.loadJSONFromAsset(application.applicationContext), DataJson::class.java)
        educationLiveData.postValue(dataJson.education)
    }
    
    fun getSkillsLiveData(application: Application): LiveData<List<SkillEntity>> {
        
        if (skillsLiveData.value == null) {
            loadSkills(application)
        }
        return skillsLiveData
    }
    
    private fun loadSkills(application: Application) {
        
        val gson = Gson()
        val dataJson =
            gson.fromJson(JsonFileReader.loadJSONFromAsset(application.applicationContext), DataJson::class.java)
        skillsLiveData.postValue(dataJson.skills)
    }
    
    fun getWorkLiveData(application: Application): LiveData<List<WorkEntity>> {
        
        if (workLiveData.value == null) {
            loadWork(application)
        }
        return workLiveData
    }
    
    private fun loadWork(application: Application) {
        
        val gson = Gson()
        val dataJson =
            gson.fromJson(JsonFileReader.loadJSONFromAsset(application.applicationContext), DataJson::class.java)
        workLiveData.postValue(dataJson.work)
    }
    
    fun getContactsLiveData(application: Application): LiveData<List<ContactEntity>> {
        
        if (contactsLiveData.value == null) {
            loadContacts(application)
        }
        return contactsLiveData
    }
    
    private fun loadContacts(application: Application) {
        
        val gson = Gson()
        val dataJson =
            gson.fromJson(JsonFileReader.loadJSONFromAsset(application.applicationContext), DataJson::class.java)
        contactsLiveData.postValue(dataJson.contacts)
    }
}
