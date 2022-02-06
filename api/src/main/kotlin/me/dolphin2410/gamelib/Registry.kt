package me.dolphin2410.gamelib

import net.kyori.adventure.text.Component.text
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object Registry {
    @JvmStatic
    var MENU_PREV = ItemStack(Material.ARROW).apply {
            editMeta { meta ->
                meta.displayName(text("←"))
            }
        }

    @JvmStatic
    var MENU_AFTER = ItemStack(Material.SPECTRAL_ARROW).apply {
            editMeta { meta ->
                meta.displayName(text("→"))
            }
        }
}