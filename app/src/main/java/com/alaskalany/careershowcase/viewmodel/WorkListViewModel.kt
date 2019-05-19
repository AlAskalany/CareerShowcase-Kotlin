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

package com.alaskalany.careershowcase.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.alaskalany.careershowcase.CareerShowcaseApp
import com.alaskalany.careershowcase.entity.WorkEntity
import com.alaskalany.careershowcase.file.FileData
import com.alaskalany.careershowcase.repository.DataRepository

class WorkListViewModel(application: Application) : AndroidViewModel(application) {
    
    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private val observableProducts: MediatorLiveData<List<WorkEntity>>
    
    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    val works: LiveData<List<WorkEntity>>
        get() = observableProducts
    
    init {
        observableProducts = MediatorLiveData()
        // set by default null, until we get data from the database.
        observableProducts.value = null
        //LiveData<List<WorkEntity>> works = ((CareerShowcaseApp) application).getRepository().workRepository.getWorks();
        val listLiveData = FileData.getWorkLiveData(application)
        // observe the changes of the products from the database and forward them
        observableProducts.addSource(listLiveData) { observableProducts.setValue(it) }
    }
    
    class Factory(private val application: Application, private val workId: Int) :
        ViewModelProvider.NewInstanceFactory() {
        
        private val repository: DataRepository
        
        init {
            repository = (application as CareerShowcaseApp).repository!!
        }
        
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            
            return WorkViewModel(application, repository, workId) as T
        }
    }
}
