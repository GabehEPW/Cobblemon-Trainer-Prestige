package com.nbp.cobblemon_trainer_prestige.title

data class Title(
    val id: String,
    val displayName: String,
    val description: String,
    val rarity: TitleRarity,
    val category: TitleCategory,
    val visibility: TitleVisibility,
    val unlockText: String,
    val hiddenText: String = "?????",
    val hintText: String = "",
    val requirement: TitleRequirement,
    val announceOnUnlock: Boolean = true,
    val showInChat: Boolean = true,
    val showInTab: Boolean = true,
    val showInNameplate: Boolean = true,
) {
    fun visibleName(unlocked: Boolean): String {
        return if (unlocked || visibility == TitleVisibility.VISIBLE) displayName else hiddenText
    }

    fun visibleUnlockText(unlocked: Boolean): String {
        return if (unlocked || visibility == TitleVisibility.VISIBLE) unlockText else "?????"
    }
}
