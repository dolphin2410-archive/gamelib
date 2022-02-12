package me.dolphin2410.sample

import me.dolphin2410.gamelib.GameLibAPI
import me.dolphin2410.gamelib.abiity.AbilityRegistry
import me.dolphin2410.gamelib.game.Game
import me.dolphin2410.gamelib.util.Icon
import me.dolphin2410.sample.ability.*
import me.dolphin2410.sample.arena.GamingArena
import net.kyori.adventure.text.Component.text
import org.bukkit.*
import org.bukkit.block.Biome
import org.bukkit.entity.EntityType
import org.bukkit.entity.PigZombie
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import kotlin.collections.ArrayList

class SampleGame: Game(), Listener {
    companion object {
        var arena = GamingArena()
        val running = ArrayList<GamingArena>()
        var pvp = false
    }

    override fun onInit() {
        icon = Icon.createIcon(Material.ANVIL, "SampleGame")
        GameLibAPI.tunnel.warpItem.apply {
            item.type = Material.COMPASS
            name = text("warp")
        }

        Bukkit.getServer().addRecipe(Recipes.backup)
        Bukkit.getServer().addRecipe(Recipes.magicCarrot)
        Bukkit.getServer().addRecipe(Recipes.magicFishingRod)
        Bukkit.getServer().addRecipe(Recipes.ultimateFurnace)
        Bukkit.getServer().pluginManager.registerEvents(this, GameLibAPI.plugin)

        GameCommands.register()

        AbilityRegistry.registerAbility(MortisAbility())
        AbilityRegistry.registerAbility(ThunderAbility())
        AbilityRegistry.registerAbility(XrayAbility())
        AbilityRegistry.registerAbility(MagicAppleAbility())
        AbilityRegistry.registerAbility(PowerBeaconAbility())
        AbilityRegistry.registerAbility(SkellySwordAbility())
        AbilityRegistry.registerAbility(FurnaceAbility())

        WorldCreator("arenaqueue").createWorld()
    }

    override fun joinPlayer(player: Player) {
        player.inventory.clear()
        player.teleport(Location(Bukkit.getWorld("arenaqueue"), 0.0, 4.0, 0.0))
        arena.queue(player)
        arena.players.forEach {
            Bukkit.getPlayer(it)!!.sendMessage("Current Players: [${arena.arenaQueue.counter}/${arena.maxPlayers}]")
        }
        if (arena.isFull) {
            running.add(arena)
            arena = GamingArena()
        }
    }

    @EventHandler
    fun playerDeath(e: PlayerDeathEvent) {
        if (e.entity.inventory.containsAtLeast(Items.backupToken, 1)) {
            e.entity.inventory.contents!!.filter { it?.isSimilar(Items.backupToken) == true }[0]!!.amount--
            e.keepInventory = true
        }
    }

    @EventHandler
    fun onHit(e: EntityDamageByEntityEvent) {
        if (e.entity is Player && e.damager is Player && !pvp) {
            e.isCancelled = true
        }
    }

    @EventHandler
    fun onFall(e: EntityDamageEvent) {
        if (e.cause == EntityDamageEvent.DamageCause.FALL && e.entity.location.world == Bukkit.getWorld("arenaqueue")) {
            e.damage = 0.0
            e.entity.fallDistance = 1.0F
        }
    }

    @EventHandler
    fun entityDeath(e: EntityDeathEvent) {
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
                        if (Random().nextInt(100) < 10) {
                            e.drops.add(Items.magicBones)
                        }
                    } else {
                        if (Random().nextInt(100) < 8) {
                            e.drops.add(ItemStack(Material.SKELETON_SKULL))
                        }
                    }
                }
                EntityType.ZOMBIE -> {
                    if (Random().nextInt(100) < 8) {
                        e.drops.add(ItemStack(Material.ZOMBIE_HEAD))
                    }
                    if (Random().nextInt(100) < 5) {
                        e.drops.add(ItemStack(Material.CARROT))
                    }
                }
                else -> {}
            }
        }
    }

    @EventHandler
    fun onEat(e: PlayerItemConsumeEvent) {
        if (e.item == Items.magicApple) {
            e.isCancelled = true
        }
        if (e.item == Items.magicGoldenCarrot) {
            e.player.addPotionEffect(PotionEffect(PotionEffectType.NIGHT_VISION, 600, 1))
            var seconds = 30
            object: BukkitRunnable() {
                override fun run() {
                    seconds--
                    e.player.getNearbyEntities(40.0, 40.0, 40.0).forEach {
                        if (it is PigZombie && (it.isAngry || it.anger != 0)) {
                            it.isAngry = false
                            it.anger = 0
                        }
                    }
                    if (seconds == 0) {
                        cancel()
                    }
                }
            }.runTaskTimer(GameLibAPI.plugin, 0, 20)
        }
    }
}