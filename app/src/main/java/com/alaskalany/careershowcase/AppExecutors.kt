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

package com.alaskalany.careershowcase

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.RejectedExecutionException

/**
 * App [Executor]s
 */
class AppExecutors
/**
 * @param diskIoExecutor     Disk IO [Executor]
 * @param networkIoExecutor  Network IO [Executor]
 * @param mainThreadExecutor Main thread [Executor]
 */
private constructor(
    /**
     * Disk IO [Executor]
     */
    private val diskIoExecutor: Executor,
    /**
     * Network IO [Executor]
     */
    private val networkIoExecutor: Executor,
    /**
     * Main thread [Executor]
     */
    private val mainThreadExecutor: Executor
) {
    
    /**
     * App [Executor]s
     */
    constructor() : this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3), MainThreadExecutor())
    
    /**
     * @return DisK IO [Executor]
     */
    fun diskIO(): Executor {
        return diskIoExecutor
    }
    
    /**
     * @return Network IO [Executor]
     */
    fun networkIO(): Executor {
        return networkIoExecutor
    }
    
    /**
     * @return Main thread [Executor]
     */
    fun mainThread(): Executor {
        return mainThreadExecutor
    }
    
    /**
     * Main thread [Executor]
     */
    private class MainThreadExecutor : Executor {
        
        /**
         * Main thread handler
         */
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        
        /**
         * Executes the given command at some time in the future.  The command
         * may execute in a new thread, in a pooled thread, or in the calling
         * thread, at the discretion of the `Executor` implementation.
         *
         * @param command the runnable task
         *
         * @throws RejectedExecutionException if this task cannot be
         * accepted for execution
         * @throws NullPointerException       if command is null
         */
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}
