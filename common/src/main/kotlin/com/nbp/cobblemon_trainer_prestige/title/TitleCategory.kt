package com.nbp.cobblemon_trainer_prestige.title

enum class TitleCategory(val ptBrName: String) {
    GENERAL("General"),
    CAPTURE("Capture"),
    TYPE_MASTER("Pokemon Types"),
    SHINY("Shiny"),
    BATTLE("Battle"),
    EXPLORATION("Exploration"),
    LEGENDARY("Legendaries"),
    LEGENDARY_MASTER("Legendary Masters"),
    MYTHICAL("Mythicals"),
    MYTHICAL_MASTER("Mythical Masters"),
    ULTRA_BEAST("Ultra Beasts"),
    ULTRA_BEAST_MASTER("Ultra Beast Masters"),
    BREEDING("Breeding"),
    SOCIAL("Social"),
    EVENT("Event"),
    SECRET("Secret"),
    ;

    fun isPossessionMaster(): Boolean {
        return this == LEGENDARY_MASTER || this == MYTHICAL_MASTER || this == ULTRA_BEAST_MASTER
    }
}
