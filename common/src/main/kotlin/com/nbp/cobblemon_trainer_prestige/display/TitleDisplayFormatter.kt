package com.nbp.cobblemon_trainer_prestige.display

import com.nbp.cobblemon_trainer_prestige.storage.TabDisplayMode
import com.nbp.cobblemon_trainer_prestige.storage.TitleDisplayStyle
import com.nbp.cobblemon_trainer_prestige.title.Title
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

object TitleDisplayFormatter {
    fun bracket(title: Title, style: TitleDisplayStyle = TitleDisplayStyle.TEXT): MutableComponent {
        return titleComponent(
            title = title,
            style = style,
            fallback = Component.literal("[${title.displayName}]").withStyle(title.rarity.fallbackColor),
        )
    }

    fun decorated(title: Title, style: TitleDisplayStyle = TitleDisplayStyle.TEXT): MutableComponent {
        val symbol = title.rarity.symbol
        val text = if (symbol.isBlank()) title.displayName else "$symbol ${title.displayName} $symbol"
        return titleComponent(
            title = title,
            style = style,
            fallback = Component.literal(text).withStyle(title.rarity.fallbackColor),
        )
    }

    fun compact(title: Title, style: TitleDisplayStyle = TitleDisplayStyle.TEXT): MutableComponent {
        return titleComponent(
            title = title,
            style = style,
            fallback = Component.literal(compactSymbol(title)).withStyle(title.rarity.fallbackColor),
        )
    }

    fun chat(
        playerName: String,
        title: Title,
        rawMessage: String,
        colorPlayerName: Boolean = false,
        style: TitleDisplayStyle = TitleDisplayStyle.TEXT,
    ): MutableComponent {
        val playerNameComponent = Component.literal(playerName)
        if (colorPlayerName) {
            playerNameComponent.withStyle(title.rarity.fallbackColor)
        }

        return Component.empty()
            .append(bracket(title, style))
            .append(" ")
            .append(playerNameComponent)
            .append(": ")
            .append(Component.literal(rawMessage))
    }

    fun tab(
        playerName: String,
        title: Title,
        mode: TabDisplayMode,
        colorPlayerName: Boolean = false,
        style: TitleDisplayStyle = TitleDisplayStyle.TEXT,
    ): MutableComponent {
        if (mode == TabDisplayMode.DISABLED) {
            return Component.literal(playerName)
        }

        val playerNameComponent = Component.literal(playerName)
        if (colorPlayerName) {
            playerNameComponent.withStyle(title.rarity.fallbackColor)
        }

        return Component.empty()
            .append(compact(title, style))
            .append(" ")
            .append(playerNameComponent)
    }

    fun nameplateLine(title: Title, style: TitleDisplayStyle = TitleDisplayStyle.TEXT): MutableComponent = compact(title, style)

    fun compactSymbol(title: Title): String = title.rarity.symbol.ifBlank { "*" }

    private fun titleComponent(title: Title, style: TitleDisplayStyle, fallback: MutableComponent): MutableComponent {
        val texture = TitleTextureGlyphs.component(title)
        return when (style) {
            TitleDisplayStyle.TEXT -> fallback
            TitleDisplayStyle.TEXTURE -> texture ?: fallback
            TitleDisplayStyle.BOTH -> {
                if (texture == null) {
                    fallback
                } else {
                    Component.empty()
                        .append(texture)
                        .append(" ")
                        .append(fallback)
                }
            }
        }
    }
}
