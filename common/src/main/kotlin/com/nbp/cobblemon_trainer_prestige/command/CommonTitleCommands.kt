package com.nbp.cobblemon_trainer_prestige.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import com.nbp.cobblemon_trainer_prestige.CobblemonTrainerPrestige
import com.nbp.cobblemon_trainer_prestige.display.TitleDisplayFormatter
import com.nbp.cobblemon_trainer_prestige.progress.TitleProgressManager
import com.nbp.cobblemon_trainer_prestige.storage.TitleStorage
import com.nbp.cobblemon_trainer_prestige.title.Title
import com.nbp.cobblemon_trainer_prestige.title.TitleCategory
import com.nbp.cobblemon_trainer_prestige.title.TitleRarity
import com.nbp.cobblemon_trainer_prestige.title.TitleRegistry
import com.nbp.cobblemon_trainer_prestige.title.TitleVisibility
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands.argument
import net.minecraft.commands.Commands.literal
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import java.util.concurrent.CompletableFuture

object CommonTitleCommands {
    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(
            literal("prestige")
                .executes { ctx -> listTitles(ctx.source.playerOrException); 1 }
                .then(literal("help").executes { ctx -> help(ctx.source.playerOrException); 1 })
                .then(literal("list").executes { ctx -> listTitles(ctx.source.playerOrException); 1 })
                .then(literal("equip").then(argument("id", StringArgumentType.word())
                    .suggests { ctx, builder -> suggestUnlockedTitles(ctx.source, builder) }
                    .executes { ctx ->
                        TitleProgressManager.equip(ctx.source.playerOrException, StringArgumentType.getString(ctx, "id"))
                        1
                    }))
                .then(literal("lock")
                    .executes { ctx ->
                        TitleProgressManager.lock(ctx.source.playerOrException, null)
                        1
                    }
                    .then(argument("id", StringArgumentType.word())
                        .suggests { ctx, builder -> suggestUnlockedTitles(ctx.source, builder) }
                        .executes { ctx ->
                            TitleProgressManager.lock(ctx.source.playerOrException, StringArgumentType.getString(ctx, "id"))
                            1
                        }))
                .then(literal("unlock").executes { ctx -> TitleProgressManager.unlock(ctx.source.playerOrException); 1 })
                .then(literal("remove").executes { ctx -> TitleProgressManager.remove(ctx.source.playerOrException); 1 })
                .then(literal("current").executes { ctx -> current(ctx.source.playerOrException); 1 })
                .then(literal("info").then(argument("id", StringArgumentType.word())
                    .suggests { _, builder -> suggestAllTitles(builder) }
                    .executes { ctx ->
                        info(ctx.source.playerOrException, StringArgumentType.getString(ctx, "id"))
                        1
                    }))
                .then(literal("progress").executes { ctx -> progress(ctx.source.playerOrException); 1 })
                .then(literal("search").then(argument("nome", StringArgumentType.greedyString()).executes { ctx ->
                    search(ctx.source.playerOrException, StringArgumentType.getString(ctx, "nome"))
                    1
                }))
                .then(literal("category").then(argument("categoria", StringArgumentType.word())
                    .suggests { _, builder -> suggestCategoriesAndRarities(builder) }
                    .executes { ctx ->
                        category(ctx.source.playerOrException, StringArgumentType.getString(ctx, "categoria"))
                        1
                    }))
                .then(literal("rarity").then(argument("raridade", StringArgumentType.word())
                    .suggests { _, builder -> suggestRarities(builder) }
                    .executes { ctx ->
                        rarity(ctx.source.playerOrException, StringArgumentType.getString(ctx, "raridade"))
                        1
                    }))
                .then(literal("grant").requires { it.hasPermission(2) }
                    .then(argument("player", EntityArgument.player())
                        .then(argument("id", StringArgumentType.word())
                            .suggests { _, builder -> suggestAllTitles(builder) }
                            .executes { ctx ->
                                val target = EntityArgument.getPlayer(ctx, "player")
                                val id = StringArgumentType.getString(ctx, "id")
                                TitleProgressManager.grant(ctx.source.player, target, id)
                                1
                            })))
                .then(literal("revoke").requires { it.hasPermission(2) }
                    .then(argument("player", EntityArgument.player())
                        .then(argument("id", StringArgumentType.word())
                            .suggests { _, builder -> suggestAllTitles(builder) }
                            .executes { ctx ->
                                val target = EntityArgument.getPlayer(ctx, "player")
                                val id = StringArgumentType.getString(ctx, "id")
                                val removed = TitleProgressManager.revoke(target, id)
                                ctx.source.sendSuccess({ CobblemonTrainerPrestige.prefix().append(if (removed) "Titulo removido." else "Titulo nao estava desbloqueado.") }, false)
                                1
                            })))
                .then(literal("set").requires { it.hasPermission(2) }
                    .then(argument("player", EntityArgument.player())
                        .then(argument("id", StringArgumentType.word())
                            .suggests { _, builder -> suggestAllTitles(builder) }
                            .executes { ctx ->
                                val target = EntityArgument.getPlayer(ctx, "player")
                                val id = StringArgumentType.getString(ctx, "id")
                                TitleProgressManager.grant(ctx.source.player, target, id)
                                TitleProgressManager.equip(target, id)
                                1
                            })))
                .then(literal("reset").requires { it.hasPermission(2) }
                    .then(argument("player", EntityArgument.player()).executes { ctx ->
                        val target = EntityArgument.getPlayer(ctx, "player")
                        TitleStorage.reset(target.server, target.uuid)
                        ctx.source.sendSuccess({ CobblemonTrainerPrestige.prefix().append("Dados resetados para ${target.gameProfile.name}.") }, false)
                        1
                    }))
                .then(literal("reload").requires { it.hasPermission(2) }.executes { ctx ->
                    TitleStorage.reloadConfig()
                    TitleRegistry.bootstrap()
                    ctx.source.sendSuccess({ CobblemonTrainerPrestige.prefix().append("Config e titulos recarregados.") }, false)
                    1
                })
                .then(literal("addprogress").requires { it.hasPermission(2) }
                    .then(argument("player", EntityArgument.player())
                        .then(argument("progress_key", StringArgumentType.word())
                            .then(argument("amount", IntegerArgumentType.integer()).executes { ctx ->
                                val target = EntityArgument.getPlayer(ctx, "player")
                                TitleProgressManager.addProgress(
                                    target,
                                    StringArgumentType.getString(ctx, "progress_key"),
                                    IntegerArgumentType.getInteger(ctx, "amount")
                                )
                                ctx.source.sendSuccess({ CobblemonTrainerPrestige.prefix().append("Progresso atualizado.") }, false)
                                1
                            }))))
                .then(literal("debug").requires { it.hasPermission(2) }
                    .then(argument("player", EntityArgument.player()).executes { ctx ->
                        debug(ctx.source, EntityArgument.getPlayer(ctx, "player"))
                        1
                    }))
        )
    }

    fun sendJoinHint(player: ServerPlayer) {
        player.sendSystemMessage(
            CobblemonTrainerPrestige.prefix()
                .append("Use /prestige help para ver, equipar, bloquear e filtrar seus titulos.")
        )
    }

    private fun help(player: ServerPlayer) {
        player.sendSystemMessage(CobblemonTrainerPrestige.prefix().append("Comandos de titulos:"))
        player.sendSystemMessage(Component.literal("/prestige - Lista seus titulos por raridade."))
        player.sendSystemMessage(Component.literal("/prestige list - Lista seus titulos por raridade."))
        player.sendSystemMessage(Component.literal("/prestige current - Mostra o titulo equipado."))
        player.sendSystemMessage(Component.literal("/prestige equip <id> - Equipa um titulo desbloqueado."))
        player.sendSystemMessage(Component.literal("/prestige lock [id] - Trava o titulo atual ou escolhido."))
        player.sendSystemMessage(Component.literal("/prestige unlock - Reativa a troca automatica pelo melhor titulo."))
        player.sendSystemMessage(Component.literal("/prestige info <id> - Mostra detalhes e progresso de um titulo."))
        player.sendSystemMessage(Component.literal("/prestige progress - Mostra titulos bloqueados em progresso."))
        player.sendSystemMessage(Component.literal("/prestige search <nome> - Procura titulos por nome ou id."))
        player.sendSystemMessage(Component.literal("/prestige rarity <raridade> - Filtra por COMMON, RARE, LEGENDARY..."))
        player.sendSystemMessage(Component.literal("/prestige category <categoria> - Filtra por CAPTURE, SHINY, BATTLE..."))
    }

    private fun listTitles(player: ServerPlayer) {
        val data = TitleStorage.data(player.server, player.uuid)
        val locked = data.lockedTitleId?.let(TitleRegistry::get)
        player.sendSystemMessage(
            CobblemonTrainerPrestige.prefix()
                .append("Seus titulos:")
                .append(if (locked == null) "" else " Travado: ${locked.displayName}")
        )
        TitleRarity.entries.forEach { rarity ->
            val titles = TitleRegistry.all()
                .filter { it.rarity == rarity }
                .filter { it.visibility != TitleVisibility.HIDDEN || it.id in data.unlockedTitles }

            if (titles.isNotEmpty()) {
                player.sendSystemMessage(Component.literal("=== ${rarity.ptBrName} ===").withStyle(rarity.fallbackColor))
                titles.forEach { title ->
                    sendTitleListLine(player, title, data.unlockedTitles, data.equippedTitleId, data.progress)
                }
            }
        }
    }

    private fun current(player: ServerPlayer) {
        val data = TitleStorage.data(player.server, player.uuid)
        val title = TitleProgressManager.equippedTitle(player)
        if (title == null) {
            CobblemonTrainerPrestige.send(player, Component.literal("Nenhum titulo equipado."))
            return
        }
        player.sendSystemMessage(CobblemonTrainerPrestige.prefix().append("Titulo atual:"))
        player.sendSystemMessage(TitleDisplayFormatter.decorated(title))
        player.sendSystemMessage(Component.literal("Raridade: ${title.rarity.ptBrName}"))
        player.sendSystemMessage(Component.literal("Categoria: ${title.category.ptBrName}"))
        player.sendSystemMessage(Component.literal("Descricao: ${title.description}"))
        player.sendSystemMessage(Component.literal("Auto-equip: ${if (data.lockedTitleId == title.id) "travado neste titulo" else "ativo"}"))
    }

    private fun info(player: ServerPlayer, titleId: String) {
        val title = TitleRegistry.get(titleId)
        if (title == null) {
            CobblemonTrainerPrestige.send(player, Component.literal("Titulo nao encontrado."))
            return
        }
        val data = TitleStorage.data(player.server, player.uuid)
        val unlocked = title.id in data.unlockedTitles
        val hidden = !unlocked && title.visibility != TitleVisibility.VISIBLE
        player.sendSystemMessage(Component.literal("[${title.visibleName(unlocked)}]").withStyle(title.rarity.fallbackColor))
        player.sendSystemMessage(Component.literal("Raridade: ${title.rarity.ptBrName}"))
        if (!hidden) player.sendSystemMessage(Component.literal("Categoria: ${title.category.ptBrName}"))
        if (hidden && title.hintText.isNotBlank()) {
            player.sendSystemMessage(Component.literal("Dica: ${title.hintText}"))
        } else {
            player.sendSystemMessage(Component.literal("Como conseguir: ${title.visibleUnlockText(unlocked)}"))
        }
        if (!unlocked && !hidden) {
            player.sendSystemMessage(Component.literal("Progresso: ${title.requirement.current(data.progress)}/${title.requirement.target}"))
        }
        player.sendSystemMessage(Component.literal("Status: ${if (unlocked) "Desbloqueado" else "Bloqueado"}"))
    }

    private fun progress(player: ServerPlayer) {
        val data = TitleStorage.data(player.server, player.uuid)
        player.sendSystemMessage(CobblemonTrainerPrestige.prefix().append("Seu progresso:"))
        TitleRegistry.all()
            .filter { it.id !in data.unlockedTitles && it.visibility == TitleVisibility.VISIBLE }
            .take(12)
            .forEach {
                player.sendSystemMessage(Component.literal("${it.displayName}: ${it.requirement.current(data.progress)}/${it.requirement.target}"))
            }
    }

    private fun search(player: ServerPlayer, query: String) {
        val data = TitleStorage.data(player.server, player.uuid)
        TitleRegistry.search(query).forEachVisible(player, data.unlockedTitles)
    }

    private fun category(player: ServerPlayer, rawCategory: String) {
        val category = runCatching { TitleCategory.valueOf(rawCategory.uppercase()) }.getOrNull()
        if (category == null) {
            val rarity = runCatching { TitleRarity.valueOf(rawCategory.uppercase()) }.getOrNull()
            if (rarity != null) {
                rarity(player, rarity.name)
                return
            }

            CobblemonTrainerPrestige.send(player, Component.literal("Categoria ou raridade invalida."))
            return
        }
        val data = TitleStorage.data(player.server, player.uuid)
        player.sendSystemMessage(CobblemonTrainerPrestige.prefix().append("Categoria: ${category.ptBrName}"))
        TitleRegistry.byCategory(category).forEachVisible(player, data.unlockedTitles)
    }

    private fun rarity(player: ServerPlayer, rawRarity: String) {
        val rarity = runCatching { TitleRarity.valueOf(rawRarity.uppercase()) }.getOrNull()
        if (rarity == null) {
            CobblemonTrainerPrestige.send(player, Component.literal("Raridade invalida."))
            return
        }

        val data = TitleStorage.data(player.server, player.uuid)
        player.sendSystemMessage(CobblemonTrainerPrestige.prefix().append("Raridade: ${rarity.ptBrName}"))
        TitleRegistry.all()
            .filter { it.rarity == rarity }
            .filter { it.visibility != TitleVisibility.HIDDEN || it.id in data.unlockedTitles }
            .forEach { sendTitleListLine(player, it, data.unlockedTitles, data.equippedTitleId, data.progress) }
    }

    private fun debug(source: CommandSourceStack, target: ServerPlayer) {
        val data = TitleStorage.data(target.server, target.uuid)
        source.sendSuccess({ Component.literal("Trainer Prestige debug ${target.gameProfile.name}: $data") }, false)
    }

    private fun suggestUnlockedTitles(source: CommandSourceStack, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        val player = runCatching { source.playerOrException }.getOrNull()
            ?: return builder.buildFuture()
        return suggestCaseInsensitive(TitleProgressManager.unlockedTitleSuggestionIds(player), builder)
    }

    private fun suggestAllTitles(builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        return suggestCaseInsensitive(TitleRegistry.all().map { it.id }, builder)
    }

    private fun suggestCategoriesAndRarities(builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        return suggestCaseInsensitive(TitleCategory.entries.map { it.name } + TitleRarity.entries.map { it.name }, builder)
    }

    private fun suggestRarities(builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        return suggestCaseInsensitive(TitleRarity.entries.map { it.name }, builder)
    }

    private fun suggestCaseInsensitive(values: Iterable<String>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        val remaining = builder.remaining.lowercase()
        values
            .filter { it.lowercase().startsWith(remaining) || it.replace("_", "").lowercase().startsWith(remaining.replace("_", "")) }
            .forEach(builder::suggest)
        return builder.buildFuture()
    }

    private fun List<Title>.forEachVisible(player: ServerPlayer, unlockedTitles: Set<String>) {
        filter { it.visibility != TitleVisibility.HIDDEN || it.id in unlockedTitles }.forEach {
            val unlocked = it.id in unlockedTitles
            player.sendSystemMessage(Component.literal("${it.id}: ${it.visibleName(unlocked)}").withStyle(it.rarity.fallbackColor))
        }
    }

    private fun sendTitleListLine(
        player: ServerPlayer,
        title: Title,
        unlockedTitles: Set<String>,
        equippedTitleId: String?,
        progress: Map<String, Int>,
    ) {
        val unlocked = title.id in unlockedTitles
        val marker = when {
            equippedTitleId == title.id -> "*"
            unlocked -> "+"
            title.visibility == TitleVisibility.MYSTERY -> "?"
            else -> "x"
        }
        val line = Component.literal("$marker ${title.visibleName(unlocked)}")
            .withStyle(if (unlocked) title.rarity.fallbackColor else net.minecraft.ChatFormatting.GRAY)
        if (!unlocked && title.visibility == TitleVisibility.VISIBLE) {
            line.append(" - ${title.requirement.current(progress)}/${title.requirement.target}")
        }
        player.sendSystemMessage(line)
    }
}
