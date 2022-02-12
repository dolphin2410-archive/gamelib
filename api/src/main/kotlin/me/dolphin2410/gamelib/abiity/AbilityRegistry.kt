package me.dolphin2410.gamelib.abiity

import me.dolphin2410.gamelib.GameLibAPI
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockDropItemEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent

object AbilityRegistry: Listener {
    init {
        Bukkit.getServer().pluginManager.registerEvents(this, me.dolphin2410.gamelib.GameLibAPI.plugin)
    }

    @JvmStatic
    val registered = ArrayList<Ability>()

    @JvmStatic
    fun <T: Ability> registerAbility(ability: T) {
        registered.add(ability)
        ability.onInit()
    }

    @EventHandler
    fun onInteract(e: PlayerInteractEvent) {
        registered.forEach {
            if (it.isGlobalAbility) {
                it.handleEvent(e)
            }
        }
    }

    @EventHandler
    fun onBlockPlace(e: BlockPlaceEvent) {
        registered.forEach {
            if (it.isGlobalAbility && it is BlockAbility<*, *>) {
                it.handleEvent(e)
            }
        }
    }

    @EventHandler
    fun onBlockBreak(e: BlockBreakEvent) {
        registered.forEach {
            if (it.isGlobalAbility && it is BlockAbility<*, *>) {
                it.handleEvent(e)
            }
        }
    }

    @EventHandler
    fun onBlockDrop(e: BlockDropItemEvent) {
        registered.forEach {
            if (it.isGlobalAbility && it is BlockAbility<*, *>) {
                it.handleEvent(e)
            }
        }
    }
}