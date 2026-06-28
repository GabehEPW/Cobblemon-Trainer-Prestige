package com.nbp.cobblemon_trainer_prestige.storage

data class ClientTrainerPrestigeConfig(
    var renderPlayerTitles: Boolean = true,
    var showTitleBelowName: Boolean = true,
    var showRaritySymbols: Boolean = true,
    var showUnlockToasts: Boolean = true,
    var titleScale: Double = 0.75,
    var titleVerticalOffset: Double = 0.28,
    var maxRenderDistance: Int = 32,
)
