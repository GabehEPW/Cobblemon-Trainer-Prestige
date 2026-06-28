package com.nbp.cobblemon_trainer_prestige.network

import java.util.UUID

data class SyncPlayerTitlePacket(
    val playerUuid: UUID,
    val equippedTitleId: String?,
    val displayName: String,
    val rarity: String,
    val color: String,
)
