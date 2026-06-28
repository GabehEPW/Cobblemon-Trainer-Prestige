package com.nbp.cobblemon_trainer_prestige.cobblemon

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.pokemon.Species
import com.nbp.cobblemon_trainer_prestige.CobblemonTrainerPrestige
import com.nbp.cobblemon_trainer_prestige.progress.TitleProgressManager
import com.nbp.cobblemon_trainer_prestige.storage.TitleStorage
import com.nbp.cobblemon_trainer_prestige.title.Title
import com.nbp.cobblemon_trainer_prestige.title.TitleRegistry
import com.nbp.cobblemon_trainer_prestige.title.TitleRequirement
import com.nbp.cobblemon_trainer_prestige.title.TitleVisibility
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

object LegendarySpeciesTitleRegistrar {
    private val registeredSpeciesIds = mutableSetOf<String>()

    fun registerLoadedSpeciesTitles() {
        var added = 0
        PokemonSpecies.species
            .filter { SpecialPokemonClassifier.group(it) != null }
            .sortedBy { it.nationalPokedexNumber }
            .forEach { species ->
                val group = SpecialPokemonClassifier.group(species) ?: return@forEach
                val progressKey = progressKey(species, group)
                val titleId = titleId(species, group)
                if (!registeredSpeciesIds.add(titleId)) return@forEach

                TitleRegistry.register(
                    Title(
                        id = titleId,
                        displayName = "${group.titlePrefix} ${species.name}",
                        description = "Voce possui ${species.name} no time ou no PC.",
                        rarity = group.rarity,
                        category = group.category,
                        visibility = TitleVisibility.VISIBLE,
                        unlockText = "Tenha ${species.name} no seu time ou PC.",
                        requirement = TitleRequirement(progressKey, 1, TitleRequirement.Operator.AT_LEAST),
                    )
                )
                added++
            }

        if (added > 0) {
            CobblemonTrainerPrestige.logger.info("Registrados $added titulos individuais especiais.")
        }
    }

    fun checkPokemon(player: ServerPlayer, pokemon: Pokemon) {
        if (SpecialPokemonClassifier.group(pokemon.species) == null) return
        registerLoadedSpeciesTitles()
        scanOwnedPokemon(player)
    }

    fun recordCapture(player: ServerPlayer, pokemon: Pokemon) {
        val species = pokemon.species
        val group = SpecialPokemonClassifier.group(species) ?: return
        registerLoadedSpeciesTitles()
        TitleStorage.claimFirstLegendaryCapture(player.server, titleId(species, group), player.uuid)
        TitleProgressManager.setFlag(player, progressKey(species, group))
    }

    fun scanOwnedPokemon(player: ServerPlayer) {
        registerLoadedSpeciesTitles()

        val titleIds = linkedSetOf<String>()
        val progressKeys = linkedSetOf<String>()
        runCatching {
            com.cobblemon.mod.common.Cobblemon.storage.getParty(player)
                .forEach { pokemon ->
                    val group = SpecialPokemonClassifier.group(pokemon.species) ?: return@forEach
                    titleIds += titleId(pokemon.species, group)
                    progressKeys += progressKey(pokemon.species, group)
                }

            com.cobblemon.mod.common.Cobblemon.storage.getPC(player)
                .forEach { pokemon ->
                    val group = SpecialPokemonClassifier.group(pokemon.species) ?: return@forEach
                    titleIds += titleId(pokemon.species, group)
                    progressKeys += progressKey(pokemon.species, group)
                }
        }.onFailure {
            CobblemonTrainerPrestige.logger.warn("Nao foi possivel escanear party/PC de ${player.gameProfile.name}.", it)
        }

        TitleProgressManager.syncLegendaryMasterOwnership(player, titleIds, progressKeys)
    }

    fun scanAllOnlinePlayers(server: MinecraftServer) {
        server.playerList.players.forEach(::scanOwnedPokemon)
    }

    private fun titleId(species: Species, group: SpecialPokemonGroup): String {
        return "${group.titleIdPrefix}_${species.showdownId()}"
    }

    private fun progressKey(species: Species, group: SpecialPokemonGroup): String {
        return "${group.progressPrefix}_${species.showdownId()}"
    }
}
