package com.nbp.cobblemon_trainer_prestige.display

import com.nbp.cobblemon_trainer_prestige.storage.TabDisplayMode
import com.nbp.cobblemon_trainer_prestige.storage.TabTitleDisplay
import com.nbp.cobblemon_trainer_prestige.storage.TitleStorage
import com.nbp.cobblemon_trainer_prestige.title.Title
import com.nbp.cobblemon_trainer_prestige.title.TitleRarity
import com.nbp.cobblemon_trainer_prestige.title.TitleRegistry
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

object TitleTabDisplayManager {
    private const val TEAM_PREFIX = "tp_"

    fun refresh(player: ServerPlayer) {
        val scoreboard = player.server.scoreboard
        val playerName = player.scoreboardName
        val data = TitleStorage.data(player.server, player.uuid)
        val title = data.equippedTitleId?.let(TitleRegistry::get)
        val config = TitleStorage.config(player.server)

        if (
            title == null ||
            !config.showTitleInTab ||
            !title.showInTab ||
            config.tabDisplayMode == TabDisplayMode.DISABLED ||
            config.tabTitleDisplay == TabTitleDisplay.DISABLED
        ) {
            clear(player)
            return
        }

        val teamName = teamName(player, title)
        val team = scoreboard.getPlayerTeam(teamName) ?: scoreboard.addPlayerTeam(teamName)
        val colorPlayerName = title.category.isPossessionMaster()
        team.setColor(if (colorPlayerName) title.rarity.fallbackColor else ChatFormatting.RESET)
        team.setPlayerPrefix(Component.empty())
        team.setPlayerSuffix(Component.empty())

        val titleComponent = when (config.tabTitleDisplay) {
            TabTitleDisplay.COMPACT -> TitleDisplayFormatter.compact(title, config.titleDisplayStyle)
            TabTitleDisplay.FULL -> TitleDisplayFormatter.bracket(title, config.titleDisplayStyle)
            TabTitleDisplay.DISABLED -> Component.empty()
        }

        when (config.tabDisplayMode) {
            TabDisplayMode.PREFIX -> team.setPlayerPrefix(Component.empty().append(titleComponent).append(Component.literal(" ")))
            TabDisplayMode.SUFFIX,
            TabDisplayMode.CLEAN_SUFFIX -> team.setPlayerSuffix(Component.empty().append(Component.literal(" ")).append(titleComponent))
            TabDisplayMode.DISABLED -> Unit
        }

        val currentTeam = scoreboard.getPlayersTeam(playerName)
        if (currentTeam == null || currentTeam.name != teamName) {
            if (currentTeam != null && currentTeam.name.startsWith(TEAM_PREFIX)) {
                scoreboard.removePlayerFromTeam(playerName, currentTeam)
            }
            scoreboard.addPlayerToTeam(playerName, team)
        } else {
            scoreboard.onTeamChanged(team)
        }
    }

    fun clear(player: ServerPlayer) {
        val scoreboard = player.server.scoreboard
        val playerName = player.scoreboardName
        val team = scoreboard.getPlayersTeam(playerName)
        if (team != null && team.name.startsWith(TEAM_PREFIX)) {
            scoreboard.removePlayerFromTeam(playerName, team)
        }
    }

    fun refreshAll(server: MinecraftServer) {
        server.playerList.players.forEach(::refresh)
    }

    private fun teamName(player: ServerPlayer, title: Title): String {
        val rarityRank = rarityRank(title.rarity)
        val uuidPart = player.uuid.toString().replace("-", "").take(11)
        return "$TEAM_PREFIX$rarityRank$uuidPart"
    }

    private fun rarityRank(rarity: TitleRarity): Int {
        return TitleRarity.entries.lastIndex - rarity.ordinal
    }

}
