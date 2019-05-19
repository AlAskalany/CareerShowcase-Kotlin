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
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alaskalany.careershowcase.CareerShowcaseApp
import com.alaskalany.careershowcase.entity.WorkEntity
import com.alaskalany.careershowcase.repository.DataRepository

class WorkViewModel(application: Application, dataRepository: DataRepository, private val workId: Int) :
    AndroidViewModel(application) {
    
    val observableWork: LiveData<WorkEntity>
    
    var work = ObservableField<WorkEntity>()
    
    init {
        observableWork = dataRepository.workRepository.load(workId)
    }
    
    fun setWork(product: WorkEntity) {
        
        this.work.set(product)
    }
    
    /**
     * A creator is used to inject the product ID into the ViewModel
     *
     *
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the product ID can be passed in a public method.
     */
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
