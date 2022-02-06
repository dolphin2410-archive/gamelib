package me.dolphin2410.sample

import me.dolphin2410.gamelib.GameLibAPI
import me.dolphin2410.gamelib.abiity.AbilityRegistry
import me.dolphin2410.gamelib.event.EventRegistry
import me.dolphin2410.gamelib.game.Game
import me.dolphin2410.gamelib.util.Icon
import net.kyori.adventure.text.Component.text
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.WorldCreator
import org.bukkit.block.Biome
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.PlayerDeathEvent
import java.util.*

class SampleGame: Game() {
    val arena = GamingArena()
    override fun onInit() {
        icon = Icon.createIcon(Material.ENCHANTED_BOOK, "SampleGame")
        GameLibAPI.tunnel.warpItem.apply {
            item.type = Material.COMPASS
            name = text("warp")
        }

        Bukkit.getServer().addRecipe(Recipes.backup)
        Bukkit.getServer().addRecipe(Recipes.mortis)
        Bukkit.getServer().addRecipe(Recipes.xray)
        Bukkit.getServer().addRecipe(Recipes.thunder)

        AbilityRegistry.registerAbility(MortisAbility())
        AbilityRegistry.registerAbility(ThunderAbility())
        AbilityRegistry.registerAbility(XrayAbility())

        WorldCreator("arenaqueue").createWorld()

        EventRegistry.register<PlayerDeathEvent> { e ->
            val index = e.entity.inventory.indexOf(Items.backupToken)
            if (index != -1) {
                e.entity.inventory.getItem(index)!!.amount--
                e.isCancelled = true
                e.entity.teleport(e.entity.bedSpawnLocation ?: arena.world.spawnLocation)
            }
        }

        EventRegistry.register<EntityDeathEvent> { e ->
            val killer = e.entity.killer
            if (killer != null) {
                when (e.entityType) {
                    EntityType.HOGLIN -> {
                        if (e.entity.location.block.biome == Biome.CRIMSON_FOREST) {
                            if (Random().nextInt(100) < 10) {
                                e.drops.add(Items.magicCrimsonRoots)
                            }
                        }
                    }
                    EntityType.ENDERMAN -> {
                        if (e.entity.location.block.biome == Biome.WARPED_FOREST) {
                            if (Random().nextInt(100) < 10) {
                                e.drops.add(Items.magicWarpedRoots)
                            }
                        }
                    }
                    EntityType.ZOMBIFIED_PIGLIN -> {
                        if (e.entity.location.block.biome == Biome.NETHER_WASTES) {
                            if (Random().nextInt(100) < 5) {
                                e.drops.add(Items.magicFlesh)
                            }
                        }
                    }
                    EntityType.MAGMA_CUBE -> {
                        if (e.entity.location.block.biome == Biome.BASALT_DELTAS) {
                            if (Random().nextInt(100) < 10) {
                                e.drops.add(Items.magicCream)
                            }
                        }
                    }
                    EntityType.SKELETON -> {
                        if (e.entity.location.block.biome == Biome.SOUL_SAND_VALLEY) {
                            if (Random().nextInt(100) < 5) {
                                e.drops.add(Items.magicBones)
                            }
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    override fun joinPlayer(player: Player) {
        player.inventory.clear()
        player.teleport(Location(Bukkit.getWorld("arenaqueue"), 0.0, 4.0, 0.0))
        arena.queue(player)
        player.sendMessage("Current Players: [${arena.arenaQueue.counter}/${arena.maxPlayers}]")
    }
}