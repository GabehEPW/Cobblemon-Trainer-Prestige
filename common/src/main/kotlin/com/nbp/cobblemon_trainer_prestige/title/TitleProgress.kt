package com.nbp.cobblemon_trainer_prestige.title

data class TitleProgress(
    val title: Title,
    val current: Int,
    val target: Int,
    val unlocked: Boolean,
)
