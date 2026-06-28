package com.nbp.cobblemon_trainer_prestige.progress

import com.nbp.cobblemon_trainer_prestige.CobblemonTrainerPrestige
import com.nbp.cobblemon_trainer_prestige.command.CommonTitleCommands
import com.nbp.cobblemon_trainer_prestige.display.TitleDisplayFormatter
import com.nbp.cobblemon_trainer_prestige.display.TitleTabDisplayManager
import com.nbp.cobblemon_trainer_prestige.storage.TitleStorage
import com.nbp.cobblemon_trainer_prestige.title.PlayerTitleData
import com.nbp.cobblemon_trainer_prestige.title.Title
import com.nbp.cobblemon_trainer_prestige.title.TitleCategory
import com.nbp.cobblemon_trainer_prestige.title.TitleRegistry
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import java.util.UUID

object TitleProgressManager {
    fun onPlayerJoin(player: ServerPlayer) {
        val data = TitleStorage.data(player.server, player.uuid)
        if ((data.progress["join_server"] ?: 0) < 1) {
            data.progress["join_server"] = 1
        }
        val defaultTitle = TitleRegistry.get(TitleStorage.config(player.server).defaultTitle)
        if (defaultTitle != null && data.unlockedTitles.add(defaultTitle.id)) {
            data.timestamps[defaultTitle.id] = System.currentTimeMillis()
        }
        data.progress["unlocked_titles"] = data.unlockedTitles.size
        TitleUnlockManager.check(player, data)
        autoEquipBestUnlocked(player, data, notify = false)
        TitleStorage.save(player.server, data)
        TitleTabDisplayManager.refreshAll(player.server)
        CommonTitleCommands.sendJoinHint(player)
    }

    fun addProgress(player: ServerPlayer, key: String, amount: Int) {
        val data = TitleStorage.data(player.server, player.uuid)
        data.progress[key] = (data.progress[key] ?: 0) + amount
        data.timestamps[key] = System.currentTimeMillis()
        updateDerivedStreaks(data.progress, key, amount)
        TitleStorage.save(player.server, data)
        TitleUnlockManager.check(player, data)
    }

    fun addProgress(player: ServerPlayer, progressChanges: Map<String, Int>) {
        if (progressChanges.isEmpty()) return

        val data = TitleStorage.data(player.server, player.uuid)
        progressChanges.forEach { (key, amount) ->
            data.progress[key] = (data.progress[key] ?: 0) + amount
            data.timestamps[key] = System.currentTimeMillis()
            updateDerivedStreaks(data.progress, key, amount)
        }
        TitleStorage.save(player.server, data)
        TitleUnlockManager.check(player, data)
    }

    fun addUniqueProgress(player: ServerPlayer, key: String, uniqueValues: Iterable<UUID>) {
        val values = uniqueValues.map { it.toString() }.filter { it != player.uuid.toString() }
        if (values.isEmpty()) return

        val data = TitleStorage.data(player.server, player.uuid)
        val stored = data.uniqueProgress.getOrPut(key) { mutableListOf() }
        var changed = false
        values.forEach { value ->
            if (value !in stored) {
                stored += value
                changed = true
            }
        }

        if (!changed) return
        data.progress[key] = stored.size
        data.timestamps[key] = System.currentTimeMillis()
        TitleStorage.save(player.server, data)
        TitleUnlockManager.check(player, data)
    }

    fun setFlag(player: ServerPlayer, key: String) {
        val data = TitleStorage.data(player.server, player.uuid)
        data.progress[key] = 1
        data.timestamps[key] = System.currentTimeMillis()
        TitleStorage.save(player.server, data)
        TitleUnlockManager.check(player, data)
    }

    fun setFlags(player: ServerPlayer, keys: Iterable<String>) {
        val keyList = keys.toSet()
        if (keyList.isEmpty()) return

        val data = TitleStorage.data(player.server, player.uuid)
        var changed = false
        keyList.forEach { key ->
            if ((data.progress[key] ?: 0) < 1) {
                data.progress[key] = 1
                data.timestamps[key] = System.currentTimeMillis()
                changed = true
            }
        }

        if (!changed) return
        TitleStorage.save(player.server, data)
        TitleUnlockManager.check(player, data)
    }

    fun syncLegendaryMasterOwnership(player: ServerPlayer, ownedTitleIds: Set<String>, ownedProgressKeys: Set<String>) {
        val data = TitleStorage.data(player.server, player.uuid)
        var changed = false

        TitleRegistry.all()
            .filter { it.category.isPossessionMaster() }
            .forEach { title ->
                val ownsTitle = title.id in ownedTitleIds
                if (ownsTitle) {
                    if ((data.progress[title.requirement.progressKey] ?: 0) < 1) {
                        data.progress[title.requirement.progressKey] = 1
                        data.timestamps[title.requirement.progressKey] = System.currentTimeMillis()
                        changed = true
                    }
                } else {
                    if (data.progress.remove(title.requirement.progressKey) != null) changed = true
                    if (data.unlockedTitles.remove(title.id)) changed = true
                    if (data.equippedTitleId == title.id) {
                        data.equippedTitleId = null
                        changed = true
                    }
                    if (data.lockedTitleId == title.id) {
                        data.lockedTitleId = null
                        changed = true
                    }
                }
            }

        ownedProgressKeys.forEach { key ->
            if ((data.progress[key] ?: 0) < 1) {
                data.progress[key] = 1
                data.timestamps[key] = System.currentTimeMillis()
                changed = true
            }
        }

        TitleUnlockManager.check(player, data)

        if (changed) {
            data.progress["unlocked_titles"] = data.unlockedTitles.size
            autoEquipBestUnlocked(player, data, notify = false)
            TitleStorage.save(player.server, data)
            TitleTabDisplayManager.refresh(player)
        }
    }

    fun equip(player: ServerPlayer, titleId: String): Boolean {
        val data = TitleStorage.data(player.server, player.uuid)
        val title = resolveUnlockedTitle(data, titleId)
        if (title == null) {
            CobblemonTrainerPrestige.send(player, Component.literal("Title not found or still locked. Use the id suggested by autocomplete."))
            return false
        }
        if (title.id !in data.unlockedTitles) {
            CobblemonTrainerPrestige.send(player, Component.literal("You have not unlocked this title yet."))
            return false
        }
        data.equippedTitleId = title.id
        TitleStorage.save(player.server, data)
        TitleTabDisplayManager.refresh(player)
        player.sendSystemMessage(
            Component.empty()
                .append(CobblemonTrainerPrestige.prefix())
                .append("You equipped the title: ")
                .append(TitleDisplayFormatter.decorated(title))
        )
        return true
    }

    fun remove(player: ServerPlayer) {
        val data = TitleStorage.data(player.server, player.uuid)
        data.equippedTitleId = null
        data.lockedTitleId = null
        TitleStorage.save(player.server, data)
        TitleTabDisplayManager.clear(player)
        CobblemonTrainerPrestige.send(player, Component.literal("Your title was removed."))
    }

    fun equippedTitle(player: ServerPlayer) =
        TitleStorage.data(player.server, player.uuid).equippedTitleId?.let(TitleRegistry::get)

    fun colorsPlayerName(player: ServerPlayer, title: Title): Boolean {
        return title.category.isPossessionMaster()
    }

    fun lock(player: ServerPlayer, rawTitleId: String?): Boolean {
        val data = TitleStorage.data(player.server, player.uuid)
        val title = if (rawTitleId.isNullOrBlank()) {
            data.equippedTitleId?.let(TitleRegistry::get)
        } else {
            resolveUnlockedTitle(data, rawTitleId)
        }

        if (title == null || title.id !in data.unlockedTitles) {
            CobblemonTrainerPrestige.send(player, Component.literal("Choose an unlocked title to lock."))
            return false
        }

        data.equippedTitleId = title.id
        data.lockedTitleId = title.id
        TitleStorage.save(player.server, data)
        TitleTabDisplayManager.refresh(player)
        player.sendSystemMessage(
            Component.empty()
                .append(CobblemonTrainerPrestige.prefix())
                .append("Title locked: ")
                .append(TitleDisplayFormatter.decorated(title))
        )
        return true
    }

    fun unlock(player: ServerPlayer) {
        val data = TitleStorage.data(player.server, player.uuid)
        val oldLocked = data.lockedTitleId?.let(TitleRegistry::get)
        data.lockedTitleId = null
        autoEquipBestUnlocked(player, data)
        TitleStorage.save(player.server, data)
        TitleTabDisplayManager.refresh(player)

        val message = if (oldLocked == null) {
            Component.literal("No title was locked. Auto-equip enabled.")
        } else {
            Component.empty()
                .append("Title unlocked: ")
                .append(TitleDisplayFormatter.decorated(oldLocked))
                .append(". Auto-equip enabled.")
        }
        player.sendSystemMessage(CobblemonTrainerPrestige.prefix().append(message))
    }

    fun autoEquipBestUnlocked(player: ServerPlayer, data: PlayerTitleData, notify: Boolean = true) {
        val lockedTitle = data.lockedTitleId?.let(TitleRegistry::get)
        if (lockedTitle != null && lockedTitle.id in data.unlockedTitles) {
            data.equippedTitleId = lockedTitle.id
            return
        }
        if (data.lockedTitleId != null) data.lockedTitleId = null

        val best = data.unlockedTitles
            .mapNotNull(TitleRegistry::get)
            .maxWithOrNull(
                compareBy<Title> { it.rarity.ordinal }
                    .thenBy { data.timestamps[it.id] ?: 0L }
            ) ?: return

        if (data.equippedTitleId == best.id) return
        data.equippedTitleId = best.id
        TitleTabDisplayManager.refresh(player)
        if (notify) {
            player.sendSystemMessage(
                Component.empty()
                    .append(CobblemonTrainerPrestige.prefix())
                    .append("Best title auto-equipped: ")
                    .append(TitleDisplayFormatter.decorated(best))
            )
        }
    }

    fun unlockedTitleSuggestionIds(player: ServerPlayer): List<String> {
        val data = TitleStorage.data(player.server, player.uuid)
        return data.unlockedTitles
            .mapNotNull(TitleRegistry::get)
            .sortedWith(compareByDescending<Title> { it.rarity.ordinal }.thenBy { it.displayName })
            .map { it.id }
    }

    private fun resolveUnlockedTitle(data: PlayerTitleData, input: String): Title? {
        val normalized = normalize(input)
        val unlocked = data.unlockedTitles.mapNotNull(TitleRegistry::get)

        return unlocked.firstOrNull { it.id.equals(input, ignoreCase = true) }
            ?: unlocked.firstOrNull { normalize(it.displayName) == normalized }
            ?: unlocked.firstOrNull { normalize(it.id).startsWith(normalized) }
            ?: unlocked.firstOrNull { normalize(it.displayName).startsWith(normalized) }
    }

    fun grant(admin: ServerPlayer?, target: ServerPlayer, titleId: String): Boolean {
        val title = TitleRegistry.get(titleId) ?: return false
        val granted = TitleUnlockManager.grant(target, title)
        admin?.sendSystemMessage(
            Component.empty()
                .append(CobblemonTrainerPrestige.prefix())
                .append("The title ${title.displayName} was granted to ${target.gameProfile.name}.")
        )
        return granted
    }

    fun revoke(target: ServerPlayer, titleId: String): Boolean {
        val data = TitleStorage.data(target.server, target.uuid)
        val removed = data.unlockedTitles.remove(titleId)
        if (data.equippedTitleId == titleId) data.equippedTitleId = null
        if (data.lockedTitleId == titleId) data.lockedTitleId = null
        data.progress["unlocked_titles"] = data.unlockedTitles.size
        autoEquipBestUnlocked(target, data, notify = false)
        TitleStorage.save(target.server, data)
        TitleTabDisplayManager.refresh(target)
        return removed
    }

    private fun updateDerivedStreaks(progress: MutableMap<String, Int>, key: String, amount: Int) {
        if (amount <= 0) return
        when (key) {
            "battle_wins" -> {
                progress["battle_win_streak"] = (progress["battle_win_streak"] ?: 0) + amount
                progress["battle_loss_streak"] = 0
            }
            "battle_pvp_wins" -> {
                progress["battle_pvp_win_streak"] = (progress["battle_pvp_win_streak"] ?: 0) + amount
                progress["battle_pvp_loss_streak"] = 0
            }
            "battle_losses" -> {
                progress["battle_loss_streak"] = (progress["battle_loss_streak"] ?: 0) + amount
                progress["battle_win_streak"] = 0
            }
            "battle_pvp_losses" -> {
                progress["battle_pvp_loss_streak"] = (progress["battle_pvp_loss_streak"] ?: 0) + amount
                progress["battle_pvp_win_streak"] = 0
            }
            "capture_shiny" -> {
                progress["captures_without_shiny"] = 0
            }
            "capture_total" -> {
                progress["captures_without_shiny"] = (progress["captures_without_shiny"] ?: 0) + amount
            }
        }
    }

    private fun normalize(value: String): String {
        return java.text.Normalizer.normalize(value, java.text.Normalizer.Form.NFD)
            .replace("\\p{Mn}+".toRegex(), "")
            .replace(' ', '_')
            .lowercase()
    }
}
