

import androidx.room.Entity
import androidx.room.PrimaryKey

data class MinerType (
    val ipv4: String,
    val port: String,
    val name: String,
    val type: String,
    val uniqueKey: String
)

