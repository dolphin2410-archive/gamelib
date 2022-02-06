package me.dolphin2410.gamelib.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.text
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

interface Icon {
    companion object {
        @JvmStatic
        val DEFAULT = object: Icon {
            override val item = ItemStack(Material.DIRT)
            override var name: Component = text("Unnamed Game")
        }

        @JvmStatic
        fun createIcon(material: Material, name: String): Icon {
            return object: Icon {
                override val item = ItemStack(material)
                override var name: Component = text(name)
            }
        }

        @JvmStatic
        fun createDefaultIcon(name: String) = DEFAULT.apply { this@apply.name = text(name) }
    }
    val item: ItemStack
    var name: Component
        get() = item.displayName()
        set(value) { item.apply { itemMeta = itemMeta.apply { displayName(value) } } }
}