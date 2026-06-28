package com.nbp.cobblemon_trainer_prestige

import com.nbp.cobblemon_trainer_prestige.cobblemon.CobblemonProgressHooks
import com.nbp.cobblemon_trainer_prestige.title.TitleRegistry
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer
import org.slf4j.LoggerFactory

object CobblemonTrainerPrestige {
    const val MOD_ID = "cobblemon_trainer_prestige"
    const val MOD_NAME = "Trainer Prestige"

    val logger = LoggerFactory.getLogger("TrainerPrestige")

    fun init() {
        TitleRegistry.bootstrap()
        logger.info("$MOD_NAME carregado com ${TitleRegistry.all().size} titulos registrados.")

        try {
            CobblemonProgressHooks.register()
        } catch (error: NoClassDefFoundError) {
            logger.warn("Cobblemon nao encontrado. Titulos de Cobblemon ficarao inativos.")
        }
    }

    fun prefix(): MutableComponent = Component.literal("[Trainer Prestige] ").withStyle(ChatFormatting.YELLOW)

    fun send(player: ServerPlayer, message: Component) {
        player.sendSystemMessage(prefix().append(message))
    }
}
