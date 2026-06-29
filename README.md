# Cobblemon Trainer Prestige

**Cobblemon Trainer Prestige** is a **Cobblemon addon** that adds a complete title and prestige system for Minecraft servers.

Players unlock titles by catching Pokemon, winning battles, exploring the world, hatching eggs, finding shinies, collecting legendary Pokemon, and completing long-term progression goals.

Titles can appear in chat, the player list, and above player names. The mod also includes an interactive `/prestige` menu so players can browse, equip, lock, and manage titles without memorizing every command.

---

## Features

* Progression-based player titles
* Interactive `/prestige` GUI
* Automatic title unlocking
* Automatic best-title equip by rarity
* Manual title equip and lock system
* Chat title display
* TAB/player list title icon display
* Nameplate title icon display
* Optional resource pack with pixel-art title textures
* Per-world and per-server title data
* Category and rarity filters
* Secret and mystery titles
* PvP battle titles
* Egg hatching titles
* Shiny, capture, exploration, breeding, and battle titles
* Dynamic titles for owned legendary, mythical, and Ultra Beast Pokemon
* Automatic scanning for traded, released, deleted, or moved special Pokemon
* Server-side configuration
* Fabric and NeoForge support

---

## How It Works

Trainer Prestige tracks player progress through Cobblemon and Minecraft events.

When a player completes a requirement, the title is unlocked automatically. If the player has not locked a specific title, the mod automatically equips the best unlocked title based on rarity.

Players can open the title menu with:

```txt
/prestige
```

The menu lets players:

* View all visible titles
* View unlocked titles
* View titles in progress
* Browse titles by category
* Equip unlocked titles
* Lock a title to prevent auto-swapping
* Unlock auto-equip again
* Remove the current title

---

## Title Rarities

Titles are sorted by rarity and progression value.

| Rarity      | Description                      |
| ----------- | -------------------------------- |
| Common      | Early and easy progression goals |
| Uncommon    | Basic server progression         |
| Rare        | Mid-game accomplishments         |
| Epic        | Strong achievements              |
| Legendary   | Major milestones                 |
| Ultra Beast | Ultra Beast-related titles       |
| Mythic      | Mythical or very rare titles     |
| Secret      | Hidden or unusual requirements   |

---

## Title Categories

| Category            | Description                                      |
| ------------------- | ------------------------------------------------ |
| General             | General progression and prestige milestones      |
| Capture             | Catching Pokemon and filling the collection      |
| Pokemon Types       | Mastery titles for catching specific types       |
| Shiny               | Shiny hunting accomplishments                    |
| Battle              | Battle, streak, PvP, and victory titles          |
| Exploration         | Travel, biome, and world exploration titles      |
| Legendaries         | Legendary Pokemon feats and ownership titles     |
| Mythicals           | Mythical Pokemon feats and ownership titles      |
| Ultra Beasts        | Ultra Beast feats and ownership titles           |
| Breeding            | Egg hatching and breeding titles                 |
| Social              | Player interaction titles                        |
| Event               | Server event and special moment titles           |
| Secret              | Mystery and hidden titles                        |

---

## Legendary, Mythical, and Ultra Beast Titles

Trainer Prestige creates special ownership titles for legendary, mythical, and Ultra Beast Pokemon.

Examples:

* **Master of Rayquaza**
* **Mythic Master of Mew**
* **Ultra Master of Nihilego**

These titles are based on actually owning the Pokemon in the player's party or PC.

If a player trades the Pokemon away, releases it, deletes it, or no longer owns it, the title is removed automatically during the next scan. If another player receives that Pokemon, they can receive the title instead.

The mod scans online players automatically, so players do not need to run commands to update special ownership titles.

---

## Optional Title Textures

Trainer Prestige includes an optional resource pack for textured title badges.

Without the resource pack, titles use normal Minecraft text formatting. With the resource pack enabled, titles can appear as compact pixel-art badges in chat, TAB/player list, and nameplates.

The resource pack is separate from the mod jar so servers and players can choose whether they want textured titles.

Resource pack file:

```txt
texture/Trainer Prestige Resource.zip
```

Display styles:

| Style     | Description                                      |
| --------- | ------------------------------------------------ |
| `TEXT`    | Uses normal text titles                          |
| `TEXTURE` | Uses textured title badges                       |
| `BOTH`    | Shows the texture badge followed by text fallback |

Admins can change the server display style with:

```txt
/prestige display <TEXT|TEXTURE|BOTH>
```

Players need the optional resource pack enabled to see the textured badges. Players without the pack should use the normal text style.

---

## Commands

Normal player commands:

| Command                          | Description                                      |
| -------------------------------- | ------------------------------------------------ |
| `/prestige`                      | Opens the interactive title menu                 |
| `/prestige help`                 | Shows the command list                           |
| `/prestige list`                 | Lists titles in chat                             |
| `/prestige current`              | Shows the equipped title                         |
| `/prestige equip <id>`           | Equips an unlocked title                         |
| `/prestige lock [id]`            | Locks the current or chosen title                |
| `/prestige unlock`               | Enables automatic best-title swapping again      |
| `/prestige remove`               | Removes the current title                        |
| `/prestige info <id>`            | Shows title details and progress                 |
| `/prestige progress`             | Shows locked titles in progress                  |
| `/prestige search <name>`        | Searches titles by name or id                    |
| `/prestige rarity <rarity>`      | Filters titles by rarity                         |
| `/prestige category <category>`  | Filters titles by category                       |

Admin commands:

| Command                                      | Description                         |
| -------------------------------------------- | ----------------------------------- |
| `/prestige grant <player> <id>`              | Grants a title                      |
| `/prestige revoke <player> <id>`             | Revokes a title                     |
| `/prestige set <player> <id>`                | Grants and equips a title           |
| `/prestige reset <player>`                   | Resets a player's title data        |
| `/prestige reload`                           | Reloads config and title registry   |
| `/prestige display <TEXT\|TEXTURE\|BOTH>`    | Changes the server title display style |
| `/prestige addprogress <player> <key> <amt>` | Adds progress to a player           |
| `/prestige debug <player>`                   | Shows stored title data             |

---

## GUI

The `/prestige` menu uses a vanilla inventory-style interface.

The top row contains management buttons:

* Current title
* All titles
* Unlocked titles
* Titles in progress
* Remove title
* Auto-equip
* Lock or unlock title
* Help

Category buttons use different Minecraft items to make browsing easier. Secret categories and hidden secret titles appear as `?????`.

Inside title lists:

* Left click equips an unlocked title
* Right click locks an unlocked title
* Locked titles show progress and unlock information
* Titles are ordered from easier to harder where applicable

---

## Configuration

Trainer Prestige creates its config per world/server at:

```txt
serverconfig/trainer_prestige/config.json
```

Player title data is also stored per world/server, so titles unlocked in one world do not carry over into another.

Available config options include:

| Option                         | Description                                      |
| ------------------------------ | ------------------------------------------------ |
| `showTitleInChat`              | Shows titles in chat                             |
| `showTitleInTab`               | Shows title icons in the player list             |
| `showTitleInNameplateFallback` | Enables scoreboard nameplate fallback display    |
| `enableClientNameplateRendering` | Enables client nameplate rendering support     |
| `allowSecretTitles`            | Allows secret titles                             |
| `announceUnlocksGlobally`      | Announces unlocks to other players               |
| `autoEquipFirstTitle`          | Gives new players the default title              |
| `defaultTitle`                 | Default first title id                           |
| `tabDisplayMode`               | Controls title display mode in TAB               |
| `titleDisplayStyle`            | Controls text, texture, or combined title display |
| `nameplateRenderDistance`      | Controls nameplate display distance              |
| `hideTitleWhenSneaking`        | Hides nameplate title while sneaking              |
| `requireCobblemon`             | Controls Cobblemon requirement behavior          |

---

## Dependencies

### Common

* Minecraft **1.21.1**
* Cobblemon **1.7.0+1.21.1**

### Fabric

* Fabric Loader
* Fabric API
* Fabric Language Kotlin

### NeoForge

* NeoForge
* Kotlin for Forge

---

## Short Summary

**Cobblemon Trainer Prestige** adds a full title progression system to Cobblemon servers, with unlockable titles, rarity tiers, an interactive `/prestige` GUI, chat/TAB/nameplate display, auto-equip, title locking, and special ownership titles for legendary, mythical, and Ultra Beast Pokemon.

---

## License

This project is licensed under the **MIT License**.

Copyright (c) 2026 **GabehEPW**
