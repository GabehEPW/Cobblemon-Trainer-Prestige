package com.nbp.cobblemon_trainer_prestige.network

data class SyncAllVisibleTitlesPacket(
    val titles: List<SyncPlayerTitlePacket>,
)
