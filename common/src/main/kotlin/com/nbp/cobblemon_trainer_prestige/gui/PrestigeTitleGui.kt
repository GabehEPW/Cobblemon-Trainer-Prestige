package com.nbp.cobblemon_trainer_prestige.gui

import com.nbp.cobblemon_trainer_prestige.CobblemonTrainerPrestige
import com.nbp.cobblemon_trainer_prestige.progress.TitleProgressManager
import com.nbp.cobblemon_trainer_prestige.storage.TitleStorage
import com.nbp.cobblemon_trainer_prestige.title.PlayerTitleData
import com.nbp.cobblemon_trainer_prestige.title.Title
import com.nbp.cobblemon_trainer_prestige.title.TitleCategory
import com.nbp.cobblemon_trainer_prestige.title.TitleRarity
import com.nbp.cobblemon_trainer_prestige.title.TitleRegistry
import com.nbp.cobblemon_trainer_prestige.title.TitleVisibility
import net.minecraft.ChatFormatting
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ClickType
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.component.ItemLore
import kotlin.math.ceil
import kotlin.math.max

object PrestigeTitleGui {
    private const val SIZE = 54
    private const val ENTRIES_PER_PAGE = 28
    private val entrySlots = listOf(
        10, 11, 12, 13, 14, 15, 16,
        19, 20, 21, 22, 23, 24, 25,
        28, 29, 30, 31, 32, 33, 34,
        37, 38, 39, 40, 41, 42, 43,
    )
    private val categorySlots = listOf(
        19, 20, 21, 22, 23, 24, 25,
        28, 29, 30, 31, 32, 33, 34,
        37, 38, 39, 40, 41, 42, 43,
    )
    private val categoryGroups = listOf(
        CategoryGroup("General", listOf(TitleCategory.GENERAL)),
        CategoryGroup("Capture", listOf(TitleCategory.CAPTURE)),
        CategoryGroup("Pokemon Types", listOf(TitleCategory.TYPE_MASTER)),
        CategoryGroup("Shiny", listOf(TitleCategory.SHINY)),
        CategoryGroup("Battle", listOf(TitleCategory.BATTLE)),
        CategoryGroup("Exploration", listOf(TitleCategory.EXPLORATION)),
        CategoryGroup("Legendaries", listOf(TitleCategory.LEGENDARY, TitleCategory.LEGENDARY_MASTER)),
        CategoryGroup("Mythicals", listOf(TitleCategory.MYTHICAL, TitleCategory.MYTHICAL_MASTER)),
        CategoryGroup("Ultra Bestas", listOf(TitleCategory.ULTRA_BEAST, TitleCategory.ULTRA_BEAST_MASTER)),
        CategoryGroup("Breeding", listOf(TitleCategory.BREEDING)),
        CategoryGroup("Social", listOf(TitleCategory.SOCIAL)),
        CategoryGroup("Evento", listOf(TitleCategory.EVENT)),
        CategoryGroup("Secret", listOf(TitleCategory.SECRET), secret = true),
    )

    fun open(player: ServerPlayer) {
        openMain(player)
    }

    private fun openMain(player: ServerPlayer, page: Int = 0) {
        val pages = categoryPages()
        open(player, GuiState.Main(page.coerceIn(0, pages - 1), pages))
    }

    private fun openList(player: ServerPlayer, filter: TitleFilter, page: Int = 0) {
        val titles = filteredTitles(player, filter)
        val pages = max(1, ceil(titles.size / ENTRIES_PER_PAGE.toDouble()).toInt())
        open(player, GuiState.List(filter, page.coerceIn(0, pages - 1), pages))
    }

    private fun open(player: ServerPlayer, state: GuiState) {
        player.openMenu(
            SimpleMenuProvider(
                { syncId, inventory, _ -> PrestigeTitleMenu(syncId, inventory, state) },
                Component.literal(state.title()).withStyle(ChatFormatting.YELLOW),
            ),
        )
    }

    private fun render(container: Container, player: ServerPlayer, state: GuiState) {
        container.clearContent()
        repeat(SIZE) { slot -> container.setItem(slot, filler()) }

        when (state) {
            is GuiState.Main -> renderMain(container, player, state)
            is GuiState.List -> renderList(container, player, state)
        }
    }

    private fun renderMain(container: Container, player: ServerPlayer, state: GuiState.Main) {
        val data = TitleStorage.data(player.server, player.uuid)
        val current = data.equippedTitleId?.let(TitleRegistry::get)
        val locked = data.lockedTitleId?.let(TitleRegistry::get)
        val unlockedCount = data.unlockedTitles.size
        val visibleCount = visibleTitles(data).size

        container.setItem(
            0,
            button(
                Items.NETHER_STAR.defaultInstance,
                if (current == null) "No title equipped" else "Current: ${current.displayName}",
                listOf(
                    "Unlocked: $unlockedCount/$visibleCount",
                    if (locked == null) "Auto-equip active" else "Locked: ${locked.displayName}",
                    "Use the buttons below to filter and manage titles.",
                ),
                current?.rarity?.fallbackColor ?: ChatFormatting.YELLOW,
            ),
        )

        container.setItem(1, button(Items.BOOK.defaultInstance, "All", listOf("Shows all visible titles."), ChatFormatting.WHITE))
        container.setItem(2, button(Items.EMERALD.defaultInstance, "Unlocked", listOf("Shows only titles you have unlocked."), ChatFormatting.GREEN))
        container.setItem(3, button(Items.CLOCK.defaultInstance, "In Progress", listOf("Shows locked titles with progress."), ChatFormatting.AQUA))
        container.setItem(5, button(Items.BARRIER.defaultInstance, "Remove", listOf("Removes the equipped title."), ChatFormatting.RED))
        container.setItem(6, button(Items.LIME_DYE.defaultInstance, "Auto-equip", listOf("Unlocks and resumes automatic best-title selection."), ChatFormatting.GREEN))
        container.setItem(
            7,
            if (locked == null) {
                button(Items.NAME_TAG.defaultInstance, "Lock", listOf("Locks the current title."), ChatFormatting.GOLD)
            } else {
                button(
                    Items.TRIPWIRE_HOOK.defaultInstance,
                    "Unlock",
                    listOf("Title locked: ${locked.displayName}", "Click to enable auto-equip again."),
                    ChatFormatting.GREEN,
                )
            },
        )
        container.setItem(8, button(Items.WRITABLE_BOOK.defaultInstance, "Help", helpLore(), ChatFormatting.YELLOW))

        val categories = categoryGroups
            .drop(state.page * categorySlots.size)
            .take(categorySlots.size)
        categories.forEachIndexed { index, group ->
            container.setItem(
                categorySlots[index],
                categoryButton(group),
            )
        }

        if (state.page > 0) {
            container.setItem(45, button(Items.ARROW.defaultInstance, "Previous Page", listOf("Returns to the previous category page."), ChatFormatting.YELLOW))
        }
        container.setItem(49, button(Items.COMPASS.defaultInstance, "Categories", listOf("Page ${state.page + 1}/${state.pages}", "Click a category to filter."), ChatFormatting.YELLOW))
        if (state.page < state.pages - 1) {
            container.setItem(53, button(Items.ARROW.defaultInstance, "Next Page", listOf("Moves to the next category page."), ChatFormatting.YELLOW))
        }
    }

    private fun renderList(container: Container, player: ServerPlayer, state: GuiState.List) {
        val data = TitleStorage.data(player.server, player.uuid)
        val titles = filteredTitles(player, state.filter)
        val start = state.page * ENTRIES_PER_PAGE
        val pageTitles = titles.drop(start).take(ENTRIES_PER_PAGE)

        container.setItem(0, button(Items.ARROW.defaultInstance, "Back", listOf("Click to return to the main menu."), ChatFormatting.YELLOW))
        container.setItem(
            4,
            button(
                Items.BOOK.defaultInstance,
                state.filter.displayName,
                listOf("Page ${state.page + 1}/${state.pages}", "Showing ${pageTitles.size} of ${titles.size} titles."),
                ChatFormatting.YELLOW,
            ),
        )
        container.setItem(8, button(Items.WRITABLE_BOOK.defaultInstance, "Help da lista", listOf("Left click: equip.", "Right click: lock.", "Locked: shows details in chat."), ChatFormatting.AQUA))

        if (pageTitles.isEmpty()) {
            container.setItem(22, button(Items.BARRIER.defaultInstance, "Nothing Found", listOf("This filter has no visible titles."), ChatFormatting.GRAY))
        } else {
            pageTitles.forEachIndexed { index, title ->
                container.setItem(entrySlots[index], titleItem(title, data))
            }
        }

        if (state.page > 0) {
            container.setItem(45, button(Items.ARROW.defaultInstance, "Previous Page", listOf("Click to go back one page."), ChatFormatting.YELLOW))
        }
        container.setItem(49, button(Items.COMPASS.defaultInstance, "Main Menu", listOf("Click to return to the start."), ChatFormatting.YELLOW))
        if (state.page < state.pages - 1) {
            container.setItem(53, button(Items.ARROW.defaultInstance, "Next Page", listOf("Click to go forward one page."), ChatFormatting.YELLOW))
        }
    }

    private fun handleClick(state: GuiState, slotId: Int, button: Int, player: ServerPlayer) {
        when (state) {
            is GuiState.Main -> handleMainClick(state, slotId, player)
            is GuiState.List -> handleListClick(state, slotId, button, player)
        }
    }

    private fun handleMainClick(state: GuiState.Main, slotId: Int, player: ServerPlayer) {
        when (slotId) {
            1 -> openList(player, TitleFilter.All)
            2 -> openList(player, TitleFilter.Unlocked)
            3 -> openList(player, TitleFilter.Progress)
            5 -> {
                TitleProgressManager.remove(player)
                openMain(player, state.page)
            }
            6 -> {
                TitleProgressManager.unlock(player)
                openMain(player, state.page)
            }
            7 -> {
                val data = TitleStorage.data(player.server, player.uuid)
                if (data.lockedTitleId == null) {
                    TitleProgressManager.lock(player, null)
                } else {
                    TitleProgressManager.unlock(player)
                }
                openMain(player, state.page)
            }
            8 -> {
                sendHelp(player)
                openMain(player, state.page)
            }
            45 -> if (state.page > 0) openMain(player, state.page - 1)
            49 -> openMain(player, state.page)
            53 -> if (state.page < state.pages - 1) openMain(player, state.page + 1)
            in categorySlots -> {
                val group = categoryGroups
                    .drop(state.page * categorySlots.size)
                    .getOrNull(categorySlots.indexOf(slotId)) ?: return
                openList(player, TitleFilter.ByCategories(group.displayName, group.categories))
            }
        }
    }

    private fun handleListClick(state: GuiState.List, slotId: Int, button: Int, player: ServerPlayer) {
        when (slotId) {
            0, 49 -> openMain(player)
            45 -> if (state.page > 0) openList(player, state.filter, state.page - 1)
            53 -> if (state.page < state.pages - 1) openList(player, state.filter, state.page + 1)
            8 -> {
                sendHelp(player)
                openList(player, state.filter, state.page)
            }
            in entrySlots -> {
                val index = entrySlots.indexOf(slotId)
                val title = filteredTitles(player, state.filter).drop(state.page * ENTRIES_PER_PAGE).getOrNull(index) ?: return
                val data = TitleStorage.data(player.server, player.uuid)
                if (title.id in data.unlockedTitles) {
                    if (button == 1) {
                        TitleProgressManager.lock(player, title.id)
                    } else {
                        TitleProgressManager.equip(player, title.id)
                    }
                    openList(player, state.filter, state.page)
                } else {
                    sendTitleInfo(player, title, data)
                }
            }
        }
    }

    private fun titleItem(title: Title, data: PlayerTitleData): ItemStack {
        val unlocked = title.id in data.unlockedTitles
        if (!unlocked && title.visibility != TitleVisibility.VISIBLE) {
            return button(
                Items.BEDROCK.defaultInstance,
                "?????",
                listOf("?????"),
                ChatFormatting.DARK_GRAY,
            )
        }

        val equipped = data.equippedTitleId == title.id
        val locked = data.lockedTitleId == title.id
        val progress = title.requirement.current(data.progress)
        val status = when {
            locked -> "Locked"
            equipped -> "Equipped"
            unlocked -> "Unlocked"
            title.visibility == TitleVisibility.MYSTERY -> "Mystery"
            else -> "Locked"
        }
        val lore = mutableListOf(
            "Status: $status",
            "Rarity: ${title.rarity.ptBrName}",
            "Category: ${title.category.ptBrName}",
        )
        if (unlocked || title.visibility == TitleVisibility.VISIBLE) {
            lore += "How to unlock: ${title.unlockText}"
        } else if (title.hintText.isNotBlank()) {
            lore += "Hint: ${title.hintText}"
        }
        if (!unlocked && title.visibility == TitleVisibility.VISIBLE) {
            lore += "Progress: $progress/${title.requirement.target}"
        }
        lore += if (unlocked) {
            listOf("Left click to equip.", "Right click to lock.")
        } else {
            listOf("Click to show details in chat.")
        }

        return button(
            titleStack(title, unlocked),
            title.visibleName(unlocked),
            lore,
            if (unlocked) title.rarity.fallbackColor else ChatFormatting.GRAY,
        )
    }

    private fun sendTitleInfo(player: ServerPlayer, title: Title, data: PlayerTitleData) {
        val unlocked = title.id in data.unlockedTitles
        val hidden = !unlocked && title.visibility != TitleVisibility.VISIBLE
        player.sendSystemMessage(Component.literal("[${title.visibleName(unlocked)}]").withStyle(title.rarity.fallbackColor))
        player.sendSystemMessage(Component.literal("Rarity: ${title.rarity.ptBrName}"))
        if (!hidden) player.sendSystemMessage(Component.literal("Category: ${title.category.ptBrName}"))
        if (hidden && title.hintText.isNotBlank()) {
            player.sendSystemMessage(Component.literal("Hint: ${title.hintText}"))
        } else {
            player.sendSystemMessage(Component.literal("How to unlock: ${title.visibleUnlockText(unlocked)}"))
        }
        if (!unlocked && !hidden) {
            player.sendSystemMessage(Component.literal("Progress: ${title.requirement.current(data.progress)}/${title.requirement.target}"))
        }
    }

    private fun sendHelp(player: ServerPlayer) {
        player.sendSystemMessage(CobblemonTrainerPrestige.prefix().append("Title menu:"))
        player.sendSystemMessage(Component.literal("/prestige - Opens this interface."))
        player.sendSystemMessage(Component.literal("/prestige list - Lists titles in chat."))
        player.sendSystemMessage(Component.literal("/prestige equip <id> - Equips an unlocked title."))
        player.sendSystemMessage(Component.literal("/prestige lock [id] - Locks the current or chosen title."))
        player.sendSystemMessage(Component.literal("/prestige unlock - Enables automatic swapping again."))
        player.sendSystemMessage(Component.literal("/prestige remove - Removes the current title."))
        player.sendSystemMessage(Component.literal("/prestige info <id> - Shows details for a title."))
        player.sendSystemMessage(Component.literal("/prestige rarity <rarity> and /prestige category <category> - Chat filters."))
    }

    private fun filteredTitles(player: ServerPlayer, filter: TitleFilter): List<Title> {
        val data = TitleStorage.data(player.server, player.uuid)
        val titles = visibleTitles(data)
            .filter { title ->
                when (filter) {
                    TitleFilter.All -> true
                    TitleFilter.Unlocked -> title.id in data.unlockedTitles
                    TitleFilter.Progress -> title.id !in data.unlockedTitles && title.visibility == TitleVisibility.VISIBLE
                    is TitleFilter.ByRarity -> title.rarity == filter.rarity
                    is TitleFilter.ByCategory -> title.category == filter.category
                    is TitleFilter.ByCategories -> title.category in filter.categories
                }
            }
        return sortTitles(titles, filter, data)
    }

    private fun sortTitles(titles: List<Title>, filter: TitleFilter, data: PlayerTitleData): List<Title> {
        return if (
            filter is TitleFilter.All ||
            filter is TitleFilter.Unlocked ||
            filter is TitleFilter.ByCategory ||
            filter is TitleFilter.ByCategories
        ) {
            titles.sortedWith(
                compareBy<Title> { it.rarity.ordinal }
                    .thenBy { it.requirement.target }
                    .thenBy { it.category.ordinal }
                    .thenBy { it.displayName },
            )
        } else {
            titles.sortedWith(
                compareByDescending<Title> { it.requirement.current(data.progress) > 0 }
                    .thenByDescending { progressRatio(it, data) }
                    .thenBy { it.rarity.ordinal }
                    .thenBy { it.requirement.target }
                    .thenBy { it.displayName },
            )
        }
    }

    private fun progressRatio(title: Title, data: PlayerTitleData): Double {
        if (title.requirement.target <= 0) return 0.0
        return title.requirement.current(data.progress).toDouble() / title.requirement.target.toDouble()
    }

    private fun visibleTitles(data: PlayerTitleData): List<Title> {
        return TitleRegistry.all()
            .filter { it.visibility != TitleVisibility.HIDDEN || it.id in data.unlockedTitles }
    }

    private fun button(stack: ItemStack, name: String, lore: List<String>, color: ChatFormatting): ItemStack {
        stack.set(DataComponents.CUSTOM_NAME, Component.literal(name).withStyle(color))
        stack.set(DataComponents.LORE, ItemLore(lore.map { Component.literal(it).withStyle(ChatFormatting.GRAY) }))
        return stack
    }

    private fun filler(): ItemStack {
        val stack = Items.GRAY_STAINED_GLASS_PANE.defaultInstance
        stack.set(DataComponents.CUSTOM_NAME, Component.literal(" "))
        return stack
    }

    private fun titleStack(title: Title, unlocked: Boolean): ItemStack {
        if (!unlocked) return Items.GRAY_DYE.defaultInstance
        if (title.category.isPossessionMaster()) return Items.NETHER_STAR.defaultInstance
        return when (title.rarity) {
            TitleRarity.COMMON -> Items.PAPER.defaultInstance
            TitleRarity.UNCOMMON -> Items.LIME_DYE.defaultInstance
            TitleRarity.RARE -> Items.DIAMOND.defaultInstance
            TitleRarity.EPIC -> Items.AMETHYST_SHARD.defaultInstance
            TitleRarity.LEGENDARY -> Items.GOLD_INGOT.defaultInstance
            TitleRarity.ULTRA_BEAST -> Items.ENDER_EYE.defaultInstance
            TitleRarity.MYTHIC -> Items.ECHO_SHARD.defaultInstance
            TitleRarity.SECRET -> Items.DRAGON_BREATH.defaultInstance
        }
    }

    private fun rarityItem(rarity: TitleRarity): ItemStack {
        return when (rarity) {
            TitleRarity.COMMON -> Items.PAPER.defaultInstance
            TitleRarity.UNCOMMON -> Items.LIME_DYE.defaultInstance
            TitleRarity.RARE -> Items.DIAMOND.defaultInstance
            TitleRarity.EPIC -> Items.AMETHYST_SHARD.defaultInstance
            TitleRarity.LEGENDARY -> Items.GOLD_INGOT.defaultInstance
            TitleRarity.ULTRA_BEAST -> Items.ENDER_EYE.defaultInstance
            TitleRarity.MYTHIC -> Items.ECHO_SHARD.defaultInstance
            TitleRarity.SECRET -> Items.DRAGON_BREATH.defaultInstance
        }
    }

    private fun categoryButton(group: CategoryGroup): ItemStack {
        return button(
            categoryItem(group),
            if (group.secret) "?????" else group.displayName,
            if (group.secret) listOf("?????") else categoryLore(group),
            if (group.secret) ChatFormatting.DARK_GRAY else ChatFormatting.WHITE,
        )
    }

    private fun categoryItem(group: CategoryGroup): ItemStack {
        if (group.secret) return Items.BEDROCK.defaultInstance
        return when (group.categories.first()) {
            TitleCategory.GENERAL -> Items.COMPASS.defaultInstance
            TitleCategory.CAPTURE -> Items.SNOWBALL.defaultInstance
            TitleCategory.TYPE_MASTER -> Items.BLAZE_POWDER.defaultInstance
            TitleCategory.SHINY -> Items.GLOWSTONE_DUST.defaultInstance
            TitleCategory.BATTLE -> Items.IRON_SWORD.defaultInstance
            TitleCategory.EXPLORATION -> Items.MAP.defaultInstance
            TitleCategory.LEGENDARY -> Items.GOLD_INGOT.defaultInstance
            TitleCategory.LEGENDARY_MASTER -> Items.NETHER_STAR.defaultInstance
            TitleCategory.MYTHICAL -> Items.ECHO_SHARD.defaultInstance
            TitleCategory.MYTHICAL_MASTER -> Items.NETHER_STAR.defaultInstance
            TitleCategory.ULTRA_BEAST -> Items.ENDER_EYE.defaultInstance
            TitleCategory.ULTRA_BEAST_MASTER -> Items.NETHER_STAR.defaultInstance
            TitleCategory.BREEDING -> Items.EGG.defaultInstance
            TitleCategory.SOCIAL -> Items.PLAYER_HEAD.defaultInstance
            TitleCategory.EVENT -> Items.FIREWORK_ROCKET.defaultInstance
            TitleCategory.SECRET -> Items.BEDROCK.defaultInstance
        }
    }

    private fun categoryLore(group: CategoryGroup): List<String> {
        val description = when (group.categories.first()) {
            TitleCategory.GENERAL -> "General titles from your journey."
            TitleCategory.CAPTURE -> "Titles tied to catches and collection."
            TitleCategory.TYPE_MASTER -> "Titles for mastering Pokemon types."
            TitleCategory.SHINY -> "Titles for shiny hunters."
            TitleCategory.BATTLE -> "Titles for battles and victories."
            TitleCategory.EXPLORATION -> "Titles for world exploration."
            TitleCategory.LEGENDARY -> "Titles for legendary feats and ownership."
            TitleCategory.LEGENDARY_MASTER -> "Titles for owning specific legendary Pokemon."
            TitleCategory.MYTHICAL -> "Titles for mythical feats and ownership."
            TitleCategory.MYTHICAL_MASTER -> "Titles for owning specific mythical Pokemon."
            TitleCategory.ULTRA_BEAST -> "Titles for Ultra Beast feats and ownership."
            TitleCategory.ULTRA_BEAST_MASTER -> "Titles for owning specific Ultra Beasts."
            TitleCategory.BREEDING -> "Titles tied to breeding and eggs."
            TitleCategory.SOCIAL -> "Social titles and player interaction."
            TitleCategory.EVENT -> "Titles from events and special moments."
            TitleCategory.SECRET -> "?????"
        }
        return listOf(description, "Click to view this category.")
    }

    private fun categoryPages(): Int {
        return max(1, ceil(categoryGroups.size / categorySlots.size.toDouble()).toInt())
    }

    private fun helpLore(): List<String> {
        return listOf(
            "Click to receive commands in chat.",
            "In lists, left click equips and right click locks.",
            "Use /prestige list if you prefer text.",
        )
    }

    private sealed interface GuiState {
        fun title(): String

        data class Main(val page: Int, val pages: Int) : GuiState {
            override fun title(): String = "Trainer Prestige"
        }

        data class List(val filter: TitleFilter, val page: Int, val pages: Int) : GuiState {
            override fun title(): String = "TP - ${filter.shortName()}"
        }
    }

    private sealed interface TitleFilter {
        val displayName: String

        data object All : TitleFilter {
            override val displayName: String = "All"
        }

        data object Unlocked : TitleFilter {
            override val displayName: String = "Unlocked"
        }

        data object Progress : TitleFilter {
            override val displayName: String = "In Progress"
        }

        data class ByRarity(val rarity: TitleRarity) : TitleFilter {
            override val displayName: String = rarity.ptBrName
        }

        data class ByCategory(val category: TitleCategory) : TitleFilter {
            override val displayName: String = category.ptBrName
        }

        data class ByCategories(override val displayName: String, val categories: List<TitleCategory>) : TitleFilter

        fun shortName(): String {
            return when (this) {
                All -> "All"
                Unlocked -> "Unlocked"
                Progress -> "Progress"
                is ByRarity -> rarity.ptBrName
                is ByCategory -> category.ptBrName
                is ByCategories -> displayName
            }
        }
    }

    private data class CategoryGroup(
        val displayName: String,
        val categories: List<TitleCategory>,
        val secret: Boolean = false,
    )

    private class PrestigeTitleMenu(
        syncId: Int,
        playerInventory: Inventory,
        private val state: GuiState,
    ) : AbstractContainerMenu(MenuType.GENERIC_9x6, syncId) {
        private val container = SimpleContainer(SIZE)

        init {
            val serverPlayer = playerInventory.player as? ServerPlayer
            if (serverPlayer != null) render(container, serverPlayer, state)
            repeat(6) { row ->
                repeat(9) { column ->
                    addSlot(LockedSlot(container, column + row * 9, 8 + column * 18, 18 + row * 18))
                }
            }
            repeat(3) { row ->
                repeat(9) { column ->
                    addSlot(Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 140 + row * 18))
                }
            }
            repeat(9) { column ->
                addSlot(Slot(playerInventory, column, 8 + column * 18, 198))
            }
        }

        override fun clicked(slotId: Int, button: Int, clickType: ClickType, player: Player) {
            if (slotId in 0 until SIZE) {
                val serverPlayer = player as? ServerPlayer ?: return
                handleClick(state, slotId, button, serverPlayer)
                return
            }
            super.clicked(slotId, button, clickType, player)
        }

        override fun quickMoveStack(player: Player, index: Int): ItemStack {
            return ItemStack.EMPTY
        }

        override fun stillValid(player: Player): Boolean = true
    }

    private class LockedSlot(container: Container, slot: Int, x: Int, y: Int) : Slot(container, slot, x, y) {
        override fun mayPlace(stack: ItemStack): Boolean = false
        override fun mayPickup(player: Player): Boolean = false
    }
}
