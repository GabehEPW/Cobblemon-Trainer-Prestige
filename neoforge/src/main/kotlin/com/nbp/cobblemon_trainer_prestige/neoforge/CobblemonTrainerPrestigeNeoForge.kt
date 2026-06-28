package com.nbp.cobblemon_trainer_prestige.neoforge

import com.nbp.cobblemon_trainer_prestige.CobblemonTrainerPrestige
import com.nbp.cobblemon_trainer_prestige.cobblemon.LegendarySpeciesTitleRegistrar
import com.nbp.cobblemon_trainer_prestige.command.CommonTitleCommands
import com.nbp.cobblemon_trainer_prestige.display.TitleDisplayFormatter
import com.nbp.cobblemon_trainer_prestige.progress.TitleProgressManager
import com.nbp.cobblemon_trainer_prestige.storage.TitleStorage
import net.minecraft.server.level.ServerPlayer
import net.neoforged.neoforge.common.NeoForge
import net.neoforged.neoforge.event.RegisterCommandsEvent
import net.neoforged.neoforge.event.ServerChatEvent
import net.neoforged.neoforge.event.entity.player.PlayerEvent
import net.neoforged.neoforge.event.server.ServerStoppedEvent
import net.neoforged.neoforge.event.tick.ServerTickEvent
import net.neoforged.fml.common.Mod

@Mod(CobblemonTrainerPrestige.MOD_ID)
class CobblemonTrainerPrestigeNeoForge {
    private var scanTicks = 0

    init {
        CobblemonTrainerPrestige.init()
        NeoForge.EVENT_BUS.addListener(::onRegisterCommands)
        NeoForge.EVENT_BUS.addListener(::onPlayerLoggedIn)
        NeoForge.EVENT_BUS.addListener(::onServerChat)
        NeoForge.EVENT_BUS.addListener(::onServerStopped)
        NeoForge.EVENT_BUS.addListener(::onServerTick)
    }

    private fun onRegisterCommands(event: RegisterCommandsEvent) {
        CommonTitleCommands.register(event.dispatcher)
    }

    private fun onPlayerLoggedIn(event: PlayerEvent.PlayerLoggedInEvent) {
        val player = event.entity as? ServerPlayer ?: return
        TitleProgressManager.onPlayerJoin(player)
    }

    private fun onServerChat(event: ServerChatEvent) {
        val title = TitleProgressManager.equippedTitle(event.player) ?: return
        val config = TitleStorage.config(event.player.server)
        if (!config.showTitleInChat || !title.showInChat) return

        val formatted = TitleDisplayFormatter.chat(
            event.player.gameProfile.name,
            title,
            event.rawText,
            TitleProgressManager.colorsPlayerName(event.player, title),
        )
        event.isCanceled = true
        event.player.server.playerList.players.forEach { it.sendSystemMessage(formatted) }
    }

    private fun onServerStopped(event: ServerStoppedEvent) {
        TitleStorage.clearMemoryCache(event.server)
    }

    private fun onServerTick(event: ServerTickEvent.Post) {
        scanTicks++
        if (scanTicks >= AUTO_SCAN_INTERVAL_TICKS) {
            scanTicks = 0
            LegendarySpeciesTitleRegistrar.scanAllOnlinePlayers(event.server)
        }
    }

    private companion object {
        private const val AUTO_SCAN_INTERVAL_TICKS = 20 * 5
    }
}
