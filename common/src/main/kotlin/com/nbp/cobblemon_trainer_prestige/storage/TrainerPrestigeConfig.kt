package com.nbp.cobblemon_trainer_prestige.storage

data class TrainerPrestigeConfig(
    var showTitleInChat: Boolean = true,
    var showTitleInTab: Boolean = true,
    var showTitleInNameplateFallback: Boolean = true,
    var enableClientNameplateRendering: Boolean = true,
    var allowSecretTitles: Boolean = true,
    var announceUnlocksGlobally: Boolean = false,
    var autoEquipFirstTitle: Boolean = true,
    var defaultTitle: String = "novato",
    var tabDisplayMode: TabDisplayMode = TabDisplayMode.PREFIX,
    var nameplateRenderDistance: Int = 32,
    var hideTitleWhenSneaking: Boolean = true,
    var requireCobblemon: Boolean = false,
)

enum class TabDisplayMode {
    PREFIX,
    SUFFIX,
    CLEAN_SUFFIX,
    DISABLED,
}
