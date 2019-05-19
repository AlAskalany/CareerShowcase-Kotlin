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

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.widget.Toast

class NetworkHandler
/**
 * @param context [Context]
 */
internal constructor(private val context: Context) : Runnable {
    
    /**
     * When an object implementing interface `Runnable` is used
     * to create a thread, starting the thread causes the object's
     * `run` method to be called in that separately executing
     * thread.
     *
     *
     * The general contract of the method `run` is that it may
     * take any action whatsoever.
     *
     * @see Thread.run
     */
    override fun run() {
        // Get the connectivity manager
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        
        var isWifiConn = false
        var isMobileConn = false
        
        // Get active network information
        val networkInfo1 = connMgr.activeNetworkInfo
        
        // Online if network info exists and is connected
        val isOnline = networkInfo1 != null && networkInfo1.isConnected
        
        if (isOnline) {
            // If online
            // Check connectivity for all networks
            for (network in connMgr.allNetworks) {
                val networkInfo = connMgr.getNetworkInfo(network)
                
                if (connMgr.getNetworkCapabilities(network).hasTransport(TRANSPORT_WIFI)) {
                    // If Wi-Fi
                    isWifiConn = isWifiConn or networkInfo.isConnected
                    if (isWifiConn) {
                        doWhenWifiIsConnected()
                    }
                }
                if (connMgr.getNetworkCapabilities(network).hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    // If mobile network
                    isMobileConn = isMobileConn or networkInfo.isConnected
                    if (isMobileConn) {
                        doWhenMobileIsConnected()
                    }
                }
            }
        } else {
            // If not connected
            doWhenNotConnected()
        }
    }
    
    /**
     * If connected through Wi-Fi do this
     */
    private fun doWhenWifiIsConnected() {
        // TODO handle if Wi-Fi is connected
        Toast.makeText(context.applicationContext, "WiFi connected", Toast.LENGTH_SHORT).show()
    }
    
    /**
     * If connected through mobile network do this
     */
    private fun doWhenMobileIsConnected() {
        // TODO handle if mobile network is connected
        Toast.makeText(context.applicationContext, "Mobile connected", Toast.LENGTH_SHORT).show()
    }
    
    /**
     * If not connected do this
     */
    private fun doWhenNotConnected() {
        
        // TODO handle if disconnected
        Toast.makeText(context.applicationContext, "Not connected", Toast.LENGTH_SHORT).show()
    }
}