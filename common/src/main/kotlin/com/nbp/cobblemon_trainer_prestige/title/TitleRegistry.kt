package com.nbp.cobblemon_trainer_prestige.title

object TitleRegistry {
    private val titles = linkedMapOf<String, Title>()

    fun bootstrap() {
        if (titles.isNotEmpty()) return
        register(
            Title(
                id = "novato",
                displayName = "Novato",
                description = "Todo grande treinador começa de algum lugar.",
                rarity = TitleRarity.COMMON,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Entre no servidor pela primeira vez.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("join_server", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "treinador_iniciante",
                displayName = "Treinador Iniciante",
                description = "O começo da sua jornada como treinador.",
                rarity = TitleRarity.COMMON,
                category = TitleCategory.CAPTURE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture seu primeiro Pokémon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_total", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "primeiro_parceiro",
                displayName = "Primeiro Parceiro",
                description = "Você encontrou seu primeiro companheiro de jornada.",
                rarity = TitleRarity.COMMON,
                category = TitleCategory.CAPTURE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 1 Pokémon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_total", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "primeiro_duelo",
                displayName = "Primeiro Duelo",
                description = "A primeira vitória de muitas.",
                rarity = TitleRarity.COMMON,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Vença sua primeira batalha.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("battle_wins", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "explorador",
                displayName = "Explorador",
                description = "O mundo é grande demais para ficar parado.",
                rarity = TitleRarity.COMMON,
                category = TitleCategory.EXPLORATION,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Caminhe 5.000 blocos.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("distance_walked", 5000, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "aprendiz_de_campo",
                displayName = "Aprendiz de Campo",
                description = "Você começou a entender como o mundo Pokémon funciona.",
                rarity = TitleRarity.COMMON,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Jogue por 1 hora no servidor.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("play_time_minutes", 60, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "curioso",
                displayName = "Curioso",
                description = "Você não resiste a olhar cada canto do mapa.",
                rarity = TitleRarity.COMMON,
                category = TitleCategory.EXPLORATION,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Visite 3 biomas diferentes.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("biomes_visited", 3, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "pequeno_colecionador",
                displayName = "Pequeno Colecionador",
                description = "Sua coleção começou a tomar forma.",
                rarity = TitleRarity.COMMON,
                category = TitleCategory.CAPTURE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 10 Pokémon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_total", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "treinador_casual",
                displayName = "Treinador Casual",
                description = "Joga no seu ritmo, mas joga com estilo.",
                rarity = TitleRarity.COMMON,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Jogue por 3 horas no servidor.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("play_time_minutes", 180, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "primeiros_passos",
                displayName = "Primeiros Passos",
                description = "Sua jornada começou oficialmente.",
                rarity = TitleRarity.COMMON,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Desbloqueie 3 títulos.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("unlocked_titles", 3, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "treinador_promissor",
                displayName = "Treinador Promissor",
                description = "Você já não é mais um iniciante.",
                rarity = TitleRarity.UNCOMMON,
                category = TitleCategory.CAPTURE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 25 Pokémon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_total", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "colecionador",
                displayName = "Colecionador",
                description = "Sua box começa a ficar interessante.",
                rarity = TitleRarity.UNCOMMON,
                category = TitleCategory.CAPTURE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 25 espécies diferentes.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_species_unique", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "duelista",
                displayName = "Duelista",
                description = "Você está começando a entender as batalhas.",
                rarity = TitleRarity.UNCOMMON,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Vença 10 batalhas.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("battle_wins", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mochileiro",
                displayName = "Mochileiro",
                description = "Você conhece bem os caminhos do mundo.",
                rarity = TitleRarity.UNCOMMON,
                category = TitleCategory.EXPLORATION,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Visite 10 biomas diferentes.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("biomes_visited", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "evolucionista",
                displayName = "Evolucionista",
                description = "Você ajudou seus Pokémon a crescer.",
                rarity = TitleRarity.UNCOMMON,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Evolua 10 Pokémon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("evolution_total", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "treinador_persistente",
                displayName = "Treinador Persistente",
                description = "Você continua seguindo em frente.",
                rarity = TitleRarity.UNCOMMON,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Jogue por 10 horas no servidor.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("play_time_minutes", 600, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "viajante",
                displayName = "Viajante",
                description = "Você já andou mais que muito treinador veterano.",
                rarity = TitleRarity.UNCOMMON,
                category = TitleCategory.EXPLORATION,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Caminhe 25.000 blocos.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("distance_walked", 25000, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "aprendiz_de_estrategia",
                displayName = "Aprendiz de Estratégia",
                description = "Você está aprendendo a vencer com inteligência.",
                rarity = TitleRarity.UNCOMMON,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Vença 5 batalhas sem perder mais de 2 Pokémon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("clean_battle_wins", 5, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "treinador_experiente",
                displayName = "Treinador Experiente",
                description = "Você já tem uma boa história como treinador.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.CAPTURE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 75 Pokémon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_total", 75, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "pesquisador_de_campo",
                displayName = "Pesquisador de Campo",
                description = "Você conhece muitas espécies diferentes.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.CAPTURE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 75 espécies diferentes.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_species_unique", 75, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "estrategista",
                displayName = "Estrategista",
                description = "Suas vitórias não são sorte, são plano.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Vença 50 batalhas.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("battle_wins", 50, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "aventureiro",
                displayName = "Aventureiro",
                description = "Você encara o mapa como uma missão.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.EXPLORATION,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Encontre 10 estruturas especiais.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("structures_found", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "toque_brilhante",
                displayName = "Toque Brilhante",
                description = "O brilho escolheu aparecer para você.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.SHINY,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 1 Pokémon shiny.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_shiny", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mestre_das_chamas",
                displayName = "Mestre das Chamas",
                description = "Seu espírito queima como fogo.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 30 Pokémon do tipo Fogo.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_fire", 30, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mestre_aquatico",
                displayName = "Mestre Aquático",
                description = "Você domina rios, mares e tempestades.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 30 Pokémon do tipo Água.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_water", 30, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "guardiao_da_floresta",
                displayName = "Guardião da Floresta",
                description = "A natureza reconhece sua presença.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 30 Pokémon do tipo Planta.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_grass", 30, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mestre_dos_eletricos",
                displayName = "Mestre dos Elétricos",
                description = "Energia pura acompanha sua jornada.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 30 Pokémon do tipo Elétrico.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_electric", 30, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "senhor_do_gelo",
                displayName = "Senhor do Gelo",
                description = "Frio, preciso e difícil de parar.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 25 Pokémon do tipo Gelo.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_ice", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "punhos_de_aco",
                displayName = "Punhos de Aço",
                description = "Você treina com força e disciplina.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 25 Pokémon do tipo Lutador.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_fighting", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "venenoso",
                displayName = "Venenoso",
                description = "Seus métodos podem ser perigosos.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 25 Pokémon do tipo Venenoso.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_poison", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "senhor_da_terra",
                displayName = "Senhor da Terra",
                description = "Você pisa firme em qualquer terreno.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 25 Pokémon do tipo Terra.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_ground", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "domador_dos_ceus",
                displayName = "Domador dos Céus",
                description = "O céu também faz parte da sua jornada.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 30 Pokémon do tipo Voador.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_flying", 30, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mente_psiquica",
                displayName = "Mente Psíquica",
                description = "Sua força vem da mente.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 25 Pokémon do tipo Psíquico.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_psychic", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "rei_dos_insetos",
                displayName = "Rei dos Insetos",
                description = "Pequenos Pokémon, grande domínio.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 30 Pokémon do tipo Inseto.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_bug", 30, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "coracao_de_pedra",
                displayName = "Coração de Pedra",
                description = "Resistente como uma montanha.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 25 Pokémon do tipo Pedra.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_rock", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "treinador_fantasma",
                displayName = "Treinador Fantasma",
                description = "Nem todos conseguem lidar com o invisível.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 20 Pokémon do tipo Fantasma.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_ghost", 20, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "senhor_das_sombras",
                displayName = "Senhor das Sombras",
                description = "Você entende o poder da escuridão.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 20 Pokémon do tipo Sombrio.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_dark", 20, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "comandante_de_aco",
                displayName = "Comandante de Aço",
                description = "Sua equipe é resistente e imponente.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 25 Pokémon do tipo Aço.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_steel", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "treinador_veterano",
                displayName = "Treinador Veterano",
                description = "Sua jornada já deixou marcas.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.CAPTURE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 150 Pokémon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_total", 150, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mestre_da_pokedex",
                displayName = "Mestre da Pokédex",
                description = "Seu conhecimento sobre espécies é impressionante.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.CAPTURE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 200 espécies diferentes.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_species_unique", 200, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "cacador_de_shinies",
                displayName = "Caçador de Shinies",
                description = "Você é conhecido por encontrar Pokémon brilhantes.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.SHINY,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 3 Pokémon shiny.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_shiny", 3, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "invicto",
                displayName = "Invicto",
                description = "Uma sequência de vitórias digna de respeito.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Vença 10 batalhas seguidas.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("battle_win_streak", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "sem_piedade",
                displayName = "Sem Piedade",
                description = "Você venceu sem dar chance ao adversário.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Vença 10 batalhas sem perder nenhum Pokémon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("flawless_wins", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "andarilho",
                displayName = "Andarilho",
                description = "O mundo parece pequeno para você.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.EXPLORATION,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Caminhe 100.000 blocos.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("distance_walked", 100000, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "domador_de_dragoes",
                displayName = "Domador de Dragões",
                description = "Poucos conseguem conquistar a confiança dos dragões.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 15 Pokémon do tipo Dragão.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_dragon", 15, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mestre_das_fadas",
                displayName = "Mestre das Fadas",
                description = "Beleza, magia e poder em uma só equipe.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.TYPE_MASTER,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 20 Pokémon do tipo Fada.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_type_fairy", 20, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "testemunha_lendaria",
                displayName = "Testemunha Lendária",
                description = "Você viu algo que poucos treinadores presenciam.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.LEGENDARY,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Encontre um Pokémon lendário.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("encounter_legendary", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mestre_criador",
                displayName = "Mestre Criador",
                description = "Você entende a arte da criação Pokémon.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.BREEDING,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Choque 50 ovos.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("egg_hatched", 50, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mestre_treinador",
                displayName = "Mestre Treinador",
                description = "Seu nome já é conhecido entre treinadores.",
                rarity = TitleRarity.LEGENDARY,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 500 Pokémon.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_total", 500, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "ima_de_shiny",
                displayName = "Imã de Shiny",
                description = "Shinies parecem encontrar você.",
                rarity = TitleRarity.LEGENDARY,
                category = TitleCategory.SHINY,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 10 Pokémon shiny.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_shiny", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "domador_lendario",
                displayName = "Domador Lendário",
                description = "Você conquistou o respeito de uma lenda.",
                rarity = TitleRarity.LEGENDARY,
                category = TitleCategory.LEGENDARY,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 1 Pokémon lendário.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_legendary", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "domador_mitico",
                displayName = "Domador Mitico",
                description = "Voce tocou uma historia que poucos conseguem provar.",
                rarity = TitleRarity.MYTHIC,
                category = TitleCategory.MYTHICAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 1 Pokemon mitico.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_mythical", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "domador_ultra",
                displayName = "Domador Ultra",
                description = "Voce dominou uma criatura vinda de alem do mundo comum.",
                rarity = TitleRarity.ULTRA_BEAST,
                category = TitleCategory.ULTRA_BEAST,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 1 Ultra Besta.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_ultra_beast", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "campeao_local",
                displayName = "Campeão Local",
                description = "Você provou seu valor em competição.",
                rarity = TitleRarity.LEGENDARY,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Vença um torneio ou evento do servidor.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("tournament_wins", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "virada_impossivel",
                displayName = "Virada Impossível",
                description = "Quando tudo parecia perdido, você venceu.",
                rarity = TitleRarity.LEGENDARY,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Vença uma batalha com apenas 1 Pokémon restante.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("comeback_wins", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "lenda_da_pokedex",
                displayName = "Lenda da Pokédex",
                description = "Sua coleção é digna de respeito absoluto.",
                rarity = TitleRarity.LEGENDARY,
                category = TitleCategory.CAPTURE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 400 espécies diferentes.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_species_unique", 400, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "guardiao_do_mundo",
                displayName = "Guardião do Mundo",
                description = "Você conhece lugares que muitos nunca verão.",
                rarity = TitleRarity.LEGENDARY,
                category = TitleCategory.EXPLORATION,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Visite 50 biomas diferentes.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("biomes_visited", 50, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "lenda_brilhante",
                displayName = "Lenda Brilhante",
                description = "Seu nome brilha tanto quanto sua coleção.",
                rarity = TitleRarity.MYTHIC,
                category = TitleCategory.SHINY,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 25 Pokémon shiny.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_shiny", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "cacador_de_mitos",
                displayName = "Caçador de Mitos",
                description = "Você persegue histórias que pareciam impossíveis.",
                rarity = TitleRarity.MYTHIC,
                category = TitleCategory.LEGENDARY,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 3 Pokémon lendários ou míticos.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_legendary_or_mythical", 3, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mestre_das_lendas",
                displayName = "Mestre das Lendas",
                description = "Poucos treinadores chegam tão longe.",
                rarity = TitleRarity.MYTHIC,
                category = TitleCategory.LEGENDARY,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Capture 10 Pokémon lendários ou míticos.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("capture_legendary_or_mythical", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "a_lenda_do_servidor",
                displayName = "A Lenda do Servidor",
                description = "Seu nome virou parte da história do servidor.",
                rarity = TitleRarity.MYTHIC,
                category = TitleCategory.EVENT,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Seja o primeiro jogador a alcançar 50 títulos desbloqueados.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("first_to_50_titles", 1, TitleRequirement.Operator.TRUE),
            )
        )
        register(
            Title(
                id = "treinador_absoluto",
                displayName = "Treinador Absoluto",
                description = "Você domina vários caminhos da jornada Pokémon.",
                rarity = TitleRarity.MYTHIC,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Desbloqueie 75 títulos.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("unlocked_titles", 75, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "sem_pokebola_nao_da",
                displayName = "Sem Pokébola Não Dá",
                description = "Você tentou. Tentou muito.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.SECRET,
                visibility = TitleVisibility.MYSTERY,
                unlockText = "Falhe 25 capturas seguidas.",
                hiddenText = "?????",
                hintText = "Às vezes, errar muitas Pokébolas também é uma conquista.",
                requirement = TitleRequirement("failed_capture_streak", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "o_azarado",
                displayName = "O Azarado",
                description = "A derrota também constrói reputação.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.SECRET,
                visibility = TitleVisibility.MYSTERY,
                unlockText = "Perca 10 batalhas seguidas.",
                hiddenText = "?????",
                hintText = "Nem toda derrota é inútil.",
                requirement = TitleRequirement("battle_loss_streak", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "abencoado_pelo_rng",
                displayName = "Abençoado pelo RNG",
                description = "A sorte claramente tem favoritos.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.SECRET,
                visibility = TitleVisibility.MYSTERY,
                unlockText = "Capture um shiny em uma condição extremamente rara definida pelo servidor.",
                hiddenText = "?????",
                hintText = "A sorte nem sempre abandona você.",
                requirement = TitleRequirement("rare_shiny_condition", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "sofrimento_cromatico",
                displayName = "Sofrimento Cromático",
                description = "Você procurou tanto que virou lenda.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.SECRET,
                visibility = TitleVisibility.MYSTERY,
                unlockText = "Passe muito tempo sem encontrar shiny enquanto captura muitos Pokémon.",
                hiddenText = "?????",
                hintText = "Nem todo caçador encontra brilho rápido.",
                requirement = TitleRequirement("captures_without_shiny", 500, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "sortudo_demais",
                displayName = "Sortudo Demais",
                description = "Isso não deveria acontecer tão fácil.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.SECRET,
                visibility = TitleVisibility.MYSTERY,
                unlockText = "Capture um Pokémon raro com chance muito baixa na primeira tentativa.",
                hiddenText = "?????",
                hintText = "Algumas capturas parecem mentira.",
                requirement = TitleRequirement("first_try_rare_capture", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mestre_do_nada",
                displayName = "Mestre do Nada",
                description = "Você fez algo inútil tantas vezes que virou talento.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.SECRET,
                visibility = TitleVisibility.HIDDEN,
                unlockText = "Execute uma ação inútil repetidamente, definida pela config do servidor.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("useless_action_count", 100, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "perdido_no_mundo",
                displayName = "Perdido no Mundo",
                description = "Você foi longe demais. Literalmente.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.EXPLORATION,
                visibility = TitleVisibility.MYSTERY,
                unlockText = "Fique a uma distância absurda do spawn.",
                hiddenText = "?????",
                hintText = "O spawn ficou para trás há muito tempo.",
                requirement = TitleRequirement("distance_from_spawn", 100000, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "fantasma_do_servidor",
                displayName = "Fantasma do Servidor",
                description = "Você aparece, some e ninguém entende.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.SECRET,
                visibility = TitleVisibility.HIDDEN,
                unlockText = "Jogue várias vezes de madrugada ou em horários incomuns, se habilitado na config.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("midnight_sessions", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "quase_la",
                displayName = "Quase Lá",
                description = "Você chegou perto. Muito perto.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.MYSTERY,
                unlockText = "Perca uma batalha deixando o adversário com apenas 1 Pokémon quase derrotado.",
                hiddenText = "?????",
                hintText = "Às vezes, faltar pouco dói mais.",
                requirement = TitleRequirement("near_win_losses", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "teimoso",
                displayName = "Teimoso",
                description = "Você insiste até o mundo cansar.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.MYSTERY,
                unlockText = "Tente capturar o mesmo Pokémon muitas vezes antes de conseguir.",
                hiddenText = "?????",
                hintText = "Persistência ou loucura?",
                requirement = TitleRequirement("same_pokemon_capture_attempts", 30, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "o_escolhido",
                displayName = "O Escolhido",
                description = "Algo raro aconteceu, e foi com você.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.SECRET,
                visibility = TitleVisibility.HIDDEN,
                unlockText = "Ser o primeiro jogador do servidor a capturar um shiny.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("first_server_shiny", 1, TitleRequirement.Operator.TRUE),
            )
        )
        register(
            Title(
                id = "primeiro_campeao",
                displayName = "Primeiro Campeão",
                description = "O primeiro nome marcado na história.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.EVENT,
                visibility = TitleVisibility.HIDDEN,
                unlockText = "Ser o primeiro campeão oficial do servidor.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("first_server_champion", 1, TitleRequirement.Operator.TRUE),
            )
        )
        register(
            Title(
                id = "o_imortal",
                displayName = "O Imortal",
                description = "Você sobreviveu tempo demais para ser comum.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.EXPLORATION,
                visibility = TitleVisibility.MYSTERY,
                unlockText = "Fique muitas horas sem morrer.",
                hiddenText = "?????",
                hintText = "Cair não faz parte do seu estilo.",
                requirement = TitleRequirement("time_without_death_minutes", 3000, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "rival_publico",
                displayName = "Rival Público",
                description = "Todo mundo já sabe que você gosta de briga.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.MYSTERY,
                unlockText = "Desafie muitos players diferentes para batalha.",
                hiddenText = "?????",
                hintText = "Você anda arrumando rival demais.",
                requirement = TitleRequirement("unique_players_battled", 50, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "primeira_vitoria_pvp",
                displayName = "Primeira Vitória PvP",
                description = "Você provou seu valor contra outro treinador de verdade.",
                rarity = TitleRarity.UNCOMMON,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Vença sua primeira batalha contra outro jogador.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("battle_pvp_wins", 1, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "rival_de_respeito",
                displayName = "Rival de Respeito",
                description = "Seu nome começou a circular entre os treinadores do servidor.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Vença 5 batalhas contra jogadores.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("battle_pvp_wins", 5, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "sequencia_competitiva",
                displayName = "Sequência Competitiva",
                description = "Você manteve a cabeça fria em duelos consecutivos.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.BATTLE,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Vença 3 batalhas PvP seguidas.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("battle_pvp_win_streak", 3, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "amigo_de_batalha",
                displayName = "Amigo de Batalha",
                description = "Você duelou com vários amigos e deixou sua marca.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.SOCIAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Batalhe contra 10 jogadores diferentes.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("unique_players_battled", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "mestre_do_caos",
                displayName = "Mestre do Caos",
                description = "Nada ao seu redor parece normal.",
                rarity = TitleRarity.SECRET,
                category = TitleCategory.SECRET,
                visibility = TitleVisibility.HIDDEN,
                unlockText = "Complete uma combinação incomum de feitos definida pelo servidor.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("chaos_condition", 1, TitleRequirement.Operator.TRUE),
            )
        )
        register(
            Title(
                id = "prestigio_i",
                displayName = "Prestígio I",
                description = "Você começou sua escalada de prestígio.",
                rarity = TitleRarity.RARE,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Desbloqueie 10 títulos.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("unlocked_titles", 10, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "prestigio_ii",
                displayName = "Prestígio II",
                description = "Sua reputação está crescendo.",
                rarity = TitleRarity.EPIC,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Desbloqueie 25 títulos.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("unlocked_titles", 25, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "prestigio_iii",
                displayName = "Prestígio III",
                description = "Você já é conhecido no servidor.",
                rarity = TitleRarity.LEGENDARY,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Desbloqueie 50 títulos.",
                hiddenText = "?????",
                hintText = "",
                requirement = TitleRequirement("unlocked_titles", 50, TitleRequirement.Operator.AT_LEAST),
            )
        )
        register(
            Title(
                id = "prestigio_maximo",
                displayName = "Prestígio Máximo",
                description = "Sua jornada virou referência.",
                rarity = TitleRarity.MYTHIC,
                category = TitleCategory.GENERAL,
                visibility = TitleVisibility.VISIBLE,
                unlockText = "Desbloqueie 100 títulos.",
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
