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

import android.content.Context
import android.os.AsyncTask
import android.view.ViewOutlineProvider
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.alaskalany.careershowcase.AppExecutors
import com.alaskalany.careershowcase.database.dao.ContactDao
import com.alaskalany.careershowcase.database.dao.EducationDao
import com.alaskalany.careershowcase.database.dao.SkillDao
import com.alaskalany.careershowcase.database.dao.WorkDao
import com.alaskalany.careershowcase.entity.ContactEntity
import com.alaskalany.careershowcase.entity.EducationEntity
import com.alaskalany.careershowcase.entity.SkillEntity
import com.alaskalany.careershowcase.entity.WorkEntity

@Database(version = 6, entities = [ContactEntity::class, EducationEntity::class, SkillEntity::class, WorkEntity::class])
abstract class AppDatabase : RoomDatabase() {
    
    
    private val isDatabaseCreated = MutableLiveData<Boolean>()
    val databaseCreated: LiveData<Boolean>
        get() = isDatabaseCreated
    
    abstract fun workDao(): WorkDao
    abstract fun skillDao(): SkillDao
    abstract fun educationDao(): EducationDao
    abstract fun contactDao(): ContactDao
    /**
     * @param context
     */
    private fun updateDatabaseCreated(context: Context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated()
        }
    }
    
    private fun setDatabaseCreated() {
        isDatabaseCreated.postValue(true)
    }
    
    private class PopulateDatabaseAsync(pInstance: AppDatabase) : AsyncTask<Void, Void, ViewOutlineProvider>() {
        
        private val educationDao: EducationDao = pInstance.educationDao()
        private val workDao: WorkDao = pInstance.workDao()
        private val skillDao: SkillDao = pInstance.skillDao()
        override fun doInBackground(vararg pVoids: Void): ViewOutlineProvider? {
            val educationEntities = DataGenerator.EducationContent.ITEMS
            educationDao.insertAll(educationEntities)
            val workEntities = DataGenerator.WorkContent.ITEMS
            workDao.insertAll(workEntities)
            val skillEntities = DataGenerator.SkillContent.ITEMS
            skillDao.insertAll(skillEntities)
            return null
        }
    }
    
    companion object {
        
        @VisibleForTesting
        val DATABASE_NAME = "app-db"
        private var INSTANCE: AppDatabase? = null
        private var roomDatabaseCallback: Callback = object : Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDatabaseAsync(INSTANCE!!).execute()
            }
        }
        
        fun getInstance(context: Context, executors: AppExecutors): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = buildDatabase(context.applicationContext, executors)
                        INSTANCE!!.updateDatabaseCreated(context.applicationContext)
                    }
                }
            }
            return INSTANCE
        }
        
        private fun buildDatabase(context: Context, executors: AppExecutors): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : Callback() {
                    /**
                     * Called when the database is created for the first time. This is called after all the
                     * tables are created.
                     *
                     * @param db The database.
                     */
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        executors.diskIO().execute {
                            // Add a delay to simulate a long-running operation
                            addDelay()
                            // Generate the data for pre-population
                            val database = getInstance(context, executors)
                            val contactEntities = DataGenerator.generateContacts()
                            val educationEntities = DataGenerator.generateEducations()
                            val skillEntities = DataGenerator.generateSkills()
                            val workEntities = DataGenerator.generateWorks()
                            database!!.runInTransaction {
                                database.contactDao().insertAll(contactEntities)
                                database.educationDao().insertAll(educationEntities)
                                database.skillDao().insertAll(skillEntities)
                                database.workDao().insertAll(workEntities)
                            }
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated()
                        }
                    }
                }).addCallback(roomDatabaseCallback).fallbackToDestructiveMigration().build()
        }
        
        private fun addDelay() {
            try {
                Thread.sleep(4000)
            } catch (ignored: InterruptedException) {
            }
        }
    }
}
