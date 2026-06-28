package com.nbp.cobblemon_trainer_prestige.title

import net.minecraft.ChatFormatting

enum class TitleRarity(
    val translationKey: String,
    val fallbackColor: ChatFormatting,
    val hexColor: String,
    val symbol: String,
    val ptBrName: String,
) {
    COMMON("trainerprestige.rarity.common", ChatFormatting.WHITE, "#F8FAFC", "", "Common"),
    UNCOMMON("trainerprestige.rarity.uncommon", ChatFormatting.GREEN, "#22C55E", "+", "Uncommon"),
    RARE("trainerprestige.rarity.rare", ChatFormatting.AQUA, "#38BDF8", "*", "Rare"),
    EPIC("trainerprestige.rarity.epic", ChatFormatting.LIGHT_PURPLE, "#A855F7", "*", "Epic"),
    LEGENDARY("trainerprestige.rarity.legendary", ChatFormatting.GOLD, "#F59E0B", "*", "Legendary"),
    ULTRA_BEAST("trainerprestige.rarity.ultra_beast", ChatFormatting.DARK_PURPLE, "#7C3AED", "#", "Ultra Beast"),
    MYTHIC("trainerprestige.rarity.mythic", ChatFormatting.LIGHT_PURPLE, "#EC4899", "*", "Mythic"),
    SECRET("trainerprestige.rarity.secret", ChatFormatting.DARK_RED, "#7F1D1D", "?", "Secret"),
}
