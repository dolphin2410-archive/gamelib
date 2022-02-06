package me.dolphin2410.gamelib.warp

import net.kyori.adventure.text.Component.text
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class WarpItemBuilderImpl: WarpItemBuilder {
    var material = Material.COMPASS
    var name = "Warp"
    var generator = { material: Material, name: String ->
        ItemStack(material).apply { itemMeta = itemMeta.apply { displayName(text(name)) } }
    }

    var onclick = { _: PlayerInteractEvent ->

    }

    override fun material(material: Material): WarpItemBuilder {
        this.material = material
        return this
    }

    override fun name(name: String): WarpItemBuilder {
        this.name = name
        return this
    }

    override fun item(generator: (Material, String) -> ItemStack): WarpItemBuilder {
        this.generator = generator
        return this
    }

    override fun onClick(onclick: (PlayerInteractEvent) -> Unit): WarpItemBuilder {
        this.onclick = onclick
        return this
    }

    override fun build(): WarpItem {
        return WarpItemImpl(text(name), generator(material, name))
    }
}