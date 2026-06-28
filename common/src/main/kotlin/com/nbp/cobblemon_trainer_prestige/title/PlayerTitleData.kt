package com.nbp.cobblemon_trainer_prestige.title

import java.util.UUID

data class PlayerTitleData(
    val playerUuid: UUID,
    val unlockedTitles: MutableSet<String> = linkedSetOf(),
    var equippedTitleId: String? = null,
    var lockedTitleId: String? = null,
    val progress: MutableMap<String, Int> = linkedMapOf(),
    val timestamps: MutableMap<String, Long> = linkedMapOf(),
    val streaks: MutableMap<String, Int> = linkedMapOf(),
    val uniqueProgress: MutableMap<String, MutableList<String>> = linkedMapOf(),
)
