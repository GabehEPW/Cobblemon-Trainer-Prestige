package com.nbp.cobblemon_trainer_prestige.network

import net.minecraft.resources.ResourceLocation

object TrainerPrestigePackets {
    val SYNC_PLAYER_TITLE: ResourceLocation = id("sync_player_title")
    val SYNC_ALL_VISIBLE_TITLES: ResourceLocation = id("sync_all_visible_titles")
    val OPEN_TITLE_SCREEN: ResourceLocation = id("open_title_screen")
    val EQUIP_TITLE_REQUEST: ResourceLocation = id("equip_title_request")

    private fun id(path: String): ResourceLocation {
        return ResourceLocation.fromNamespaceAndPath("cobblemon_trainer_prestige", path)
    }
}
