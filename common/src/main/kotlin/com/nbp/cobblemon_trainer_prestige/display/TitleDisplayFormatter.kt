package com.nbp.cobblemon_trainer_prestige.display

import com.nbp.cobblemon_trainer_prestige.storage.TabDisplayMode
import com.nbp.cobblemon_trainer_prestige.title.Title
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

object TitleDisplayFormatter {
    fun bracket(title: Title): MutableComponent {
        return Component.literal("[${title.displayName}]").withStyle(title.rarity.fallbackColor)
    }

    fun decorated(title: Title): MutableComponent {
        val symbol = title.rarity.symbol
        val text = if (symbol.isBlank()) title.displayName else "$symbol ${title.displayName} $symbol"
        return Component.literal(text).withStyle(title.rarity.fallbackColor)
    }

    fun compact(title: Title): MutableComponent {
        return Component.literal(compactSymbol(title)).withStyle(title.rarity.fallbackColor)
    }

    fun chat(playerName: String, title: Title, rawMessage: String, colorPlayerName: Boolean = false): MutableComponent {
        val playerNameComponent = Component.literal(playerName)
        if (colorPlayerName) {
            playerNameComponent.withStyle(title.rarity.fallbackColor)
        }

        return Component.empty()
            .append(bracket(title))
            .append(" ")
            .append(playerNameComponent)
            .append(": ")
            .append(Component.literal(rawMessage))
    }

    fun tab(playerName: String, title: Title, mode: TabDisplayMode, colorPlayerName: Boolean = false): MutableComponent {
        if (mode == TabDisplayMode.DISABLED) {
            return Component.literal(playerName)
        }

        val playerNameComponent = Component.literal(playerName)
        if (colorPlayerName) {
            playerNameComponent.withStyle(title.rarity.fallbackColor)
        }

        return Component.empty()
            .append(compact(title))
            .append(" ")
            .append(playerNameComponent)
    }

    fun nameplateLine(title: Title): MutableComponent = compact(title)

    fun compactSymbol(title: Title): String = title.rarity.symbol.ifBlank { "*" }

    private fun shorten(value: String, max: Int): String {
        return if (value.length <= max) value else value.take(max - 3) + "..."
    }
}
