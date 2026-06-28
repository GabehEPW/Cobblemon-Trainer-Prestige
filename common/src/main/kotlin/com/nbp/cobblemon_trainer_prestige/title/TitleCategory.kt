package com.nbp.cobblemon_trainer_prestige.title

enum class TitleCategory(val ptBrName: String) {
    GENERAL("Geral"),
    CAPTURE("Captura"),
    TYPE_MASTER("Tipo Pokemon"),
    SHINY("Shiny"),
    BATTLE("Batalha"),
    EXPLORATION("Exploracao"),
    LEGENDARY("Lendarios"),
    LEGENDARY_MASTER("Mestres Lendarios"),
    MYTHICAL("Miticos"),
    MYTHICAL_MASTER("Mestres Miticos"),
    ULTRA_BEAST("Ultra Bestas"),
    ULTRA_BEAST_MASTER("Mestres Ultra Bestas"),
    BREEDING("Criacao"),
    SOCIAL("Social"),
    EVENT("Evento"),
    SECRET("Secreto"),
    ;

    fun isPossessionMaster(): Boolean {
        return this == LEGENDARY_MASTER || this == MYTHICAL_MASTER || this == ULTRA_BEAST_MASTER
    }
}
