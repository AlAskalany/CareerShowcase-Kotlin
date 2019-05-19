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

class NetworkHandler(private val context: Context) : Runnable {
    
    override fun run() {
        // Get the connectivity manager
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var isWifiConn = false
        var isMobileConn = false
        val networkInfo1 = connMgr.activeNetworkInfo
        val isOnline = networkInfo1 != null && networkInfo1.isConnected
        
        when {
            isOnline -> connMgr.allNetworks.forEach { network ->
                val networkInfo = connMgr.getNetworkInfo(network)
                
                if (connMgr.getNetworkCapabilities(network).hasTransport(TRANSPORT_WIFI)) {
                    isWifiConn = isWifiConn or networkInfo.isConnected
                    if (isWifiConn) {
                        doWhenWifiIsConnected()
                    }
                }
                if (connMgr.getNetworkCapabilities(network).hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    isMobileConn = isMobileConn or networkInfo.isConnected
                    if (isMobileConn) {
                        doWhenMobileIsConnected()
                    }
                }
            }
            else -> doWhenNotConnected()
        }
    }
    
    private fun doWhenWifiIsConnected() =
        Toast.makeText(context.applicationContext, "WiFi connected", Toast.LENGTH_SHORT).show()
    
    private fun doWhenMobileIsConnected() =
        Toast.makeText(context.applicationContext, "Mobile connected", Toast.LENGTH_SHORT).show()
    
    private fun doWhenNotConnected() =
        Toast.makeText(context.applicationContext, "Not connected", Toast.LENGTH_SHORT).show()
}