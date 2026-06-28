package com.nbp.cobblemon_trainer_prestige.display

import com.nbp.cobblemon_trainer_prestige.title.TitleRarity
import java.util.UUID

data class TitleNameplateData(
    val playerUuid: UUID,
    val equippedTitleId: String?,
    val displayName: String,
    val rarity: TitleRarity,
    val color: String,
)
