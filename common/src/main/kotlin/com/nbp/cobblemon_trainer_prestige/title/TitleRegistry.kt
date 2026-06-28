package com.nbp.cobblemon_trainer_prestige.title

object TitleRegistry {
    private val titles = linkedMapOf<String, Title>()

    fun bootstrap() {
        if (titles.isNotEmpty()) return
        register(
            Title(
                id = "novato",
                displayName = "Rookie",
                description = "A milestone from your Trainer Prestige journey.",
                rarity = TitleRarity.COMMON,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Join the server for the first time.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("join_server", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "treinador_iniciante",
                displayName = "Beginner Trainer",
                description = "A title earned through catches and collection progress.",
                rarity = TitleRarity.COMMON,
                category = TitleCategory.CAPTURE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 1 Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_total", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "primeiro_parceiro",
                displayName = "First Partner",
                description = "A title earned through catches and collection progress.",
                rarity = TitleRarity.COMMON,
                category = TitleCategory.CAPTURE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 1 Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_total", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "primeiro_duelo",
                displayName = "First Duel",
                description = "A title earned through battles and victories.",
                rarity = TitleRarity.COMMON,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Win 1 battle.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("battle_wins", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "explorador",
                displayName = "Explorer",
                description = "A title earned by exploring the world.",
                rarity = TitleRarity.COMMON,
                category = TitleCategory.EXPLORATION,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Walk 5,000 blocks.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("distance_walked", 5000, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "aprendiz_de_campo",
                displayName = "Field Apprentice",
                description = "A milestone from your Trainer Prestige journey.",
                rarity = TitleRarity.COMMON,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Play for 1 hour on the server.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("play_time_minutes", 60, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "curioso",
                displayName = "Curious",
                description = "A title earned by exploring the world.",
                rarity = TitleRarity.COMMON,
                category = TitleCategory.EXPLORATION,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Visit 3 different biomes.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("biomes_visited", 3, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "pequeno_colecionador",
                displayName = "Small Collector",
                description = "A title earned through catches and collection progress.",
                rarity = TitleRarity.COMMON,
                category = TitleCategory.CAPTURE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 10 Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_total", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "treinador_casual",
                displayName = "Casual Trainer",
                description = "A milestone from your Trainer Prestige journey.",
                rarity = TitleRarity.COMMON,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Play for 3 hours on the server.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("play_time_minutes", 180, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "primeiros_passos",
                displayName = "First Steps",
                description = "A milestone from your Trainer Prestige journey.",
                rarity = TitleRarity.COMMON,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Unlock 3 titles.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("unlocked_titles", 3, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "treinador_promissor",
                displayName = "Promising Trainer",
                description = "A title earned through catches and collection progress.",
                rarity = TitleRarity.UNCOMMON,
                category = TitleCategory.CAPTURE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 25 Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_total", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "colecionador",
                displayName = "Collector",
                description = "A title earned through catches and collection progress.",
                rarity = TitleRarity.UNCOMMON,
                category = TitleCategory.CAPTURE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 25 different Pokemon species.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_species_unique", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "duelista",
                displayName = "Duelist",
                description = "A title earned through battles and victories.",
                rarity = TitleRarity.UNCOMMON,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Win 10 battles.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("battle_wins", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mochileiro",
                displayName = "Backpacker",
                description = "A title earned by exploring the world.",
                rarity = TitleRarity.UNCOMMON,
                category = TitleCategory.EXPLORATION,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Visit 10 different biomes.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("biomes_visited", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "evolucionista",
                displayName = "Evolutionist",
                description = "A milestone from your Trainer Prestige journey.",
                rarity = TitleRarity.UNCOMMON,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Evolve 10 Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("evolution_total", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "treinador_persistente",
                displayName = "Persistent Trainer",
                description = "A milestone from your Trainer Prestige journey.",
                rarity = TitleRarity.UNCOMMON,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Play for 10 hours on the server.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("play_time_minutes", 600, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "viajante",
                displayName = "Traveler",
                description = "A title earned by exploring the world.",
                rarity = TitleRarity.UNCOMMON,
                category = TitleCategory.EXPLORATION,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Walk 25,000 blocks.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("distance_walked", 25000, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "aprendiz_de_estrategia",
                displayName = "Strategy Apprentice",
                description = "A title earned through battles and victories.",
                rarity = TitleRarity.UNCOMMON,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Win 5 battles while losing no more than 2 Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("clean_battle_wins", 5, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "treinador_experiente",
                displayName = "Experienced Trainer",
                description = "A title earned through catches and collection progress.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.CAPTURE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 75 Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_total", 75, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "pesquisador_de_campo",
                displayName = "Field Researcher",
                description = "A title earned through catches and collection progress.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.CAPTURE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 75 different Pokemon species.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_species_unique", 75, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "estrategista",
                displayName = "Strategist",
                description = "A title earned through battles and victories.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Win 50 battles.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("battle_wins", 50, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "aventureiro",
                displayName = "Adventurer",
                description = "A title earned by exploring the world.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.EXPLORATION,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Find 10 special structures.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("structures_found", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "toque_brilhante",
                displayName = "Shiny Touch",
                description = "A title earned through shiny hunting.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.SHINY,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 1 shiny Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_shiny", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mestre_das_chamas",
                displayName = "Master of Flames",
                description = "A title earned by mastering a Pokemon type.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 30 Fire-type Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_fire", 30, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mestre_aquatico",
                displayName = "Aquatic Master",
                description = "A title earned by mastering a Pokemon type.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 30 Water-type Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_water", 30, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "guardiao_da_floresta",
                displayName = "Forest Guardian",
                description = "A title earned by mastering a Pokemon type.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 30 Grass-type Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_grass", 30, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mestre_dos_eletricos",
                displayName = "Master of Sparks",
                description = "A title earned by mastering a Pokemon type.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 30 Electric-type Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_electric", 30, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "senhor_do_gelo",
                displayName = "Lord of Ice",
                description = "A title earned by mastering a Pokemon type.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 25 Ice-type Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_ice", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "punhos_de_aco",
                displayName = "Steel Fists",
                description = "A title earned by mastering a Pokemon type.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 25 Fighting-type Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_fighting", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "venenoso",
                displayName = "Toxic",
                description = "A title earned by mastering a Pokemon type.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 25 Poison-type Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_poison", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "senhor_da_terra",
                displayName = "Lord of the Earth",
                description = "A title earned by mastering a Pokemon type.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 25 Ground-type Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_ground", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "domador_dos_ceus",
                displayName = "Sky Tamer",
                description = "A title earned by mastering a Pokemon type.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 30 Flying-type Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_flying", 30, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mente_psiquica",
                displayName = "Psychic Mind",
                description = "A title earned by mastering a Pokemon type.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 25 Psychic-type Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_psychic", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "rei_dos_insetos",
                displayName = "Bug King",
                description = "A title earned by mastering a Pokemon type.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 30 Bug-type Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_bug", 30, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "coracao_de_pedra",
                displayName = "Heart of Stone",
                description = "A title earned by mastering a Pokemon type.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 25 Rock-type Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_rock", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "treinador_fantasma",
                displayName = "Ghost Trainer",
                description = "A title earned by mastering a Pokemon type.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 20 Ghost-type Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_ghost", 20, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "senhor_das_sombras",
                displayName = "Lord of Shadows",
                description = "A title earned by mastering a Pokemon type.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 20 Dark-type Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_dark", 20, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "comandante_de_aco",
                displayName = "Steel Commander",
                description = "A title earned by mastering a Pokemon type.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 25 Steel-type Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_steel", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "treinador_veterano",
                displayName = "Veteran Trainer",
                description = "A title earned through catches and collection progress.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.CAPTURE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 150 Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_total", 150, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mestre_da_pokedex",
                displayName = "Pokedex Master",
                description = "A title earned through catches and collection progress.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.CAPTURE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 200 different Pokemon species.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_species_unique", 200, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "cacador_de_shinies",
                displayName = "Shiny Hunter",
                description = "A title earned through shiny hunting.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.SHINY,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 3 shiny Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_shiny", 3, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "invicto",
                displayName = "Undefeated",
                description = "A title earned through battles and victories.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Complete the required progress for this title.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("battle_win_streak", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "sem_piedade",
                displayName = "Merciless",
                description = "A title earned through battles and victories.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Complete the required progress for this title.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("flawless_wins", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "andarilho",
                displayName = "Wanderer",
                description = "A title earned by exploring the world.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.EXPLORATION,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Walk 100,000 blocks.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("distance_walked", 100000, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "domador_de_dragoes",
                displayName = "Dragon Tamer",
                description = "A title earned by mastering a Pokemon type.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 15 Dragon-type Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_dragon", 15, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mestre_das_fadas",
                displayName = "Fairy Master",
                description = "A title earned by mastering a Pokemon type.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 20 Fairy-type Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_fairy", 20, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "testemunha_lendaria",
                displayName = "Legendary Witness",
                description = "A title earned through legendary Pokemon feats.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.LEGENDARY,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Encounter a legendary Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("encounter_legendary", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "primeiro_ovo",
                displayName = "First Egg",
                description = "A title earned through breeding and hatching eggs.",
                rarity = TitleRarity.COMMON,
                category = TitleCategory.BREEDING,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Hatch 1 egg.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("egg_hatched", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "cuidador_de_ovos",
                displayName = "Egg Caretaker",
                description = "A title earned through breeding and hatching eggs.",
                rarity = TitleRarity.UNCOMMON,
                category = TitleCategory.BREEDING,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Hatch 10 eggs.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("egg_hatched", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "criador_dedicado",
                displayName = "Dedicated Breeder",
                description = "A title earned through breeding and hatching eggs.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.BREEDING,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Hatch 25 eggs.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("egg_hatched", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mestre_criador",
                displayName = "Master Breeder",
                description = "A title earned through breeding and hatching eggs.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.BREEDING,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Hatch 50 eggs.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("egg_hatched", 50, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "lenda_da_criacao",
                displayName = "Breeding Legend",
                description = "A title earned through breeding and hatching eggs.",
                rarity = TitleRarity.LEGENDARY,
                category = TitleCategory.BREEDING,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Hatch 150 eggs.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("egg_hatched", 150, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mestre_treinador",
                displayName = "Master Trainer",
                description = "A milestone from your Trainer Prestige journey.",
                rarity = TitleRarity.LEGENDARY,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 500 Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_total", 500, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "ima_de_shiny",
                displayName = "Shiny Magnet",
                description = "A title earned through shiny hunting.",
                rarity = TitleRarity.LEGENDARY,
                category = TitleCategory.SHINY,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 10 shiny Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_shiny", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "domador_lendario",
                displayName = "Legendary Tamer",
                description = "A title earned through legendary Pokemon feats.",
                rarity = TitleRarity.LEGENDARY,
                category = TitleCategory.LEGENDARY,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 1 legendary Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_legendary", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "domador_mitico",
                displayName = "Mythical Tamer",
                description = "A title earned through mythical Pokemon feats.",
                rarity = TitleRarity.MYTHIC,
                category = TitleCategory.MYTHICAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 1 mythical Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_mythical", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "domador_ultra",
                displayName = "Ultra Tamer",
                description = "A title earned through Ultra Beast feats.",
                rarity = TitleRarity.ULTRA_BEAST,
                category = TitleCategory.ULTRA_BEAST,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 1 Ultra Beast.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_ultra_beast", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "campeao_local",
                displayName = "Local Champion",
                description = "A title earned through battles and victories.",
                rarity = TitleRarity.LEGENDARY,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Win a server tournament or event.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("tournament_wins", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "virada_impossivel",
                displayName = "Impossible Comeback",
                description = "A title earned through battles and victories.",
                rarity = TitleRarity.LEGENDARY,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Win a battle with only 1 Pokemon remaining.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("comeback_wins", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "lenda_da_pokedex",
                displayName = "Pokedex Legend",
                description = "A title earned through catches and collection progress.",
                rarity = TitleRarity.LEGENDARY,
                category = TitleCategory.CAPTURE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 400 different Pokemon species.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_species_unique", 400, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "guardiao_do_mundo",
                displayName = "World Guardian",
                description = "A title earned by exploring the world.",
                rarity = TitleRarity.LEGENDARY,
                category = TitleCategory.EXPLORATION,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Visit 50 different biomes.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("biomes_visited", 50, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "lenda_brilhante",
                displayName = "Shiny Legend",
                description = "A title earned through shiny hunting.",
                rarity = TitleRarity.MYTHIC,
                category = TitleCategory.SHINY,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 25 shiny Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_shiny", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "cacador_de_mitos",
                displayName = "Myth Hunter",
                description = "A title earned through legendary Pokemon feats.",
                rarity = TitleRarity.MYTHIC,
                category = TitleCategory.LEGENDARY,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 3 legendary or mythical Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_legendary_or_mythical", 3, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mestre_das_lendas",
                displayName = "Master of Legends",
                description = "A title earned through legendary Pokemon feats.",
                rarity = TitleRarity.MYTHIC,
                category = TitleCategory.LEGENDARY,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Catch 10 legendary or mythical Pokemon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_legendary_or_mythical", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "a_lenda_do_servidor",
                displayName = "Server Legend",
                description = "A title earned from special server moments.",
                rarity = TitleRarity.MYTHIC,
                category = TitleCategory.EVENT,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Be the first player on the server to unlock 50 titles.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("first_to_50_titles", 1, TitleRequirement.Operator.TRUE),
            )
        )
        register(
            Title(
                id = "treinador_absoluto",
                displayName = "Absolute Trainer",
                description = "A milestone from your Trainer Prestige journey.",
                rarity = TitleRarity.MYTHIC,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Unlock 75 titles.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("unlocked_titles", 75, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "sem_pokebola_nao_da",
                displayName = "No Poke Ball, No Chance",
                description = "A hidden title with unusual requirements.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.SECRET,
                visibility = TitleVisibility.MYSTERY,
                unlockText = "Fail 25 captures in a row.",
                hiddenText = "?????",
                hintText = "Sometimes missing many Poke Balls is an achievement too.",
                requirement = TitleRequirement("failed_capture_streak", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "o_azarado",
                displayName = "The Unlucky One",
                description = "A hidden title with unusual requirements.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.SECRET,
                visibility = TitleVisibility.MYSTERY,
                unlockText = "Lose 10 battles in a row.",
                hiddenText = "?????",
                hintText = "Not every loss is useless.",
                requirement = TitleRequirement("battle_loss_streak", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "abencoado_pelo_rng",
                displayName = "Blessed by RNG",
                description = "A hidden title with unusual requirements.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.SECRET,
                visibility = TitleVisibility.MYSTERY,
                unlockText = "Catch a shiny under an extremely rare server-defined condition.",
                hiddenText = "?????",
                hintText = "Luck does not always abandon you.",
                requirement = TitleRequirement("rare_shiny_condition", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "sofrimento_cromatico",
                displayName = "Chromatic Suffering",
                description = "A hidden title with unusual requirements.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.SECRET,
                visibility = TitleVisibility.MYSTERY,
                unlockText = "Catch many Pokemon without finding a shiny.",
                hiddenText = "?????",
                hintText = "Not every shiny hunter finds sparkle quickly.",
                requirement = TitleRequirement("captures_without_shiny", 500, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "sortudo_demais",
                displayName = "Too Lucky",
                description = "A hidden title with unusual requirements.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.SECRET,
                visibility = TitleVisibility.MYSTERY,
                unlockText = "Catch a rare Pokemon with a very low chance on your first attempt.",
                hiddenText = "?????",
                hintText = "Some catches feel impossible to believe.",
                requirement = TitleRequirement("first_try_rare_capture", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mestre_do_nada",
                displayName = "Master of Nothing",
                description = "A hidden title with unusual requirements.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.SECRET,
                visibility = TitleVisibility.HIDDEN,
                unlockText = "Repeat a server-defined useless action many times.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("useless_action_count", 100, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "perdido_no_mundo",
                displayName = "Lost in the World",
                description = "A title earned by exploring the world.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.EXPLORATION,
                visibility = TitleVisibility.MYSTERY,
                unlockText = "Reach an extreme distance from spawn.",
                hiddenText = "?????",
                hintText = "Spawn is very far behind you.",
                requirement = TitleRequirement("distance_from_spawn", 100000, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "fantasma_do_servidor",
                displayName = "Server Ghost",
                description = "A hidden title with unusual requirements.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.SECRET,
                visibility = TitleVisibility.HIDDEN,
                unlockText = "Play several late-night or unusual-time sessions if enabled.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("midnight_sessions", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "quase_la",
                displayName = "So Close",
                description = "A title earned through battles and victories.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.MYSTERY,
                unlockText = "Lose a battle while leaving the opponent almost defeated.",
                hiddenText = "?????",
                hintText = "Sometimes almost winning hurts more.",
                requirement = TitleRequirement("near_win_losses", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "teimoso",
                displayName = "Stubborn",
                description = "A milestone from your Trainer Prestige journey.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.MYSTERY,
                unlockText = "Try to catch the same Pokemon many times before succeeding.",
                hiddenText = "?????",
                hintText = "Persistence or madness?",
                requirement = TitleRequirement("same_pokemon_capture_attempts", 30, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "o_escolhido",
                displayName = "The Chosen One",
                description = "A hidden title with unusual requirements.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.SECRET,
                visibility = TitleVisibility.HIDDEN,
                unlockText = "Be the first player on the server to catch a shiny.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("first_server_shiny", 1, TitleRequirement.Operator.TRUE),
            )
        )
        register(
            Title(
                id = "primeiro_campeao",
                displayName = "First Champion",
                description = "A title earned from special server moments.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.EVENT,
                visibility = TitleVisibility.HIDDEN,
                unlockText = "Become the first official server champion.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("first_server_champion", 1, TitleRequirement.Operator.TRUE),
            )
        )
        register(
            Title(
                id = "o_imortal",
                displayName = "The Immortal",
                description = "A title earned by exploring the world.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.EXPLORATION,
                visibility = TitleVisibility.MYSTERY,
                unlockText = "Stay alive for many hours.",
                hiddenText = "?????",
                hintText = "Falling is not your style.",
                requirement = TitleRequirement("time_without_death_minutes", 3000, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "rival_publico",
                displayName = "Public Rival",
                description = "A title earned through battles and victories.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.MYSTERY,
                unlockText = "Battle 50 different players.",
                hiddenText = "?????",
                hintText = "You have been making a lot of rivals.",
                requirement = TitleRequirement("unique_players_battled", 50, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "primeira_vitoria_pvp",
                displayName = "First PvP Victory",
                description = "A title earned through battles and victories.",
                rarity = TitleRarity.UNCOMMON,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Win 1 battle against players.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("battle_pvp_wins", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "rival_de_respeito",
                displayName = "Respected Rival",
                description = "A title earned through battles and victories.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Win 5 battles against players.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("battle_pvp_wins", 5, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "sequencia_competitiva",
                displayName = "Competitive Streak",
                description = "A title earned through battles and victories.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Win 3 PvP battles in a row.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("battle_pvp_win_streak", 3, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "amigo_de_batalha",
                displayName = "Battle Friend",
                description = "A title earned through player interaction.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.SOCIAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Battle 10 different players.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("unique_players_battled", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mestre_do_caos",
                displayName = "Master of Chaos",
                description = "A hidden title with unusual requirements.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.SECRET,
                visibility = TitleVisibility.HIDDEN,
                unlockText = "Complete an unusual server-defined combination of feats.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("chaos_condition", 1, TitleRequirement.Operator.TRUE),
            )
        )
        register(
            Title(
                id = "prestigio_i",
                displayName = "Prestige I",
                description = "A milestone from your Trainer Prestige journey.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Unlock 10 titles.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("unlocked_titles", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "prestigio_ii",
                displayName = "Prestige II",
                description = "A milestone from your Trainer Prestige journey.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Unlock 25 titles.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("unlocked_titles", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "prestigio_iii",
                displayName = "Prestige III",
                description = "A milestone from your Trainer Prestige journey.",
                rarity = TitleRarity.LEGENDARY,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Unlock 50 titles.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("unlocked_titles", 50, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "prestigio_maximo",
                displayName = "Maximum Prestige",
                description = "A milestone from your Trainer Prestige journey.",
                rarity = TitleRarity.MYTHIC,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Unlock 100 titles.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("unlocked_titles", 100, TitleRequirement.Operator.AT_LEAST),
            )
        )
    }

    fun register(title: Title) {
        titles[title.id] = title
    }

    fun get(id: String): Title? = titles[id]

    fun all(): Collection<Title> = titles.values

    fun search(query: String): List<Title> {
        val normalized = query.lowercase()
        return titles.values.filter { it.id.contains(normalized) || it.displayName.lowercase().contains(normalized) }
    }

    fun byCategory(category: TitleCategory): List<Title> = titles.values.filter { it.category == category }
}
