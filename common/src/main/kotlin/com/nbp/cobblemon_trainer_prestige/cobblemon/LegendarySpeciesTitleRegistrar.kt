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
import java.util.UUID

object LegendarySpeciesTitleRegistrar {
    private val registeredSpeciesIds = mutableSetOf<String>()
    private val queuedPlayerScans = linkedSetOf<UUID>()
    private val scanningPlayers = mutableSetOf<UUID>()
    private var queuedScanAll = false
    private var scanningAll = false

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
                        description = "You own ${species.name} in your party or PC.",
                        rarity = group.rarity,
                        category = group.category,
                        visibility = TitleVisibility.VISIBLE,
                        unlockText = "Own ${species.name} in your party or PC.",
                        requirement = TitleRequirement(progressKey, 1, TitleRequirement.Operator.AT_LEAST),
                    )
                )
                added++
            }

        if (added > 0) {
            CobblemonTrainerPrestige.logger.info("Registered $added individual special titles.")
        }
    }

    fun checkPokemon(player: ServerPlayer, pokemon: Pokemon): Boolean {
        val group = SpecialPokemonClassifier.group(pokemon.species) ?: return false
        if (isScanning()) return true

        registerLoadedSpeciesTitles()
        TitleProgressManager.setFlag(player, progressKey(pokemon.species, group))
        requestScanOwnedPokemon(player)
        return true
    }

    fun recordCapture(player: ServerPlayer, pokemon: Pokemon) {
        val species = pokemon.species
        val group = SpecialPokemonClassifier.group(species) ?: return
        registerLoadedSpeciesTitles()
        TitleStorage.claimFirstLegendaryCapture(player.server, titleId(species, group), player.uuid)
        TitleProgressManager.setFlag(player, progressKey(species, group))
    }

    fun scanOwnedPokemon(player: ServerPlayer) {
        if (!scanningPlayers.add(player.uuid)) return

        try {
            registerLoadedSpeciesTitles()

            val titleIds = linkedSetOf<String>()
            val progressKeys = linkedSetOf<String>()
            var scanCompleted = false
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
                scanCompleted = true
            }.onFailure {
                CobblemonTrainerPrestige.logger.warn("Could not scan party/PC for ${player.gameProfile.name}.", it)
            }

            if (scanCompleted) {
                TitleProgressManager.syncLegendaryMasterOwnership(player, titleIds, progressKeys)
            }
        } finally {
            scanningPlayers.remove(player.uuid)
        }
    }

    fun scanAllOnlinePlayers(server: MinecraftServer) {
        if (scanningAll) return

        scanningAll = true
        try {
            server.playerList.players.forEach(::scanOwnedPokemon)
        } finally {
            scanningAll = false
        }
    }

    fun requestScanOwnedPokemon(player: ServerPlayer) {
        if (isScanning()) return
        queuedPlayerScans += player.uuid
    }

    fun requestScanAllOnlinePlayers(server: MinecraftServer) {
        if (isScanning()) return
        queuedScanAll = true
        queuedPlayerScans.clear()
    }

    fun processQueuedScans(server: MinecraftServer) {
        if (isScanning()) return

        if (queuedScanAll) {
            queuedScanAll = false
            queuedPlayerScans.clear()
            scanAllOnlinePlayers(server)
            return
        }

        val playerIds = queuedPlayerScans.toList()
        queuedPlayerScans.clear()
        playerIds.forEach { playerId ->
            server.playerList.getPlayer(playerId)?.let(::scanOwnedPokemon)
        }
    }

    private fun titleId(species: Species, group: SpecialPokemonGroup): String {
        return "${group.titleIdPrefix}_${species.showdownId()}"
    }

    private fun progressKey(species: Species, group: SpecialPokemonGroup): String {
        return "${group.progressPrefix}_${species.showdownId()}"
    }

    private fun isScanning(): Boolean {
        return scanningAll || scanningPlayers.isNotEmpty()
    }
}
