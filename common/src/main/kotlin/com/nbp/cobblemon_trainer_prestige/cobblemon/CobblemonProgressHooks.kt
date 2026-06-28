package com.nbp.cobblemon_trainer_prestige.cobblemon

import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.battles.actor.PlayerBattleActor
import com.nbp.cobblemon_trainer_prestige.CobblemonTrainerPrestige
import com.nbp.cobblemon_trainer_prestige.progress.TitleProgressManager

object CobblemonProgressHooks {
    private var registered = false

    fun register() {
        if (registered) return
        registered = true

        LegendarySpeciesTitleRegistrar.registerLoadedSpeciesTitles()
        PokemonSpecies.observable.subscribe {
            LegendarySpeciesTitleRegistrar.registerLoadedSpeciesTitles()
        }
        CobblemonEvents.COBBLEMON_INITIALISED.subscribe {
            LegendarySpeciesTitleRegistrar.registerLoadedSpeciesTitles()
        }
        CobblemonEvents.DATA_SYNCHRONIZED.subscribe { player ->
            LegendarySpeciesTitleRegistrar.scanOwnedPokemon(player)
        }

        CobblemonEvents.POKEMON_CAPTURED.subscribe { event ->
            val player = event.player
            val pokemon = event.pokemon
            val progress = linkedMapOf(
                "capture_total" to 1,
                "capture_species_unique" to 1,
            )
            pokemon.types.forEach { type ->
                progress["capture_type_${type.name.lowercase()}"] = 1
            }
            if (pokemon.shiny) progress["capture_shiny"] = 1

            when (SpecialPokemonClassifier.group(pokemon.species)) {
                SpecialPokemonGroup.LEGENDARY -> {
                    progress["capture_legendary"] = 1
                    progress["capture_legendary_or_mythical"] = 1
                    progress["encounter_legendary"] = 1
                }
                SpecialPokemonGroup.MYTHICAL -> {
                    progress["capture_mythical"] = 1
                    progress["capture_legendary_or_mythical"] = 1
                }
                SpecialPokemonGroup.ULTRA_BEAST -> {
                    progress["capture_ultra_beast"] = 1
                }
                null -> Unit
            }
            TitleProgressManager.addProgress(player, progress)
            LegendarySpeciesTitleRegistrar.recordCapture(player, pokemon)
        }

        CobblemonEvents.POKEMON_GAINED.subscribe { event ->
            event.playerId.let { playerId ->
                com.cobblemon.mod.common.util.server()?.playerList?.getPlayer(playerId)
            }?.let { player ->
                LegendarySpeciesTitleRegistrar.checkPokemon(player, event.pokemon)
                LegendarySpeciesTitleRegistrar.scanAllOnlinePlayers(player.server)
            }
        }

        CobblemonEvents.BATTLE_VICTORY.subscribe { event ->
            val winnerPlayers = event.winners.filterIsInstance<PlayerBattleActor>().mapNotNull { it.entity }
            val loserPlayers = event.losers.filterIsInstance<PlayerBattleActor>().mapNotNull { it.entity }
            val allPlayerUuids = (winnerPlayers + loserPlayers).map { it.uuid }

            winnerPlayers.forEach {
                val progress = linkedMapOf("battle_wins" to 1)
                if (event.battle.isPvP) {
                    progress["battle_pvp_wins"] = 1
                }
                TitleProgressManager.addProgress(it, progress)
                if (event.battle.isPvP) {
                    TitleProgressManager.addUniqueProgress(it, "unique_players_battled", allPlayerUuids)
                }
            }

            loserPlayers.forEach {
                val progress = linkedMapOf("battle_losses" to 1)
                if (event.battle.isPvP) {
                    progress["battle_pvp_losses"] = 1
                }
                TitleProgressManager.addProgress(it, progress)
                if (event.battle.isPvP) {
                    TitleProgressManager.addUniqueProgress(it, "unique_players_battled", allPlayerUuids)
                }
            }
        }

        CobblemonEvents.EVOLUTION_COMPLETE.subscribe { event ->
            event.pokemon.getOwnerPlayer()?.let { TitleProgressManager.addProgress(it, "evolution_total", 1) }
        }

        CobblemonEvents.HATCH_EGG_POST.subscribe { event ->
            TitleProgressManager.addProgress(event.player, "egg_hatched", 1)
        }

        CobblemonTrainerPrestige.logger.info("Trainer Prestige Cobblemon hooks registered.")
    }
}
