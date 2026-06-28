package com.nbp.cobblemon_trainer_prestige.cobblemon

import com.cobblemon.mod.common.api.pokemon.labels.CobblemonPokemonLabels
import com.cobblemon.mod.common.pokemon.Species
import com.nbp.cobblemon_trainer_prestige.title.TitleCategory
import com.nbp.cobblemon_trainer_prestige.title.TitleRarity

object SpecialPokemonClassifier {
    fun group(species: Species): SpecialPokemonGroup? {
        return when {
            species.hasSpecialLabel("ultra_beast", "ultrabeast") || species.normalizedShowdownId() in ULTRA_BEAST_IDS -> SpecialPokemonGroup.ULTRA_BEAST
            species.hasSpecialLabel(CobblemonPokemonLabels.MYTHICAL) || species.normalizedShowdownId() in MYTHICAL_IDS -> SpecialPokemonGroup.MYTHICAL
            species.hasSpecialLabel(CobblemonPokemonLabels.LEGENDARY, "sub_legendary", "sublegendary") || species.normalizedShowdownId() in LEGENDARY_IDS -> SpecialPokemonGroup.LEGENDARY
            else -> null
        }
    }

    private fun Species.hasSpecialLabel(vararg expected: String): Boolean {
        val expectedLabels = expected.map(::normalize).toSet()
        return labels.any { normalize(it) in expectedLabels }
    }

    private fun Species.normalizedShowdownId(): String = normalize(showdownId())

    private fun normalize(value: String): String {
        return value.lowercase()
            .replace("-", "")
            .replace("_", "")
            .replace(" ", "")
            .replace(".", "")
            .replace(":", "")
            .replace("'", "")
        }

    private val LEGENDARY_IDS = setOf(
        "articuno", "zapdos", "moltres", "mewtwo",
        "raikou", "entei", "suicune", "lugia", "hooh",
        "regirock", "regice", "registeel", "latias", "latios", "kyogre", "groudon", "rayquaza",
        "uxie", "mesprit", "azelf", "dialga", "palkia", "heatran", "regigigas", "giratina", "cresselia",
        "cobalion", "terrakion", "virizion", "tornadus", "thundurus", "reshiram", "zekrom", "landorus", "kyurem",
        "xerneas", "yveltal", "zygarde",
        "typenull", "silvally", "tapukoko", "tapulele", "tapubulu", "tapufini", "cosmog", "cosmoem", "solgaleo", "lunala", "necrozma",
        "zacian", "zamazenta", "eternatus", "kubfu", "urshifu", "regieleki", "regidrago", "glastrier", "spectrier", "calyrex", "enamorus",
        "wochien", "chienpao", "tinglu", "chiyu", "koraidon", "miraidon", "okidogi", "munkidori", "fezandipiti", "ogerpon", "terapagos",
    )

    private val MYTHICAL_IDS = setOf(
        "mew", "celebi", "jirachi", "deoxys",
        "phione", "manaphy", "darkrai", "shaymin", "arceus",
        "victini", "keldeo", "meloetta", "genesect",
        "diancie", "hoopa", "volcanion",
        "magearna", "marshadow", "zeraora", "meltan", "melmetal",
        "zarude", "pecharunt",
    )

    private val ULTRA_BEAST_IDS = setOf(
        "nihilego", "buzzwole", "pheromosa", "xurkitree", "celesteela", "kartana", "guzzlord",
        "poipole", "naganadel", "stakataka", "blacephalon",
    )
}

enum class SpecialPokemonGroup(
    val titleIdPrefix: String,
    val progressPrefix: String,
    val titlePrefix: String,
    val rarity: TitleRarity,
    val category: TitleCategory,
) {
    LEGENDARY(
        titleIdPrefix = "mestre_de",
        progressPrefix = "owned_legendary",
        titlePrefix = "Master of",
        rarity = TitleRarity.LEGENDARY,
        category = TitleCategory.LEGENDARY_MASTER,
    ),
    MYTHICAL(
        titleIdPrefix = "mestre_mitico_de",
        progressPrefix = "owned_mythical",
        titlePrefix = "Mythic Master of",
        rarity = TitleRarity.MYTHIC,
        category = TitleCategory.MYTHICAL_MASTER,
    ),
    ULTRA_BEAST(
        titleIdPrefix = "mestre_ultra_de",
        progressPrefix = "owned_ultra_beast",
        titlePrefix = "Ultra Master of",
        rarity = TitleRarity.ULTRA_BEAST,
        category = TitleCategory.ULTRA_BEAST_MASTER,
    ),
}
