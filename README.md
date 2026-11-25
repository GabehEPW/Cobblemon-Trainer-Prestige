![Banner](https://i.imgur.com/PpyhcZC.png)

# Cobblemon Trainer Prestige

<p align="center">
  <a href="https://modrinth.com/mod/cobblemon-trainer-prestige"><img src="https://img.shields.io/badge/Modrinth-Download-00AF5C?style=for-the-badge&logo=modrinth&logoColor=white" alt="Modrinth" /></a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/cobblemon-trainer-prestige"><img src="https://img.shields.io/badge/CurseForge-Download-F16436?style=for-the-badge&logo=curseforge&logoColor=white" alt="CurseForge" /></a>
</p>

**Cobblemon Trainer Prestige** is a **Cobblemon addon** that adds a complete title and prestige progression system for Minecraft servers.

Players unlock titles by catching Pokémon, winning battles, exploring the world, hatching eggs, finding shinies, collecting legendary Pokémon, and achieving long-term milestones.

Titles can be displayed in chat, in the TAB player list, and directly above player heads (nameplates). The mod also includes an interactive `/prestige` inventory GUI with smooth, in-place page navigation so players can easily browse, equip, lock, and manage their titles.

***

## Features

* **Progression-Based Titles**: Unlock titles across multiple categories and rarity tiers.
* **Interactive `/prestige` GUI**: Fast, responsive menu with smooth in-place page navigation (no cursor resetting).
* **Automatic Title Unlocking**: Instant unlock notifications and automatic best-title equipping by rarity.
* **Title Locking**: Lock your favorite title so automatic equip doesn't swap it.
* **Multi-Channel Display**: Show titles in chat, TAB player list, and overhead nameplates.
* **Optional Pixel-Art Badges**: Built-in compatibility with the optional **Trainer Prestige Resource** pack for custom badge icons.
* **Dynamic Legendary, Mythical & Ultra Beast Titles**: Automatically detects ownership in Party/PC (including Gen 9 additions like Terapagos, Ogerpon, Pecharunt, and the Loyal Three).
* **Automatic Ownership Scanning**: Automatically revokes ownership titles if Pokémon are traded, released, or moved.
* **Category & Rarity Filters**: Easily filter titles in the GUI or via chat commands.
* **Secret & Mystery Titles**: Hidden titles that display as `?????` until unlocked or given hints.
* **Diagnostics Tool**: Built-in `/prestige debug` to inspect scoreboard and display states.
* **Server-Side Configuration**: Per-world and global server configuration support.
* **Fabric & NeoForge Support**: Unified codebase supporting both platforms on Minecraft 1.21.1 (Java 21).

***

## How It Works

Trainer Prestige tracks player progression through Cobblemon and Minecraft events.

When a requirement is met, the title is unlocked automatically. If no specific title is locked, the mod automatically equips the highest rarity title available.

Open the prestige menu at any time with:

```txt
/prestige
```

Inside the menu, players can:

* Browse all visible titles with continuous page flipping
* View unlocked titles and titles in progress
* Filter titles by category or rarity
* Left-click to equip an unlocked title
* Right-click to lock a title in place
* Enable or disable auto-equipping
* Remove the active title

***

## Title Rarities

Titles are categorized into rarity tiers:

| Rarity | Description |
| --- | --- |
| **Common** | Early and introductory progression goals |
| **Uncommon** | Standard exploration and capture milestones |
| **Rare** | Mid-game accomplishments and type mastery |
| **Epic** | Advanced achievements and competitive feats |
| **Legendary** | Major server milestones and legendary ownership |
| **Ultra Beast** | Ultra Beast capture and mastery titles |
| **Mythic** | Mythical Pokémon feats and rare milestones |
| **Secret** | Mystery titles with hidden unlock criteria |

***

## Title Categories

| Category | Description |
| --- | --- |
| **General** | Server milestones, playtime, and overall prestige |
| **Capture** | Total catches, unique species, and collection progression |
| **Pokemon Types** | Mastery titles for catching specific elemental types |
| **Shiny** | Shiny Pokémon hunting accomplishments |
| **Battle** | Battle victories, win streaks, and PvP triumphs |
| **Exploration** | Distance traveled, biomes discovered, and world exploration |
| **Legendaries** | Legendary Pokémon capture and ownership titles |
| **Mythicals** | Mythical Pokémon feats and ownership titles |
| **Ultra Beasts** | Ultra Beast encounters and ownership titles |
| **Breeding** | Egg hatching and breeding achievements |
| **Social** | Unique player interactions and duel milestones |
| **Event** | Server events and special occasion titles |
| **Secret** | Hidden easter eggs and mystery titles |

***

## Legendary, Mythical & Ultra Beast Ownership

Trainer Prestige features dynamic ownership titles for special Pokémon:

* **Master of Rayquaza**
* **Mythic Master of Mew**
* **Ultra Master of Nihilego**
* **Master of Terapagos**, **Master of Ogerpon**, **Mythic Master of Pecharunt**, etc.

These titles check whether the player currently holds the Pokémon in their **Party** or **PC**.

If a player trades, releases, or deletes the Pokémon, the title is safely revoked during the automatic background scan. If another player obtains that Pokémon, they become eligible for the title.

***

## Optional Title Textures (Badges)

Trainer Prestige supports an optional resource pack called **Trainer Prestige Resource** (included in the `texture/resourcepack` folder).

When active, titles render as custom pixel-art badges in chat, the TAB player list, and above player heads. Over **194 unique badges** are available for progression milestones, types, shinies, and individual legendary/mythical species.

Admins can configure the display mode on the server:

```txt
/prestige display <TEXT|TEXTURE|BOTH>
```

| Mode | Description |
| --- | --- |
| <code>TEXT</code> | Standard formatted colored text |
| <code>TEXTURE</code> | Custom pixel-art badges |
| <code>BOTH</code> | Displays both the badge icon and text title |

***

## Server Compatibility & Diagnostics

Trainer Prestige uses vanilla Minecraft chat components and scoreboard teams for nameplate formatting.

If chat or nameplate display is overwritten by another plugin (e.g., Essentials, TAB, or LuckPerms chat formatting), you can check the display state with:

```txt
/prestige debug
/prestige debug <player>
```

This displays the equipped title, active display mode, TAB style, and team membership.

***

## Commands

### Player Commands

| Command | Description |
| --- | --- |
| <code>/prestige</code> | Opens the interactive title menu |
| <code>/prestige help</code> | Displays the command help list |
| <code>/prestige list</code> | Lists unlocked titles in chat |
| <code>/prestige current</code> | Shows your current equipped title |
| <code>/prestige equip &lt;id&gt;</code> | Equips an unlocked title |
| <code>/prestige lock [id]</code> | Locks the active or specified title |
| <code>/prestige unlock</code> | Re-enables automatic best-title swapping |
| <code>/prestige remove</code> | Unequips your current title |
| <code>/prestige info &lt;id&gt;</code> | Displays title requirements and progress |
| <code>/prestige progress</code> | Displays progress on locked titles |
| <code>/prestige search &lt;name&gt;</code> | Searches titles by name or ID |
| <code>/prestige rarity &lt;rarity&gt;</code> | Filters titles by rarity |
| <code>/prestige category &lt;category&gt;</code> | Filters titles by category |
| <code>/prestige debug</code> | Displays title diagnostics |

### Admin Commands

| Command | Description |
| --- | --- |
| <code>/prestige grant &lt;player&gt; &lt;id&gt;</code> | Grants a title to a player |
| <code>/prestige revoke &lt;player&gt; &lt;id&gt;</code> | Revokes a title from a player |
| <code>/prestige set &lt;player&gt; &lt;id&gt;</code> | Grants and immediately equips a title |
| <code>/prestige reset &lt;player&gt;</code> | Resets a player's title data |
| <code>/prestige reload</code> | Reloads configs and title registry |
| <code>/prestige display &lt;TEXT\|TEXTURE\|BOTH&gt;</code> | Changes title display mode |
| <code>/prestige addprogress &lt;player&gt; &lt;key&gt; &lt;amount&gt;</code> | Adds progress points to a player |
| <code>/prestige debug &lt;player&gt;</code> | Inspects another player's title diagnostics |

***

## Configuration

Server configs are saved per world at:

```txt
serverconfig/trainer_prestige/config.json
```

Global modpack defaults can be placed at:

```txt
config/trainer_prestige/config.json
```

### Config Options

| Option | Description |
| --- | --- |
| <code>showTitleInChat</code> | Shows titles before or after chat messages |
| <code>showTitleInTab</code> | Shows title icons in the TAB player list |
| <code>showTitleInNameplateFallback</code> | Enables scoreboard team nameplate display |
| <code>enableClientNameplateRendering</code> | Enables client-side nameplate rendering support |
| <code>allowSecretTitles</code> | Toggles availability of secret titles |
| <code>announceUnlocksGlobally</code> | Broadcasts title unlocks to the server |
| <code>autoEquipFirstTitle</code> | Automatically equips the default title to new players |
| <code>defaultTitle</code> | ID of the initial title given to new players |
| <code>tabDisplayMode</code> | TAB position: <code>PREFIX</code>, <code>SUFFIX</code>, <code>CLEAN_SUFFIX</code>, or <code>DISABLED</code> |
| <code>tabTitleDisplay</code> | TAB title formatting: <code>COMPACT</code>, <code>FULL</code>, or <code>DISABLED</code> |
| <code>titleDisplayStyle</code> | Title format: <code>TEXT</code>, <code>TEXTURE</code>, or <code>BOTH</code> |
| <code>nameplateRenderDistance</code> | Maximum render distance for nameplates |
| <code>specialOwnershipScanIntervalSeconds</code> | Interval in seconds to scan online players for legendary/mythical ownership |
| <code>hideTitleWhenSneaking</code> | Hides overhead nameplate title while sneaking |
| <code>requireCobblemon</code> | Controls whether Cobblemon is strictly required |

***

## Dependencies

* **Minecraft**: `1.21.1`
* **Cobblemon**: `1.8.4+`
* **Java**: `21`
* **Fabric**: Fabric Loader & Fabric Language Kotlin
* **NeoForge**: NeoForge & Kotlin for Forge

> *Note for Cobblemon 1.7 users: If you are running Cobblemon 1.7.0, please use version 1.0.0 (branch `legacy-1.0.0`).*

***

## License

This project is licensed under the **MIT License**.

Copyright (c) 2026 **GabehEPW**
