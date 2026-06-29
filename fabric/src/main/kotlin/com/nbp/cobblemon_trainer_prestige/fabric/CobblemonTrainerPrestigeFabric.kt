package com.nbp.cobblemon_trainer_prestige.fabric

import com.nbp.cobblemon_trainer_prestige.CobblemonTrainerPrestige
import com.nbp.cobblemon_trainer_prestige.cobblemon.LegendarySpeciesTitleRegistrar
import com.nbp.cobblemon_trainer_prestige.command.CommonTitleCommands
import com.nbp.cobblemon_trainer_prestige.display.TitleDisplayFormatter
import com.nbp.cobblemon_trainer_prestige.progress.TitleProgressManager
import com.nbp.cobblemon_trainer_prestige.storage.TitleStorage
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents

class CobblemonTrainerPrestigeFabric : ModInitializer {
    private var scanTicks = 0

    override fun onInitialize() {
        CobblemonTrainerPrestige.init()
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            CommonTitleCommands.register(dispatcher)
        }
        ServerPlayConnectionEvents.JOIN.register { handler, _, _ ->
            TitleProgressManager.onPlayerJoin(handler.player)
        }
        ServerLifecycleEvents.SERVER_STOPPED.register { server ->
            TitleStorage.clearMemoryCache(server)
        }
        ServerTickEvents.END_SERVER_TICK.register { server ->
            scanTicks++
            if (scanTicks >= AUTO_SCAN_INTERVAL_TICKS) {
                scanTicks = 0
                LegendarySpeciesTitleRegistrar.scanAllOnlinePlayers(server)
            }
        }
        ServerMessageEvents.ALLOW_CHAT_MESSAGE.register { message, sender, _ ->
            val title = TitleProgressManager.equippedTitle(sender)
            val config = TitleStorage.config(sender.server)
            if (title == null || !config.showTitleInChat || !title.showInChat) {
                true
            } else {
                val formatted = TitleDisplayFormatter.chat(
                    sender.gameProfile.name,
                    title,
                    message.signedContent(),
                    TitleProgressManager.colorsPlayerName(sender, title),
                    config.titleDisplayStyle,
                )
                sender.server.playerList.players.forEach { it.sendSystemMessage(formatted) }
                false
            }
        }
    }

    private companion object {
        private const val AUTO_SCAN_INTERVAL_TICKS = 20 * 5
    }
}
