package com.example.fetch.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.toMutableStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetch.api.FetchNetworkInstance
import com.example.fetch.api.model.FetchDataObject
import kotlinx.coroutines.launch

class FetchActivityViewModel : ViewModel() {

    val networkData = mutableStateMapOf<Long, List<FetchDataObject>>()
    var isExpandedMap = mutableStateMapOf<Int, Boolean>()

    private val regexAlpha = Regex("[^0-9 ]")

    init {
        getNetworkData()
    }

    private fun getNetworkData() {

        viewModelScope.launch {
            val result = FetchNetworkInstance.getRetrifitService().getFetchData()

            if (result.isSuccessful && !result.body().isNullOrEmpty()) {
                result.body()!!
                    .filter { !it.name.isNullOrEmpty() }
                    .forEach {
                        it.digit = regexAlpha.replace(it.name!!, "").replace(" ", "").toLong()

                        if (networkData.containsKey(it.listId)) {
                            networkData[it.listId] = networkData[it.listId]!!.plus(it)
                        } else {
                            networkData[it.listId] = mutableListOf(it)
                        }
                    }

                networkData.forEach { (k, v) ->
                    networkData[k] = v.sortedBy { it.digit }
                }

                networkData.toSortedMap()

                isExpandedMap = List(networkData.size) { index: Int -> index to false }.toMutableStateMap()

            } else {
                Log.d("API", "FAILED")
            }

        }
    }
}