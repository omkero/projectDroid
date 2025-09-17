package com.omkero.asicscanner.repo

import MinerType
import android.content.Context
import androidx.core.content.edit


fun saveMiners(context: Context, miners: List<MinerType>) {
    val prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    prefs.edit {

        val serialized = miners.joinToString(";;") { miner ->
            "${miner.ipv4}|${miner.port}|${miner.name}|${miner.type}|${miner.uniqueKey}"
        }

        putString("miners_list", serialized)
    }
}

fun loadMiners(context: Context): List<MinerType> {
    val prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    val serialized = prefs.getString("miners_list", null) ?: return emptyList()

    return serialized
        .split(";;")
        .filter { it.isNotBlank() }
        .mapNotNull { minerString ->
            val parts = minerString.split("|")
            when (parts.size) {
                5 -> MinerType(parts[0], parts[1], parts[2], parts[3], parts[4]) // full record
                3 -> MinerType(parts[0], parts[1], parts[2], "", "")             // old record
                else -> null
            }
        }


}

fun removeMiner(context: Context, target: MinerType) {
    val miners = loadMiners(context).toMutableList()
    miners.removeAll { it.ipv4 == target.ipv4 && it.port == target.port && it.name == target.name }
    saveMiners(context, miners)
}

fun removeMinerAt(context: Context, index: Int) {
    val miners = loadMiners(context).toMutableList()
    if (index in miners.indices) {
        miners.removeAt(index)
        saveMiners(context, miners)
    }
}

fun insertMiner(context: Context, newMiner: MinerType) {
    val miners = loadMiners(context).toMutableList()
    miners.add(newMiner)
    saveMiners(context, miners)
}

