package me.dolphin2410.gamelib.warp

import me.dolphin2410.gamelib.GameLibAPI
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

interface WarpItemBuilder {
    companion object {
        @JvmStatic
        fun new(): WarpItemBuilder {
            return GameLibAPI.builder()
        }
    }

    fun material(material: Material): WarpItemBuilder

    fun name(name: String): WarpItemBuilder

    fun item(generator: (Material, String) -> ItemStack): WarpItemBuilder

    fun onClick(onclick: (PlayerInteractEvent) -> Unit): WarpItemBuilder

    fun build(): WarpItem
}