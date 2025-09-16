package com.omkero.asicscanner.viewmodel

import MinerType
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.omkero.asicscanner.repo.insertMiner
import com.omkero.asicscanner.repo.loadMiners
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MinerViewModel : ViewModel() {
    private var _miners = MutableStateFlow<List<MinerType>>(emptyList())
    val miners : StateFlow<List<MinerType>> = _miners

    fun getMiners(context: Context) {
         _miners.value = loadMiners(context)
    }

    fun addMiner(context: Context, data: MinerType) {
        insertMiner(context, data)
    }
}