![Banner](https://i.imgur.com/Zp9MVK8.png)

# Cobblemon Trainer Prestige

**Cobblemon Trainer Prestige** is a **Cobblemon addon** that adds a complete title and prestige system for Minecraft servers.

Players unlock titles by catching Pokemon, winning battles, exploring the world, hatching eggs, finding shinies, collecting legendary Pokemon, and completing long-term progression goals.

Titles can appear in chat, the player list, and above player names. The mod also includes an interactive `/prestige` menu so players can browse, equip, lock, and manage titles without memorizing every command.

***

## Features

* Progression-based player titles
* Interactive `/prestige` GUI
* Automatic title unlocking
* Automatic best-title equip by rarity
* Manual title equip and lock system
* Chat title display
* TAB/player list title icon display
* Nameplate title icon display
* Optional pixel-art title texture resource pack
* Textured title badges for chat, TAB/player list, and nameplates
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

***

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

***

## Title Rarities

Titles are sorted by rarity and progression value.

| Rarity | Description |
| --- | --- |
| Common | Early and easy progression goals |
| Uncommon | Basic server progression |
| Rare | Mid-game accomplishments |
| Epic | Strong achievements |
| Legendary | Major milestones |
| Ultra Beast | Ultra Beast-related titles |
| Mythic | Mythical or very rare titles |
| Secret | Hidden or unusual requirements |

***

## Title Categories

| Category | Description |
| --- | --- |
| General | General progression and prestige milestones |
| Capture | Catching Pokemon and filling the collection |
| Pokemon Types | Mastery titles for catching specific types |
| Shiny | Shiny hunting accomplishments |
| Battle | Battle, streak, PvP, and victory titles |
| Exploration | Travel, biome, and world exploration titles |
| Legendaries | Legendary Pokemon feats and ownership titles |
| Mythicals | Mythical Pokemon feats and ownership titles |
| Ultra Beasts | Ultra Beast feats and ownership titles |
| Breeding | Egg hatching and breeding titles |
| Social | Player interaction titles |
| Event | Server event and special moment titles |
| Secret | Mystery and hidden titles |

***

## Legendary, Mythical, and Ultra Beast Titles

Trainer Prestige creates special ownership titles for legendary, mythical, and Ultra Beast Pokemon.

Examples:

* **Master of Rayquaza**
* **Mythic Master of Mew**
* **Ultra Master of Nihilego**

These titles are based on actually owning the Pokemon in the player's party or PC.

If a player trades the Pokemon away, releases it, deletes it, or no longer owns it, the title is removed automatically during the next scan. If another player receives that Pokemon, they can receive the title instead.

The mod scans online players automatically, so players do not need to run commands to update special ownership titles.

***

## Optional Title Textures

Trainer Prestige also supports an optional resource pack called **Trainer Prestige Resource**.

When enabled, titles can appear as custom pixel-art badges instead of plain text. These textured badges work in chat, the TAB/player list, and above player names.

The texture pack includes badges for progression titles, capture titles, shiny titles, battle titles, PvP titles, breeding titles, exploration titles, type mastery titles, legendary titles, mythical titles, Ultra Beast titles, event titles, secret titles, and special Pokemon ownership titles.

Special Pokemon titles, such as Hoopa, Mew, Rayquaza, Nihilego, and other legendary, mythical, or Ultra Beast Pokemon, have their own compact badge textures.

This resource pack is fully optional. Players who do not use it can still play normally with the default text titles.

Admins can choose the display style with:

```txt
/prestige display <TEXT|TEXTURE|BOTH>
```

Display modes:

| Mode | Description |
| --- | --- |
| <code>TEXT</code> | Uses normal text titles |
| <code>TEXTURE</code> | Uses custom textured badges |
| <code>BOTH</code> | Shows both the texture badge and text title |

***

## Commands

Normal player commands:

| Command | Description |
| --- | --- |
| <code>/prestige</code> | Opens the interactive title menu |
| <code>/prestige help</code> | Shows the command list |
| <code>/prestige list</code> | Lists titles in chat |
| <code>/prestige current</code> | Shows the equipped title |
| <code>/prestige equip &lt;id&gt;</code> | Equips an unlocked title |
| <code>/prestige lock [id]</code> | Locks the current or chosen title |
| <code>/prestige unlock</code> | Enables automatic best-title swapping again |
| <code>/prestige remove</code> | Removes the current title |
| <code>/prestige info &lt;id&gt;</code> | Shows title details and progress |
| <code>/prestige progress</code> | Shows locked titles in progress |
| <code>/prestige search &lt;name&gt;</code> | Searches titles by name or id |
| <code>/prestige rarity &lt;rarity&gt;</code> | Filters titles by rarity |
| <code>/prestige category &lt;category&gt;</code> | Filters titles by category |

Admin commands:

| Command | Description |
| --- | --- |
| <code>/prestige grant &lt;player&gt; &lt;id&gt;</code> | Grants a title |
| <code>/prestige revoke &lt;player&gt; &lt;id&gt;</code> | Revokes a title |
| <code>/prestige set &lt;player&gt; &lt;id&gt;</code> | Grants and equips a title |
| <code>/prestige reset &lt;player&gt;</code> | Resets a player's title data |
| <code>/prestige reload</code> | Reloads config and title registry |
| <code>/prestige display &lt;TEXT\|TEXTURE\|BOTH&gt;</code> | Changes the title display style |
| <code>/prestige addprogress &lt;player&gt; &lt;key&gt; &lt;amt&gt;</code> | Adds progress to a player |
| <code>/prestige debug &lt;player&gt;</code> | Shows stored title data |

***

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

***

## Configuration

Trainer Prestige creates its config per world/server at:

```txt
serverconfig/trainer_prestige/config.json
```

Player title data is also stored per world/server, so titles unlocked in one world do not carry over into another.

Available config options include:

| Option | Description |
| --- | --- |
| <code>showTitleInChat</code> | Shows titles in chat |
| <code>showTitleInTab</code> | Shows title icons in the player list |
| <code>showTitleInNameplateFallback</code> | Enables scoreboard nameplate fallback display |
| <code>enableClientNameplateRendering</code> | Enables client nameplate rendering support |
| <code>allowSecretTitles</code> | Allows secret titles |
| <code>announceUnlocksGlobally</code> | Announces unlocks to other players |
| <code>autoEquipFirstTitle</code> | Gives new players the default title |
| <code>defaultTitle</code> | Default first title id |
| <code>tabDisplayMode</code> | Controls title display mode in TAB |
| <code>titleDisplayStyle</code> | Controls text, texture, or combined title display |
| <code>nameplateRenderDistance</code> | Controls nameplate display distance |
| <code>hideTitleWhenSneaking</code> | Hides nameplate title while sneaking |
| <code>requireCobblemon</code> | Controls Cobblemon requirement behavior |

***

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

***

## Short Summary

**Cobblemon Trainer Prestige** adds a complete title progression system to Cobblemon servers, with unlockable titles, rarity tiers, an interactive `/prestige` GUI, chat/TAB/nameplate display, auto-equip, title locking, optional textured title badges, and special ownership titles for legendary, mythical, and Ultra Beast Pokemon.

***

## License

This project is licensed under the **MIT License**.

Copyright (c) 2026 **GabehEPW**
