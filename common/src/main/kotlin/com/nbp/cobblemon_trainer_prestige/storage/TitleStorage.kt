package com.nbp.cobblemon_trainer_prestige.storage

import com.google.gson.GsonBuilder
import com.nbp.cobblemon_trainer_prestige.CobblemonTrainerPrestige
import com.nbp.cobblemon_trainer_prestige.title.PlayerTitleData
import com.nbp.cobblemon_trainer_prestige.title.WorldTitleData
import net.minecraft.server.MinecraftServer
import net.minecraft.world.level.storage.LevelResource
import java.nio.file.Files
import java.nio.file.Path
import java.util.UUID
import kotlin.io.path.exists
import kotlin.io.path.reader
import kotlin.io.path.writer

object TitleStorage {
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val cache = mutableMapOf<PlayerCacheKey, PlayerTitleData>()
    private val configCache = mutableMapOf<String, TrainerPrestigeConfig>()
    private val worldDataCache = mutableMapOf<String, WorldTitleData>()

    fun data(server: MinecraftServer, uuid: UUID): PlayerTitleData {
        return cache.getOrPut(PlayerCacheKey(worldKey(server), uuid)) { loadPlayer(server, uuid) }
    }

    fun save(server: MinecraftServer, data: PlayerTitleData) {
        val path = playerPath(server, data.playerUuid)
        Files.createDirectories(path.parent)
        path.writer().use { gson.toJson(data, it) }
        cache[PlayerCacheKey(worldKey(server), data.playerUuid)] = data
    }

    fun reset(server: MinecraftServer, uuid: UUID): PlayerTitleData {
        val data = PlayerTitleData(uuid)
        save(server, data)
        return data
    }

    fun config(server: MinecraftServer): TrainerPrestigeConfig {
        val worldKey = worldKey(server)
        configCache[worldKey]?.let { return it }

        val path = configPath(server)
        val loaded = if (path.exists()) {
            runCatching { path.reader().use { gson.fromJson(it, TrainerPrestigeConfig::class.java) } }
                .getOrElse {
                    CobblemonTrainerPrestige.logger.warn("Falha ao ler config Trainer Prestige; usando padrao.", it)
                    TrainerPrestigeConfig()
                }
        } else {
            TrainerPrestigeConfig()
        }
        Files.createDirectories(path.parent)
        path.writer().use { gson.toJson(loaded, it) }
        configCache[worldKey] = loaded
        return loaded
    }

    fun reloadConfig() {
        configCache.clear()
    }

    fun claimFirstLegendaryCapture(server: MinecraftServer, titleId: String, uuid: UUID): Boolean {
        val data = worldData(server)
        val existing = data.firstLegendaryCapturers[titleId]
        if (existing != null) return existing == uuid.toString()

        data.firstLegendaryCapturers[titleId] = uuid.toString()
        saveWorldData(server, data)
        return true
    }

    fun isFirstLegendaryCapturer(server: MinecraftServer, titleId: String, uuid: UUID): Boolean {
        return worldData(server).firstLegendaryCapturers[titleId] == uuid.toString()
    }

    fun clearMemoryCache(server: MinecraftServer? = null) {
        if (server == null) {
            cache.clear()
            configCache.clear()
            worldDataCache.clear()
            return
        }

        val worldKey = worldKey(server)
        cache.keys.removeIf { it.worldKey == worldKey }
        configCache.remove(worldKey)
        worldDataCache.remove(worldKey)
    }

    private fun loadPlayer(server: MinecraftServer, uuid: UUID): PlayerTitleData {
        val path = playerPath(server, uuid)
        if (!path.exists()) return PlayerTitleData(uuid)

        return runCatching {
            path.reader().use { gson.fromJson(it, PlayerTitleData::class.java) }
        }.getOrElse {
            CobblemonTrainerPrestige.logger.warn("Falha ao ler dados de titulo de $uuid; usando dados vazios.", it)
            PlayerTitleData(uuid)
        }
    }

    private fun worldData(server: MinecraftServer): WorldTitleData {
        val worldKey = worldKey(server)
        worldDataCache[worldKey]?.let { return it }

        val path = worldDataPath(server)
        val loaded = if (path.exists()) {
            runCatching { path.reader().use { gson.fromJson(it, WorldTitleData::class.java) } }
                .getOrElse {
                    CobblemonTrainerPrestige.logger.warn("Falha ao ler dados globais Trainer Prestige; usando dados vazios.", it)
                    WorldTitleData()
                }
        } else {
            WorldTitleData()
        }

        worldDataCache[worldKey] = loaded
        return loaded
    }

    private fun saveWorldData(server: MinecraftServer, data: WorldTitleData) {
        val path = worldDataPath(server)
        Files.createDirectories(path.parent)
        path.writer().use { gson.toJson(data, it) }
        worldDataCache[worldKey(server)] = data
    }

    private fun basePath(server: MinecraftServer): Path {
        return server.getWorldPath(LevelResource.ROOT).resolve("serverconfig").resolve("trainer_prestige")
    }

    private fun worldKey(server: MinecraftServer): String {
        return basePath(server).toAbsolutePath().normalize().toString()
    }

    private fun playerPath(server: MinecraftServer, uuid: UUID): Path {
        return basePath(server).resolve("players").resolve("$uuid.json")
    }

    private fun configPath(server: MinecraftServer): Path {
        return basePath(server).resolve("config.json")
    }

    private fun worldDataPath(server: MinecraftServer): Path {
        return basePath(server).resolve("world.json")
    }

    private data class PlayerCacheKey(
        val worldKey: String,
        val uuid: UUID,
    )
}
