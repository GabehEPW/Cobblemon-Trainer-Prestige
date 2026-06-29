package com.nbp.cobblemon_trainer_prestige.progress

import com.nbp.cobblemon_trainer_prestige.CobblemonTrainerPrestige
import com.nbp.cobblemon_trainer_prestige.display.TitleDisplayFormatter
import com.nbp.cobblemon_trainer_prestige.storage.TitleStorage
import com.nbp.cobblemon_trainer_prestige.title.PlayerTitleData
import com.nbp.cobblemon_trainer_prestige.title.Title
import com.nbp.cobblemon_trainer_prestige.title.TitleRegistry
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer

object TitleUnlockManager {
    fun check(player: ServerPlayer, data: PlayerTitleData): List<Title> {
        val unlocked = mutableListOf<Title>()
        for (title in TitleRegistry.all()) {
            if (title.id in data.unlockedTitles) continue
            if (!title.requirement.isComplete(data.progress)) continue

            data.unlockedTitles += title.id
            data.timestamps[title.id] = System.currentTimeMillis()
            unlocked += title
            notifyUnlock(player, title)
        }

        if (unlocked.isNotEmpty()) {
            data.progress["unlocked_titles"] = data.unlockedTitles.size
            TitleProgressManager.autoEquipBestUnlocked(player, data, notify = false)
            TitleStorage.save(player.server, data)
        }

        return unlocked
    }

    fun grant(player: ServerPlayer, title: Title, equipIfEmpty: Boolean = false): Boolean {
        val data = TitleStorage.data(player.server, player.uuid)
        if (!data.unlockedTitles.add(title.id)) return false
        data.progress["unlocked_titles"] = data.unlockedTitles.size
        data.timestamps[title.id] = System.currentTimeMillis()
        if (equipIfEmpty || data.lockedTitleId == null) {
            TitleProgressManager.autoEquipBestUnlocked(player, data, notify = false)
        }
        TitleStorage.save(player.server, data)
        notifyUnlock(player, title)
        return true
    }

    private fun notifyUnlock(player: ServerPlayer, title: Title) {
        val displayStyle = TitleStorage.config(player.server).titleDisplayStyle
        player.sendSystemMessage(CobblemonTrainerPrestige.prefix().append("Title unlocked:"))
        player.sendSystemMessage(TitleDisplayFormatter.decorated(title, displayStyle))
        player.sendSystemMessage(Component.literal("Rarity: ${title.rarity.ptBrName}").withStyle(title.rarity.fallbackColor))

        if (TitleStorage.config(player.server).announceUnlocksGlobally) {
            player.server.playerList.players
                .filter { it.uuid != player.uuid }
                .forEach {
                    it.sendSystemMessage(
                        Component.empty()
                            .append(CobblemonTrainerPrestige.prefix())
                            .append("${player.gameProfile.name} unlocked the title ")
                            .append(TitleDisplayFormatter.decorated(title, displayStyle))
                            .append("!")
                    )
                }
        }
    }
}
